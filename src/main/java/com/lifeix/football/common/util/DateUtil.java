package com.lifeix.football.common.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

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

}
