/**
 * 
 */
package com.lifeix.football.common.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.io.IOUtils;
import org.springframework.util.StringUtils;

import com.qiniu.common.Config;

/**
 * @author xule
 */
public class ImageUtil {
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
	 * @throws Exception 
	 * @name readData
	 * @description 从图片地址读取图片内容到内存中
	 * @author xule
	 * @version 2016年10月8日 上午11:26:13
	 * @param 
	 * @return byte[]
	 * @throws
	 */
	public static byte[] readData(String url) throws Exception{
			URL URL;
			URL = new URL(url);
			InputStream is = URL.openStream();
			byte[] b = IOUtils.toByteArray(is);//获得图片
			is.close();
			return b;
	}
	
}
