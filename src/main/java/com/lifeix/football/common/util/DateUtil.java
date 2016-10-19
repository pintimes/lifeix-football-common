package com.lifeix.football.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
	
	public static String toUTCStr(Date d) {
		DateFormat format = new SimpleDateFormat(UTC_FORMAT_DEFAULT);
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
		return format.format(d);
	}
	
	public static Date engToDate(String dateStr) {
		DateFormat format = new SimpleDateFormat(ENG_FORMAT_DEFAULT,Locale.ENGLISH);
		try {
			return format.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) throws ParseException {
//		String dateStr = "Thu Jun 30 09:15:27 +0800 2016";
//		SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd HH:mm:ss ZZZZ yyyy",Locale.ENGLISH);
		
//		String dateStr = "星期四 一月 15 08:42:01 +0800 1970";
//		DateFormat sdf = new SimpleDateFormat("E MMM dd HH:mm:ss ZZZZ yyyy");
		// Use UTC as the default time zone.
		
//		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
//		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//		Date date = dateFormat.parse(dateStr);
		
//		SimpleDateFormat sdf = new SimpleDateFormat("E MMM dd HH:mm:ss ZZZZ yyyy",Locale.ENGLISH);
//		String format2 = sdf.format(new Date(1467249327000l));
//		System.out.println(format2);
		
		Date date = engToDate("Sun Oct 16 12:47:13 +0800 2016");
		System.out.println(DateUtil.toTimeStr(date));
	}

}
