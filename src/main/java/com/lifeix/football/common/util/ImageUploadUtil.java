/**
 * 
 */
package com.lifeix.football.common.util;

import java.io.InputStream;
import java.net.URL;

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
			 * 获得图片上传token
			 */
			String imgName="img.jpg";
			String token = getUploadToken(fileHost,imgName);
			JSONObject uploadTokenJson= JSONObject.parseObject(token);//获得token
			String uploadToken = uploadTokenJson.getString("uptoken");
			JSONArray keyArray=uploadTokenJson.getJSONArray("keys");
			if (keyArray==null||keyArray.size()<=0) {
				logger.error("获取生成的唯一文件名失败！");
				return null;
			}
			String key=keyArray.getString(0);
			
			/**
			 * 判断是简单上传还是覆盖上传
			 */
			if (!StringUtils.isEmpty(imagePrefix)) {//图片前缀不为空，是覆盖上传
				key=imagePrefix+key;
			}
			
			/**
			 * 上传图片
			 */
			UploadManager uploadManager = new UploadManager();
			Response res = uploadManager.put(b,key,uploadToken);//上传图片
			JSONObject result=JSONObject.parseObject(res.bodyString());
			if (result!=null) {
				return result.getString("key");
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
	 * @name readData
	 * @description 上传图片到七牛云的之前从图片地址读取图片内容到内存中
	 * @author xule
	 * @version 2016年10月8日 上午11:26:13
	 * @param 
	 * @return byte[]
	 * @throws
	 */
	private static byte[] readData(String url){
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
	public static String getUploadToken(String fileHost,String imgName) {
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

}
