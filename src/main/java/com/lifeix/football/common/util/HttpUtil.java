package com.lifeix.football.common.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class HttpUtil {

	public static String sendDelete(String url) throws Exception {
		HttpDelete http = new HttpDelete(url);
		return sendHttp(http);
	}
	
	public static String sendGet(String url) throws Exception {
		HttpGet http = new HttpGet(url);
		return sendHttp(http);
	}

	public static String sendPost(String url, Map<String, String> map) throws Exception {
		HttpPost http = new HttpPost(url);
		http.setEntity(getEntity(map));
		return sendHttp(http);
	}

	public static String sendPut(String url, Map<String, String> map) throws Exception {
		HttpPut http = new HttpPut(url);
		http.setEntity(getEntity(map));
		return sendHttp(http);
	}

	private static UrlEncodedFormEntity getEntity(Map<String, String> map) throws UnsupportedEncodingException {
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		if (!CollectionUtils.isEmpty(map)) {
			Set<String> set = map.keySet();
			for (String key : set) {
				urlParameters.add(new BasicNameValuePair(key, map.get(key)));
			}
		}
		return new UrlEncodedFormEntity(urlParameters);
	}

	@SuppressWarnings("resource")
	public static String sendHttp(HttpUriRequest request) throws Exception {
		HttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(request);
		if (response.getEntity()==null) {
			return null;
		}
		if (StringUtils.isEmpty(response.getEntity().getContent())) {
			return null ;
		}
		InputStream is = response.getEntity().getContent();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		return result.toString();
	}
	
}
