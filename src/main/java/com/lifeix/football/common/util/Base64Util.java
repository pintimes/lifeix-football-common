package com.lifeix.football.common.util;

import org.springframework.util.StringUtils;

public class Base64Util {
	
	public static String encoding(String s){
		if (StringUtils.isEmpty(s)) {
			return null;
		}
		return java.util.Base64.getEncoder().encodeToString(s.getBytes());
	}

}
