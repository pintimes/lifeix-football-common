package com.lifeix.football.common.exception;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.springframework.util.CollectionUtils;
import com.lifeix.football.common.exception.BaseException;
import com.lifeix.football.common.util.HttpUtil;
import com.lifeix.football.common.util.HttpsUtil;
import com.lifeix.football.common.util.ProfileUtil;

public class SystemException extends BaseException {
	private static final long serialVersionUID = 8327543447249465048L;
	
	public SystemException(String error) {
		super(error);
	}
	
	public SystemException(int code, String error) {
		super(String.valueOf(code),error);
	}
	
	public SystemException(String error,String mobile) {
		super(error);
		sendNotice("", mobile, error,null);
	}
	
	public SystemException(String error,String system, String mobile,Logger logger) {
		super(error);
		sendNotice(system, mobile, error,logger);
	}
	
	public SystemException(String error,String system, String mobile,String email) {
		super(error);
		sendNotice(system, mobile, error,null);
	}
	
	public SystemException(String error,String system, String mobile,String email,String msg) {
		super(error);
		sendNotice(system, mobile, msg,null);
	}
	
	public SystemException(int code, String error,String system, String mobile,String email,String msg) {
		super(String.valueOf(code),error);
		sendNotice(system, mobile, msg,null);
	}
	
	public SystemException(int code, String error, String system,String mobile,String email,String mobileMsg,String emailMsg) {
		super(String.valueOf(code),error);
		sendNotice(system, mobile, mobileMsg,null);
	}

	private static final Map<String,String> hostMap;
	private static final Map<String,String> keyMap;
	private static String link="";
	static{
		hostMap=new HashMap<>();
		hostMap.put("dev", "http://54.223.127.33:8300/");
		hostMap.put("qa", "http://54.223.127.33:8300/");
		hostMap.put("pr", "https://api.c-f.com/");
		keyMap=new HashMap<>();
		keyMap.put("dev", "admin");
		keyMap.put("qa", "admin");
		keyMap.put("pr", "1066f341ba1c4f2f9830579beae07135");
		String[] profiles = ProfileUtil.getProfiles("PROFILE_ENV");
		if (profiles!=null&&profiles.length>0) {
			Set<String> envSet=new HashSet<>(Arrays.asList(profiles));
			String env="";
			if (!CollectionUtils.isEmpty(envSet)) {
				if (envSet.contains("dev")) {
					env="dev";
				}else if (envSet.contains("qa")) {
					env="qa";
				}else{
					env="pr";
				}
				link=hostMap.get(env)+"football/user/notice/message?key="+hostMap.get(env);
			}
		}
	}
	
	private SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
	private void sendNotice(String system,String mobile,String reason,Logger logger){
		logger.info("link="+link);
		logger.info("system="+system);
		logger.info("mobile="+mobile);
		logger.info("reason="+reason);
		
		try {
			Map<String, Object> map=new HashMap<>();
			map.put("system", system);
			map.put("time",simpleDateFormat.format(new Date()));
			map.put("mobile", mobile);
			map.put("reason", reason);
			HttpsUtil.sendPost(link, map);
		} catch (Exception e) {
			try {
				Map<String, String> map=new HashMap<>();
				map.put("system", system);
				map.put("time",simpleDateFormat.format(new Date()));
				map.put("mobile", mobile);
				map.put("reason", reason);
				HttpUtil.sendPost(link, map);
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

}