/**
 * 
 */
package com.lifeix.football.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.io.IOUtils;
import org.springframework.util.StringUtils;

/**
 * @author xule
 */
public class ImageUtil {
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
		Set<String> imgSuffixSet=new HashSet<>();
		imgSuffixSet.add(".jpg");
		imgSuffixSet.add(".jpeg");
		imgSuffixSet.add(".png");
		imgSuffixSet.add(".gif");
		int index1 = image.indexOf("?");
		if (index1>=0) {
			image=image.substring(0, index1);
		}
		String suffix = image.substring(image.lastIndexOf("."));
		if (StringUtils.isEmpty(suffix)||!imgSuffixSet.contains(suffix)) {//当前图片后缀为空，或者不是合法格式的图片，全部转换成.jpg格式的图片
			suffix=".jpg";
		}
		return suffix;
	}

	/**
	 * @name readData
	 * @description 从图片地址读取图片内容到内存中
	 * @author xule
	 * @version 2016年10月8日 上午11:26:13
	 * @param 
	 * @return byte[]
	 * @throws
	 */
	public static byte[] readData(String url){
		try {
			URL URL;
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
	public static String calcETag(byte[] fileData) throws IOException,NoSuchAlgorithmException {
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

}
