package com.lifeix.football.common.util;
/**
 * @author  zengguangwei
 * 
 */
public class StringUtil {

    
    public static String firstUpcase(String name) {
        String first = name.substring(0, 1);
        return name.replaceFirst(first, first.toUpperCase());
    }
}
