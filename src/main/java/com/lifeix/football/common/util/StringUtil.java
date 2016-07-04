package com.lifeix.football.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zengguangwei
 * 
 */
public class StringUtil {

	public static String firstUpcase(String name) {
		String first = name.substring(0, 1);
		return name.replaceFirst(first, first.toUpperCase());
	}
	
	public static String replaceRow(String str) {
		String dest = "";
		if (str != null) {//"\t|\r|\n"
			Pattern p = Pattern.compile("\t|\n|\r");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	/**
	 * 注：n 回车(u000a) t 水平制表符(u0009) s 空格(u0008) r 换行(u000d)
	 * @description
	 * @author zengguangwei 
	 * @version 2016年7月1日下午1:40:14
	 *
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|t|r|n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	public static void main(String[] args) {
//		String content = FileUtil.readFileContent("d:/shujutongji - 副本.html");
		String content = FileUtil.readFileContent("D:/chuchangjilu - 副本.html");
		System.out.println(content);
		System.out.println("-------------------------------");
		String result = replaceRow(content);
		System.out.println(result);
	}
}
