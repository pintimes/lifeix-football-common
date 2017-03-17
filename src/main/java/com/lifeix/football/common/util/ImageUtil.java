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
import org.springframework.util.StringUtils;
import com.squareup.okhttp.Response;

/**
 * @author xule
 */
public class ImageUtil {
	public static final Set<String> IMAGE_SUFFIX_SET=new HashSet<>(Arrays.asList(".jpg",".jpeg",".png",".gif",".bmp"));
	
	/**
 	 * @name getSuffix
	 * @description 根据图片链接地址获得图片后缀名，地址中不存在图片后缀名或者不是规范图片格式的图片给全部默认为jpg格式的图片
	 * 				规范图片格式包括:jpg,jpeg,png,gif
	 * @author xule
	 * @version 2016年10月27日 上午11:50:31
	 * @param 
	 * @return String
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
		InputStream is =null;
		try {
			URL = new URL(url);
			is = URL.openStream();
			byte[] b = IOUtils.toByteArray(is);//获得图片
			is.close();
			return b;
		} catch (Exception e) {
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
		return null;
	}
}
