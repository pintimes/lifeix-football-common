/**
 * 
 */
package com.lifeix.football.common.util;


import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.xml.bind.DatatypeConverter;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;

/**
 * @author xule
 */
public class ImageUploadUtil {
	private static Logger logger = LoggerFactory.getLogger(ImageUploadUtil.class);
	
	/**
	 * @name uploadImg
	 * @description 上传图片到七牛云
	 * @author xule
	 * @version 2016年8月3日 上午11:15:01
	 * @param 
	 * @return String 上传到七牛云的图片名（不是图片完整路径，不包含图片主机地址）
	 * @throws
	 * 
	 * 	app.imageHost=http://s.files.c-f.com/
		app.imagePrefix=wemedia/images/
	 */
	public static String uploadImage(String fileHost, String imageHost, String imagePrefix, String imgUrl) {
		if (StringUtils.isEmpty(imgUrl)) {
			logger.error("上传图片参数缺失！");
			return null;
		}
		try {
			/**
			 * 读取图片
			 */
			byte[] b = readData(imgUrl);
			
			/**
			 * 计算唯一的图片名
			 */
			String key=calcETag(b)+getSuffix(imgUrl);

			/**
			 * 判断是否需要加上图片名前缀
			 */
			if (!StringUtils.isEmpty(imagePrefix)) {//图片前缀不为空，加上前缀
				key=imagePrefix+key;
			}
			
			String newImage = imageHost+key;//生成图片完整路径
			logger.info("newImage= "+newImage);
			if (imageExists(newImage)) {//图片存在，不需要重复上传
				return newImage;
			}
			
			/**
			 * 获得图片上传token
			 */
			String imgName=imgUrl;
			String token = getUploadToken(fileHost,imgName);
			JSONObject uploadTokenJson= JSONObject.parseObject(token);//获得token
			String uploadToken = uploadTokenJson.getString("uptoken");
			
			/**
			 * 上传图片
			 */
			UploadManager uploadManager = new UploadManager();
			Response res = uploadManager.put(b,key,uploadToken);//上传图片
			JSONObject result=JSONObject.parseObject(res.bodyString());
			if (result!=null) {
				return newImage;
			}
			return null;
		} catch (QiniuException e) {
			Response r = e.response;
			logger.error(r.toString());
		} catch (Exception e) {
			logger.error("upload image fail",e);
		}
		return null;
	}
	
	/**
 	 * @name getSuffix
	 * @description 根据图片链接地址获得图片后缀名，地址中不存在图片后缀名或者不是规范图片格式的图片给全部默认为jpg格式的图片
	 * 				规范图片格式包括:jpg,jpeg,png,gif
	 * @author xule
	 * @version 2016年10月27日 上午11:50:31
	 * @param 
	 * @return String
	 * @throws
	 */
	public static String getSuffix(String imgUrl){
		Set<String> imgSuffixSet=new HashSet<>();
		imgSuffixSet.add(".jpg");
		imgSuffixSet.add(".jpeg");
		imgSuffixSet.add(".png");
		imgSuffixSet.add(".gif");
		String suffix = imgUrl.substring(imgUrl.lastIndexOf("."));
		if (StringUtils.isEmpty(suffix)||!imgSuffixSet.contains(suffix)) {//当前图片后缀为空，或者不是合法格式的图片，全部转换成.jpg格式的图片
			suffix=".jpg";
		}
		return suffix;
	}

	/**
	 * @name readData
	 * @description 上传图片到七牛云的之前从图片地址读取图片内容到内存中
	 * @author xule
	 * @version 2016年10月8日 上午11:26:13
	 * @param 
	 * @return byte[]
	 * @throws
	 */
	public static byte[] readData(String url){
		URL URL;
		try {
			URL = new URL(url);
			InputStream is = URL.openStream();
			byte[] b = IOUtils.toByteArray(is);//获得图片
			is.close();
			return b;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null ;
	}

	/**
	 * @name getToken
	 * @description
	 * @author xule
	 * @version 2016年8月2日 上午9:59:22
	 * @param 
	 * @return String
	 * @throws 
	 */
	private static String getUploadToken(String fileHost,String imgName) {
		try {
			String url=fileHost+"/football/file/token/upload?key=admin&file_type=1&file_name="+imgName;
			HttpGet http = new HttpGet(url);
			http.setHeader("Content-Type", "application/json");
			String result = HttpUtil.sendHttp(http);
			if (StringUtils.isEmpty(result)) {
				return null;
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/***************************************生成唯一图片名，同一图片地址生成相同图片名，不同图片地址生成不同图片名**************************************************/
	private static final int CHUNK_SIZE = 1 << 22;

	private static byte[] sha1(byte[] data) throws NoSuchAlgorithmException {
		MessageDigest mDigest = MessageDigest.getInstance("sha1");
		return mDigest.digest(data);
	}

	private static String urlSafeBase64Encode(byte[] data) {
		String encodedString = DatatypeConverter.printBase64Binary(data);
		encodedString = encodedString.replace('+', '-').replace('/', '_');
		return encodedString;
	}

	/**
	 * @name calcETag
	 * @description 引用七牛生成文件名算法，将原形参String fileName改成读取到的图片字节数组byte[] fileData
	 * @author xule
	 * @version 2016年10月27日 上午11:46:45
	 * @param 
	 * @return String
	 * @throws
	 */
	public static String calcETag(byte[] fileData) throws IOException,
			NoSuchAlgorithmException {
		String etag = "";
		long fileLength = fileData.length;
		if (fileLength <= CHUNK_SIZE) {
			byte[] sha1Data = sha1(fileData);
			int sha1DataLen = sha1Data.length;
			byte[] hashData = new byte[sha1DataLen + 1];
			System.arraycopy(sha1Data, 0, hashData, 1, sha1DataLen);
			hashData[0] = 0x16;
			etag = urlSafeBase64Encode(hashData);
		} else {
			int chunkCount = (int) (fileLength / CHUNK_SIZE);
			if (fileLength % CHUNK_SIZE != 0) {
				chunkCount += 1;
			}
			byte[] allSha1Data = new byte[0];
			for (int i = 0; i < chunkCount; i++) {
				byte[] chunkData = new byte[CHUNK_SIZE];
				byte[] bytesRead = new byte[(int) fileLength];
				System.arraycopy(chunkData, 0, bytesRead, 0, (int)fileLength);
				byte[] chunkDataSha1 = sha1(bytesRead);
				byte[] newAllSha1Data = new byte[chunkDataSha1.length
						+ allSha1Data.length];
				System.arraycopy(allSha1Data, 0, newAllSha1Data, 0,
						allSha1Data.length);
				System.arraycopy(chunkDataSha1, 0, newAllSha1Data,
						allSha1Data.length, chunkDataSha1.length);
				allSha1Data = newAllSha1Data;
			}
			byte[] allSha1DataSha1 = sha1(allSha1Data);
			byte[] hashData = new byte[allSha1DataSha1.length + 1];
			System.arraycopy(allSha1DataSha1, 0, hashData, 1,
					allSha1DataSha1.length);
			hashData[0] = (byte) 0x96;
			etag = urlSafeBase64Encode(hashData);
		}
		return etag;
	}
	/*********************************************************************************************************************************/

	/**
	 * @name imageExists 
	 * @description 判断图片是否存在，发送head请求
	 * @author xule
	 * @version 2016年10月27日 下午2:01:47
	 * @param 
	 * @return boolean
	 * @throws
	 */
	public static boolean imageExists(String image){
		URL url;
		try {
			url = new URL(image);
			HttpURLConnection conn=(HttpURLConnection) url.openConnection();
			conn.setRequestMethod("HEAD");
			conn.connect(); 
			if (conn.getResponseCode()==404) {
				return false;
			}
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}
}
