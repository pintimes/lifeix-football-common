package com.lifeix.football.common.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SmsCodeUtil {

    public static String[] BEFORESHUFFLE = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };

    public static String generateSmsCode() {
	List<String> list = Arrays.asList(BEFORESHUFFLE);
	Collections.shuffle(list);
	StringBuilder sb = new StringBuilder();
	for (int i = 0; i < list.size(); i++) {
	    sb.append(list.get(i));
	}
	String afterShuffle = sb.toString();
	String result = afterShuffle.substring(2, 8);
	return result;
    }

}
