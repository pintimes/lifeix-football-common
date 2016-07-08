package com.lifeix.football.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {
	
	//参考https://docs.oracle.com/javase/7/docs/api/java/text/SimpleDateFormat.html

	private static final String DATETIME_FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";

	private static final String DATE_FORMAT_DEFAULT = "yyyy-MM-dd";

	public static String toDateStr(Date d) {
		DateFormat format = new SimpleDateFormat(DATE_FORMAT_DEFAULT);
		return format.format(d);
	}

	public static String toTimeStr(Date d) {
		DateFormat format = new SimpleDateFormat(DATETIME_FORMAT_DEFAULT);
		return format.format(d);
	}

	public static void main(String[] args) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		// Use UTC as the default time zone.
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		String format2 = dateFormat.format(new Date(1472727600000l));
		System.out.println(format2);
	}

}
