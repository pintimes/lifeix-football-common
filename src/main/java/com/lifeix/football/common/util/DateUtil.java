package com.lifeix.football.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    private static final String DATE_FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";

    public static String toDate(Date d) {
        DateFormat format = new SimpleDateFormat(DATE_FORMAT_DEFAULT);
        return format.format(d);
    }

    public static String toDate(Date d, String form) {
        DateFormat format = new SimpleDateFormat(form);
        return format.format(d);
    }
}
