package com.lifeix.football.common.util;

import org.springframework.util.StringUtils;

public class Base64Util {
	/**
	 * 编码
	 */
	public static String encoding(String s){
		if (StringUtils.isEmpty(s)) {
			return null;
		}
		return java.util.Base64.getEncoder().encodeToString(s.getBytes());
	}
	
	/**
	 * 解码
	 * @author xule
	 * @version 2017年3月16日  上午10:59:46
	 * @param 
	 * @return String
	 */
	public static String decoding(String s){
		if (StringUtils.isEmpty(s)) {
			return null;
		}
		return new String(java.util.Base64.getDecoder().decode(s));
	}

}
