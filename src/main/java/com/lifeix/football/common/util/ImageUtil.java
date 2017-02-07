/**
 * 
 */
package com.lifeix.football.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.lifeix.football.common.exception.BusinessException;
import com.squareup.okhttp.Response;

/**
 * @author xule
 */
public class ImageUtil {
	private static Logger logger = LoggerFactory.getLogger(ImageUtil.class);
	public static final Set<String> IMAGE_SUFFIX_SET=new HashSet<>(Arrays.asList(".jpg",".jpeg",".png",".gif"));
	
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
		if (StringUtils.isEmpty(imgUrl)) {
			return null;
		}
		String image=imgUrl;
		int index1 = image.indexOf("?");
		if (index1>=0) {
			image=image.substring(0, index1);
		}
		int index2 = image.indexOf("!");
		if (index2>=0) {
			image=image.substring(0, index2);
		}
		String suffix = image.substring(image.lastIndexOf("."));
		if (StringUtils.isEmpty(suffix)||!IMAGE_SUFFIX_SET.contains(suffix)) {//当前图片后缀为空，或者不是合法格式的图片，全部转换成.jpg格式的图片
			suffix=".jpg";
		}
		return suffix;
	}

	/**
	 * @name readData
	 * @description 从图片地址读取图片内容到内存中
	 * @author xule
	 * @version 2016年10月8日 上午11:26:13
	 * @return byte[]
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
		return null;
	}
	
	/**
	 * 写入图片到临时文件中并返回文件全路径
	 * @author xule
	 * @version 2017年2月3日 下午5:33:42
	 * @param 
	 * @return byte[]
	 */
	public static String writeAndGetImagePath(String url){
		if (StringUtils.isEmpty(url)) {
			logger.error("图片地址为空，读取图片内容失败");
			return null;
		}
		return FileUtil.writeTempFile(url);
	}

	/**
	 * 图片有效性检查，有效时返回true，否则返回false
	 * @author xule
	 * @version 2017年2月3日 上午10:44:45
	 * @param 
	 * @return String
	 */
	public static boolean availabilityCheck(String url) {
		try {
			Response response = OKHttpUtil.get(url, null);
			int code = response.code();
			if (code<400&&code>=200) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 通过url获得图片
	 * @author xule
	 * @version 2017年2月3日 上午10:03:19
	 * @param 
	 * @return int
	 */
	public static long getImageSize(String url) {
		HttpURLConnection conn=null;
		try {
			conn = (HttpURLConnection)new URL(url).openConnection();
			if (conn==null) {
				return -1;
			}
			long contentLength = conn.getContentLengthLong();
			return contentLength;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn!=null) {
				conn.disconnect();
			}
		}
		return -1;
	}
	
	public static void main(String[] args) {
		HttpURLConnection conn=null;
		String url="http://123.jpg";
		try {
			conn = (HttpURLConnection)new URL(url).openConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(conn);
		try {
			System.out.println(conn.getResponseMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
