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
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import com.lifeix.football.common.exception.IllegalparamException;

public class HttpUtil {
	public static final String CONTENT_TYPE_APPLICATION_JSON="application/json;charset=UTF-8";
	public static final String CONTENT_TYPE_APPLICATION_TEXT="application/text;charset=UTF-8";
	public static final String CONTENT_TYPE_APPLICATION_URLENCODED="application/x-www-form-urlencoded";
	
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
	
	/**
	 * @name sendPost
	 * @description
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
	 * @param 
	 * @return String
	 * @throws
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
	 * @param headers 请求头，为空时自动设置content-type：application/json
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
	 * @param 
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
	 * @param 
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
		List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
		if (!CollectionUtils.isEmpty(map)) {
			Set<String> set = map.keySet();
			for (String key : set) {
				urlParameters.add(new BasicNameValuePair(key, map.get(key)));
			}
		}
		return new UrlEncodedFormEntity(urlParameters, "utf-8");
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
