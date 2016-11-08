package com.lifeix.football.common.util;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

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
	
	public static String getFixlengthString(final String text,final int maxLength){
		if (StringUtils.isEmpty(text)) {
			return text;
		}
		int max= Math.max(1, maxLength);
		//评论内容超出50个字符
		int length = text.length();
		length = length>max?max:length;
		return text.substring(0,length);
	}
	
	/**
	 * 字符串转换成list
	 * @description
	 * @author zengguangwei 
	 * @version 2016年11月7日下午6:28:54
	 *
	 * @param s
	 * @param split
	 * @return
	 */
	public static List<String> strToList(String s){
		return strToList(s, ",");
	}
	
	/**
	 * 字符串转换成list
	 * @description
	 * @author zengguangwei 
	 * @version 2016年11月7日下午6:28:54
	 *
	 * @param s
	 * @param split
	 * @return
	 */
	public static List<String> strToList(String s,String split){
		if (StringUtils.isEmpty(s)) {
			return null;
		}
		if (StringUtils.isEmpty(split)) {
			return null;
		}
		String splitStr = split;
		String[] temps = s.split(splitStr);
		return Arrays.asList(temps);
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
