package com.lifeix.football.common.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.lifeix.football.common.exception.BusinessException;
import com.lifeix.football.common.exception.IllegalparamException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;

/**
 * 文件上传工具类，支持图片、视频、音频等任意文件类型
 * @author xule
 * @version 2017年2月7日 下午3:52:24
 */
public class FileUploadUtil {
	private static Logger logger = LoggerFactory.getLogger(FileUploadUtil.class);
	public static final String FILETYPE_ALL="all";
	public static final String FILETYPE_IMAGE="image";
	public static final String FILETYPE_VIDEO="video";
	public static final String FILETYPE_AUDIO="audio";

	/**
	 * 通用的文件上传方法，抓取链接文件并上传到七牛，支持图片、视频、音频等任意文件类型
	 * @author xule
	 * @version 2017年2月7日 下午3:52:24
	 * @param fileHost 文件服务主机地址
	 * @param bucket 文件域名
	 * @param filePrefix 自定义的上传后的文件名前缀
	 * @param fileUrl 文件地址
	 * @return 上传后的完整文件地址
	 */
	public static String genericUpload(String fileHost, String bucket, String filePrefix, String fileUrl) {
		return upload(fileHost, bucket, filePrefix, fileUrl, FILETYPE_ALL);
	}
	
	public static String imageUpload(String fileHost, String bucket, String filePrefix, String fileUrl) {
		return upload(fileHost, bucket, filePrefix, fileUrl, FILETYPE_IMAGE);
	}
	
	public static String videoUpload(String fileHost, String bucket, String filePrefix, String fileUrl) {
		return upload(fileHost, bucket, filePrefix, fileUrl, FILETYPE_VIDEO);
	}
	
	public static String audioUpload(String fileHost, String bucket, String filePrefix, String fileUrl) {
		return upload(fileHost, bucket, filePrefix, fileUrl, FILETYPE_AUDIO);
	}
	
	private static String upload(String fileHost, String bucket, String filePrefix, String fileUrl, String fileType) {
		logger.info("文件上传中，文件地址："+fileUrl+"，文件类型："+fileType);
		if (StringUtils.isEmpty(fileUrl)) {
			throw new IllegalparamException("上传失败，文件路径为空！");
		}
		/**
		 * 读取网络文件到临时文件夹中，同时获取网络文件大小、类型等信息
		 */
        HttpURLConnection urlConn=null;
        try {
        	/**
        	 * 获得文件地址或文件重定向地址的连接对象
        	 */
        	urlConn=getFileUrlConnection(fileUrl);
        	/**
        	 * 获取连接成功的文件地址，可能是原地址或重定向的文件地址
        	 */
        	fileUrl = urlConn.getURL().toString();
            /**
			 * 读取文件并写入临时文件夹
			 */
    		String filepath = writeTempFile(urlConn);
			/**
			 * 计算唯一的文件名
			 */
			String key = QiniuEtag.file(filepath);
			String fileFormat = getFileFormat(fileType,fileUrl,urlConn);
			if (!StringUtils.isEmpty(fileFormat)) {
				if (!fileFormat.startsWith(".")) {
					fileFormat="."+fileFormat;
				}
				key+=fileFormat;
			}
			/**
			 * 判断是否需要加上文件名前缀
			 */
			if (!StringUtils.isEmpty(filePrefix)) {//文件前缀不为空，加上前缀
				key=filePrefix+key;
			}
			/**
			 * 拼接文件完整路径
			 */
			String newfile = bucket+key;
			/**
			 * 判断文件是否已经上传过
			 */
			if (OKHttpUtil.head(newfile)) {//文件存在，不需要重复上传
				logger.info("文件已存在，不需要重复上传，文件地址："+fileUrl);
				return newfile;
			}
			/**
			 * 获得文件上传token
			 */
			String fileName=key;
			String token = getUploadToken(fileHost,fileName,fileType);
			if (StringUtils.isEmpty(token)) {
				throw new BusinessException("文件上传token为空");
			}
			JSONObject uploadTokenJson= JSONObject.parseObject(token);//获得token
			if (uploadTokenJson==null||uploadTokenJson.isEmpty()) {
				throw new BusinessException("从文件服务获取文件上传token失败");
			}
			String uploadToken = uploadTokenJson.getString("uptoken");
			if (StringUtils.isEmpty(uploadToken)) {
				throw new BusinessException("文件上传token为空");
			}
			/**
			 * 上传文件
			 */
			Response res=new UploadManager().put(filepath, key, uploadToken);
			if (res==null) {
				throw new BusinessException("上传文件失败");
			}
			if (res.statusCode==200) {//文件上传成功
				long newSize=ImageUtil.getImageSize(newfile);
				if (newSize==-1) {
					throw new BusinessException("获取上传后的文件大小失败，文件地址："+newfile);
				}
				/**
				 * 获取原图大小
				 */
				long originSize=urlConn.getContentLengthLong();
				/**
				 * 比较原图和新图大小是否相同，相同时认为文件上传完整，返回新图地址
				 */
				if ((originSize!=-1&&originSize==newSize)||(originSize==-1)) {
					logger.info("文件上传成功，文件地址："+fileUrl);
					return newfile;
				}
			}
			throw new BusinessException("文件上传失败，"+res.error+" "+filepath+" "+key+" "+uploadToken);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("上传失败，"+e.getMessage()+",fileUrl="+fileUrl);
			throw new BusinessException("上传失败，"+e.getMessage());
		} finally {
            if (urlConn!=null) {
            	urlConn.disconnect();
			}
		}
	}
	
	/**
	 * 获得文件地址或文件重定向地址的连接对象
	 * @author xule
	 * @version 2017年2月7日 上午11:30:55
	 * @param 
	 * @return HttpURLConnection
	 */
	private static HttpURLConnection getFileUrlConnection(String fileUrl) {
		HttpURLConnection urlConn=null;
		try {
			for(int i=0;;i++){
				URL url=new URL(fileUrl);
				urlConn = (HttpURLConnection) url.openConnection();
				urlConn.connect();
				urlConn.setConnectTimeout(5000);  
				/**
				 * 判断链接是否有效
				 */
				int responseCode = urlConn.getResponseCode();
				if (responseCode>=400) {
					throw new BusinessException("无效的链接地址："+fileUrl);
				}
				/**
				 * 获得重定向地址
				 */
				String location = urlConn.getHeaderField("Location");
				if (StringUtils.isEmpty(location)) {
					break;
				}
				if (i>0) {
					throw new BusinessException("图片地址包含多次重定向操作");
				}
				fileUrl=location;
			}
		} catch (Exception e) {
			throw new BusinessException("获取文件连接对象失败，"+e.getMessage());
		} 
		return urlConn;
	}
	
	/**
	 * @author xule
	 * @version 2017年2月7日 上午11:39:45
	 * @param 
	 * @return String
	 */
	private static String writeTempFile(HttpURLConnection urlConn) {
		InputStream is = null;
        FileOutputStream fos = null;
        String tempDir = System.getProperty("java.io.tmpdir");//获取临时文件夹目录
        String filepath = tempDir +UUID.randomUUID().toString();//生成唯一的临时文件名
        try {
        	is=urlConn.getInputStream();
			fos = new FileOutputStream(filepath);
			byte[] buffer = new byte[4096]; 
			int length;
			while ((length = is.read(buffer)) > 0) {
				fos.write(buffer, 0, length);
			}
		} catch (IOException e) {
			throw new BusinessException("文件写入失败，"+e.getMessage());
		} finally {
			if (is != null) {
        		try {
        			is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
            if (fos != null) {
                try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
		}          
		return filepath;
	}
	
	/**
	 * @author xule
	 * @version 2017年2月7日 下午2:16:43
	 * @param 
	 * @return 文件格式：.xxx
	 */
	private static String getFileFormat(String fileType, String fileUrl,HttpURLConnection conn) {
		if (FILETYPE_IMAGE.equals(fileType)) {
			return ImageUtil.getSuffix(fileUrl);
		}
		//TODO 其他文件类型获取文件格式的专属方法
		
		if (conn==null) {
			return null;
		}
		String contentType = conn.getContentType();
		if (StringUtils.isEmpty(contentType)) {
			InputStream is =null;
			try {
				is = conn.getInputStream();
				contentType=HttpURLConnection.guessContentTypeFromStream(is);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (is!=null) {
					try {
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		//TODO 其他获取文件格式的方法
		
		if (!StringUtils.isEmpty(contentType)) {
			String[] splits = contentType.split("/");
			if (splits.length==2) {
				return "."+splits[1];
			}
		}
		return null;
	}

	/**
	 * 获得文件上传token
	 * @author xule
	 * @version 2017年2月6日 上午10:05:53
	 * @param   上传类型 |0 任意类型|1 图片|2 音频|3 视频|
	 * @return String
	 */
	private static String getUploadToken(String fileHost,String fileName,String fileType) {
		int file_type=0;
		if (FILETYPE_IMAGE.equals(fileType)) {
			file_type=1;
		}else if (FILETYPE_AUDIO.equals(fileType)) {
			file_type=2;
		}else if (FILETYPE_VIDEO.equals(fileType)) {
			file_type=3;
		}
		try {
			String url=fileHost+"/football/file/token/upload?key=visitor&file_type="+file_type+"&file_name="+fileName;
			String result = HttpUtil.sendGet(url);
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