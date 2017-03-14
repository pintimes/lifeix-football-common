package com.lifeix.football.common.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.UUID;

import org.apache.catalina.util.URLEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.MimeMappings;
import org.springframework.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.lifeix.football.common.exception.IllegalparamException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.squareup.okhttp.ResponseBody;

/**
 * 文件上传工具类，支持图片、视频、音频等任意文件类型
 * @author xule
 * @version 2017年3月9日 上午11:10:52
 */
public class FileUploadUtil {
	private static Logger logger = LoggerFactory.getLogger(FileUploadUtil.class);
	private static final String FILETYPE_ALL="all";
	private static final String FILETYPE_IMAGE="image";
	private static final String FILETYPE_VIDEO="video";
	private static final String FILETYPE_AUDIO="audio";

	/**
	 * 通用的文件上传方法，抓取链接文件并上传到七牛，支持图片、视频、音频等任意文件类型
	 * @author xule
	 * @version 2017年2月7日 下午3:52:24
	 * @param fileHost 文件服务主机地址
	 * @param bucket 文件域名
	 * @param filePrefix 自定义的上传后的文件名前缀
	 * @param fileUrl 文件地址
	 * @return 上传后的完整文件地址
	 * @throws Exception 
	 */
	public static String genericUpload(String fileHost, String bucket, String filePrefix, String fileUrl) throws Exception {
		return upload(fileHost, bucket, filePrefix, fileUrl, FILETYPE_ALL);
	}
	
	public static String imageUpload(String fileHost, String bucket, String filePrefix, String fileUrl) throws Exception{
		return upload(fileHost, bucket, filePrefix, fileUrl, FILETYPE_IMAGE);
	}
	
	public static String videoUpload(String fileHost, String bucket, String filePrefix, String fileUrl) throws Exception{
		return upload(fileHost, bucket, filePrefix, fileUrl, FILETYPE_VIDEO);
	}
	
	public static String audioUpload(String fileHost, String bucket, String filePrefix, String fileUrl) throws Exception{
		return upload(fileHost, bucket, filePrefix, fileUrl, FILETYPE_AUDIO);
	}
	
	private static String upload(String fileHost, String bucket, String filePrefix, String fileUrl, String fileType) throws Exception {
		logger.info("文件上传中...文件地址："+fileUrl+"，文件类型："+fileType);
		notNullValidate(fileHost,bucket,fileUrl);
		if (!fileUrl.startsWith("http://")&&!fileUrl.startsWith("https://")) {
			fileUrl="http://"+fileUrl;
		}
		/**
         * 计算新图片地址
         */
		String newFileKey=getNewFileKey(filePrefix,fileUrl,fileType);
		if (StringUtils.isEmpty(newFileKey)) {
			throw new Exception("生成新文件key失败！图片地址："+fileUrl);
		}
		String newFileUrl=bucket+newFileKey;
		/**
		 * 判断文件是否已经上传过，如果已经上传过，则直接返回新文件地址，否则读取文件并进行上传
		 */
		if (OKHttpUtil.head(newFileUrl)) {//文件存在，不需要重复上传
			logger.info("文件已存在，不需要重复上传！文件原地址："+fileUrl+"，文件新地址："+newFileUrl);
			return newFileUrl;
		}
		try {
			/**
        	 * 读取文件并写入临时文件夹
        	 */
        	String filepath = writeTempFile(fileUrl);
        	/**
			 * 获得文件上传token
			 */
			String uploadToken = getFileUploadToken(fileHost,fileType,fileUrl);
        	/**
        	 * 调用七牛JDK上传文件
        	 */
        	Response res=new UploadManager().put(filepath, newFileKey, uploadToken);
			if (res!=null&&res.statusCode==200) {//文件上传成功
				logger.info("文件上传成功！文件原地址："+fileUrl+"，文件新地址："+newFileUrl);
				return newFileUrl;
			}
			throw new Exception(res.error);
		} catch (Exception e) {
			logger.error("文件上传失败，"+e.getMessage()+",fileUrl="+fileUrl);
			throw new Exception("文件上传失败，"+e.getMessage());
		} 
	}
	
	/**
	 * @author xule
	 * @version 2017年3月9日  上午10:39:46
	 * @param 
	 * @return void
	 */
	private static void notNullValidate(String fileHost, String bucket,String fileUrl) {
		if (StringUtils.isEmpty(fileHost)) {
			throw new IllegalparamException("上传失败，文件服务主机地址为空！");
		}
		if (StringUtils.isEmpty(bucket)) {
			throw new IllegalparamException("上传失败，文件域名为空！");
		}
		if (StringUtils.isEmpty(fileUrl)) {
			throw new IllegalparamException("上传失败，文件路径为空！");
		}
	}

	/**
	 * 生成新文件key
	 * @author xule
	 * @version 2017年3月10日  下午8:30:42
	 * @param 
	 * @return String
	 * @throws Exception 
	 */
	private static String getNewFileKey(String filePrefix,String fileUrl, String fileType) throws Exception {
		if (filePrefix==null) {
			filePrefix="";
		}
		String newFileKey=filePrefix+MD5Util.getMD5(fileUrl);
		String fileExtension = getFileExtension(fileType, fileUrl);
		if (!StringUtils.isEmpty(fileExtension)) {
			newFileKey+=fileExtension;
		}
		return newFileKey;
	}
	
	/**
	 * 获取文件扩展名
	 * @author xule
	 * @version 2017年2月7日 下午2:16:43
	 * @param 
	 * @return 文件格式：.xxx
	 * @throws Exception 
	 */
	private static String getFileExtension(String fileType, String fileUrl) throws Exception{
		/**
		 * 使用自定义方法解析文件链接字符串获得文件扩展名
		 */
		String extension = getExtensionByUrl(fileUrl);
		if (checkExtension(fileType, extension)) {
			return "."+extension;
		}
		/**
		 * 使用jdk提供的URLConnection静态方法获得网络文件的Content-Type
		 */
		String contentType = URLConnection.guessContentTypeFromName(fileUrl);
		if (StringUtils.isEmpty(contentType)) {
			InputStream is=null;
			try {
				is = new URL(fileUrl).openStream();
				contentType=URLConnection.guessContentTypeFromStream(is);
				if (StringUtils.isEmpty(contentType)) {
					return null;
				}
			} finally {
				if (is!=null) {
					is.close();
				}
			}
		}
		/**
		 * 截取文件扩展名
		 */
		String[] splits = contentType.split("/");
		if (splits.length<2) {
			return null;
		}
		extension=splits[1];
		/**
		 * 检查扩展名合法性
		 */
		if (checkExtension(fileType, extension)) {
			return "."+extension;
		}
		return null;
	}
	
	/**
	 * 自定义的文件扩展名获取方法（简单字符串解析，为快速获取文件扩展名，不提供校验机制）
	 * @author xule
	 * @version 2017年3月13日  下午5:01:17
	 * @param 
	 * @return String
	 */
	private static String getExtensionByUrl(String fileUrl) {
		String temp=fileUrl;
		int index1 = temp.indexOf("?");
		if (index1>=0) {
			temp=temp.substring(0, index1);
		}
		int index2 = temp.indexOf("!");
		if (index2>=0) {
			temp=temp.substring(0, index2);
		}
		int index3 = temp.lastIndexOf(".");
		if (index3==-1) {
			return null;
		}
		return temp.substring(index3+1);
	}

	/**
	 * 扩展名合法性校验
	 * @author xule
	 * @version 2017年3月13日  下午4:43:52
	 * @param 
	 * @return boolean
	 */
	private static boolean checkExtension(String fileType,String extension){
		String mime=MimeMappings.DEFAULT.get(extension);
		if (StringUtils.isEmpty(mime)) {//根据文件扩展名无法获取到mime，则认为截取的文件扩展名不合法
			return false;
		}
		if (!FILETYPE_ALL.equals(fileType)&&!fileType.equals(mime.split("/")[0])) {//如果mime不包含指定的文件类型，则认为截取的扩展名不合法
			return false;
		}
		return true;
	}

	/**
	 * 将指定链接的文件写入临时文件夹中
	 * @author xule
	 * @version 2017年3月10日  下午7:49:24
	 * @param 
	 * @return String
	 */
	private static String writeTempFile(String fileUrl) throws Exception{
		String filepath = System.getProperty("java.io.tmpdir") +UUID.randomUUID().toString();//生成唯一的临时文件名
		InputStream is = null;
        FileOutputStream fos = null;
        ResponseBody body=null;
        try {
        	com.squareup.okhttp.Response headResponse = OKHttpUtil.get(fileUrl, null);
        	if (headResponse==null) {
        		throw new Exception("OKHttpUtil.get=null，文件地址："+fileUrl);
			}
        	if (!headResponse.isSuccessful()) {
				throw new Exception("无效的图片地址："+fileUrl);
			}
        	body = headResponse.body();
        	if (body==null) {
				throw new Exception("获取文件http请求返回体失败，文件地址："+fileUrl);
			}
        	is=body.byteStream();
			fos = new FileOutputStream(filepath);
			byte[] buffer = new byte[4096]; 
			int length;
			while ((length = is.read(buffer)) > 0) {
				fos.write(buffer, 0, length);
			}
		} catch (Exception e) {
			throw new Exception("文件写入失败，"+e.getMessage());
		} finally {
			if (is != null) {
        		is.close();
            }
            if (fos != null) {
				fos.close();
            }
            if (body!=null) {
				body.close();
			}
		}          
		return filepath;
	}
	
	/**
	 * 获取文件上传token
	 * @author xule
	 * @version 2017年3月10日  下午7:49:05
	 * @param 
	 * @return String
	 */
	private static String getFileUploadToken(String fileHost, String fileType ,String fileUrl) throws Exception {
		int file_type=0;
		if (FILETYPE_IMAGE.equals(fileType)) {
			file_type=1;
		}else if (FILETYPE_AUDIO.equals(fileType)) {
			file_type=2;
		}else if (FILETYPE_VIDEO.equals(fileType)) {
			file_type=3;
		}
		String url=fileHost+"/football/file/token/upload?key=visitor&file_type="+file_type+"&file_name="+URLEncoder.DEFAULT.encode(fileUrl);
		String token = HttpUtil.sendGet(url);
		if (StringUtils.isEmpty(token)) {
			throw new Exception("文件上传token为空");
		}
		JSONObject uploadTokenJson= JSONObject.parseObject(token);//获得token
		if (uploadTokenJson==null||uploadTokenJson.isEmpty()) {
			throw new Exception("从文件服务获取文件上传token失败");
		}
		String uploadToken = uploadTokenJson.getString("uptoken");
		if (StringUtils.isEmpty(uploadToken)) {
			throw new Exception("文件上传token为空");
		}
		return uploadToken;
	}
}