package com.lifeix.football.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil {
	
	//参考https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html

	private static final String DATETIME_FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";

	private static final String DATE_FORMAT_DEFAULT = "yyyy-MM-dd";
	
	private static final String UTC_FORMAT_DEFAULT = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
	
	private static final String ENG_FORMAT_DEFAULT = "E MMM dd HH:mm:ss ZZZZ yyyy";
	

	public static String toDateStr(Date d) {
		DateFormat format = new SimpleDateFormat(DATE_FORMAT_DEFAULT);
		return format.format(d);
	}

	public static String toTimeStr(Date d) {
		DateFormat format = new SimpleDateFormat(DATETIME_FORMAT_DEFAULT);
		return format.format(d);
	}
	
	/**
	 * 将时间转换为"协调世界时"
	 */
	public static String toUTCStr(Date d) {
		DateFormat format = new SimpleDateFormat(UTC_FORMAT_DEFAULT);
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
		return format.format(d);
	}
	
	/**
	 * @param dateStr 如： Thu Mar 16 14:54:41 CST 2017
	 */
	public static Date engToDate(String dateStr) {
		DateFormat format = new SimpleDateFormat(ENG_FORMAT_DEFAULT,Locale.ENGLISH);
		try {
			return format.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}
