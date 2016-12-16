package com.lifeix.football.common.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.lifeix.football.common.exception.IllegalparamException;

public class HttpUtil {
	public static final String CONTENT_TYPE="Content-Type";
	public static final String CONTENT_TYPE_APPLICATION_JSON="application/json;charset=UTF-8";
	public static final String CONTENT_TYPE_APPLICATION_TEXT="application/text;charset=UTF-8";
	public static final String CONTENT_TYPE_APPLICATION_URLENCODED="application/x-www-form-urlencoded";
	
	/**
	 * @throws Exception 
	 * @name sendHead 
	 * @description 发送head请求，判断链接是否可用
	 * @author xule
	 * @version 2016年10月27日 下午2:01:47
	 * @param 
	 * @return boolean 链接可用返回true，否则返回false
	 * @throws
	 */
	public static boolean sendHead(String urlStr) throws Exception{
		if (StringUtils.isEmpty(urlStr)) {
			return false;
		}
		URL url = new URL(urlStr);
		HttpURLConnection conn=(HttpURLConnection) url.openConnection();
		conn.setRequestMethod("HEAD");
		conn.connect(); 
		int resCode=conn.getResponseCode();
		conn.disconnect();
		if (resCode==404) {
			return false;
		}
		return true;
	}
	
	/**
	 * 发送http PATCH请求
	 * @name sendPatch
	 * @author xule
	 * @version 2016年11月1日 下午2:29:28
	 * @param 
	 * @return String
	 */
	public static String sendPatch(String url,Map<String, String> map) throws Exception {
		HttpPatch http=new HttpPatch(url);
		http.setEntity(getEntity(map));
		return sendHttp(http);
	}
	
	public static String sendDelete(String url) throws Exception {
		HttpDelete http = new HttpDelete(url);
		return sendHttp(http);
	}
	
	public static String sendDelete(String url,Map<String, String> headers) throws Exception {
		HttpDelete http = new HttpDelete(url);
		Set<String> keySet = headers.keySet();
		for (String key : keySet) {
			http.setHeader(key, headers.get(key));
		}
		return sendHttp(http);
	}
	
	public static String sendGet(String url) throws Exception {
		HttpGet http = new HttpGet(url);
		return sendHttp(http);
	}
	
	public static String sendGet(String url,Map<String, String> headers) throws Exception {
		HttpGet http = new HttpGet(url);
		Set<String> keySet = headers.keySet();
		for (String key : keySet) {
			http.setHeader(key, headers.get(key));
		}
		return sendHttp(http);
	}

	public static String sendPost(String url, Map<String, String> map) throws Exception {
		HttpPost http = new HttpPost(url);
		http.setEntity(getEntity(map));
		return sendHttp(http);
	}
	
	/**
	 * @name sendPost
	 * @description headers 请求头，为空或未设置content-type时，将自动设置content-type：application/json
	 * @author xule
	 * @version 2016年10月26日 下午5:05:20
	 * @param 
	 * @return String
	 * @throws
	 */
	public static String sendPost(String url,Map<String, String> headers, Map<String, String> params) throws Exception {
		HttpPost http = new HttpPost(url);
		http=(HttpPost) setHttpHeaders(http, headers);
		http.setEntity(getEntity(params));
		return sendHttp(http);
	}
	
	/**
	 * @name sendPost
	 * @description
	 * @author xule
	 * @version 2016年10月26日 下午4:05:36
	 * @param headers 请求头，为空或未设置content-type时，将自动设置content-type：application/json
	 * @return String
	 */
	public static String sendPost(String url,Map<String, String> headers, Object entity) throws Exception {
		if (StringUtils.isEmpty(url)) {
			throw new IllegalparamException("空url");
		}
		HttpPost http = new HttpPost(url);
		http=(HttpPost) setHttpHeaders(http, headers);
		if (entity!=null) {
			String json = JSONUtils.obj2jsonNotNull(entity);
			http.setEntity(new ByteArrayEntity(json.getBytes("UTF-8")));
		}
		return sendHttp(http);
	}
	
	
	public static String sendPut(String url, Map<String, String> map) throws Exception {
		HttpPut http = new HttpPut(url);
		http.setEntity(getEntity(map));
		return sendHttp(http);
	}
	
	/**
	 * @name sendPut
	 * @description
	 * @author xule
	 * @version 2016年10月26日 下午5:05:20
	 * @param headers 请求头，为空或未设置content-type时，将自动设置content-type：application/json
	 * @return String
	 * @throws
	 */
	public static String sendPut(String url,Map<String, String> headers, Map<String, String> params) throws Exception {
		HttpPut http = new HttpPut(url);
		http=(HttpPut) setHttpHeaders(http, headers);
		http.setEntity(getEntity(params));
		return sendHttp(http);
	}
	
	/**
	 * @name sendPut
	 * @description
	 * @author xule
	 * @version 2016年10月26日 下午4:05:36
	 * @param headers 请求头，为空或未设置content-type时，将自动设置content-type：application/json
	 * @return String
	 * @throws
	 */
	public static String sendPut(String url,Map<String, String> headers, Object entity) throws Exception {
		if (StringUtils.isEmpty(url)) {
			throw new IllegalparamException("空url");
		}
		HttpPut http = new HttpPut(url);
		http=(HttpPut) setHttpHeaders(http, headers);
		if (entity!=null) {
			String json = JSONUtils.obj2jsonNotNull(entity);
			http.setEntity(new ByteArrayEntity(json.getBytes("UTF-8")));
		}
		return sendHttp(http);
	}
	
	/**
	 * @name setHttpHeaders
	 * @description 
	 * @author xule
	 * @version 2016年10月26日 下午5:02:07
	 * @param headers 请求头，为空或未设置content-type时，将自动设置content-type：application/json
	 * @return Object
	 * @throws
	 */
	private static HttpEntityEnclosingRequestBase setHttpHeaders(HttpEntityEnclosingRequestBase http,Map<String, String> headers){
		if (CollectionUtils.isEmpty(headers)) {//未设置header时默认设置header: "Content-Type"="application/json"
			http.setHeader("Content-Type", "application/json");
		}else{
			Set<String> keySet = headers.keySet();
			for (String key : keySet) {
				http.setHeader(key, headers.get(key));
			}
			Header[] headers2 = http.getHeaders("Content-Type");
			if (headers2==null||headers2.length==0) {//未设置Content-Type时默认为"application/json"
				http.setHeader("Content-Type", "application/json");
			}
		}
		return http;
	}

	private static UrlEncodedFormEntity getEntity(Map<String, String> map) throws UnsupportedEncodingException {
		return getEntity("utf-8", map);
	}
	
	private static UrlEncodedFormEntity getEntity(String encode,Map<String, String> map) throws UnsupportedEncodingException {
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		if (!CollectionUtils.isEmpty(map)) {
			Set<String> set = map.keySet();
			for (String key : set) {
				urlParameters.add(new BasicNameValuePair(key, map.get(key)));
			}
		}
		return new UrlEncodedFormEntity(urlParameters, encode);
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
