package com.lifeix.football.common.util;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.util.StringUtils;

import com.squareup.okhttp.ConnectionPool;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class OKHttpUtil {

    public static final String JSON_MIME = "application/json";
    public static final String FORM_MIME = "application/x-www-form-urlencoded";

    private static final OkHttpClient httpClient = new OkHttpClient();

    static {
	httpClient.setConnectTimeout(30, TimeUnit.SECONDS);
	ConnectionPool connectionPool = new ConnectionPool(32, 5 * 60 * 1000);
	httpClient.setConnectionPool(connectionPool);
    }

    public static boolean head(String url) {
	if (StringUtils.isEmpty(url)) {
	    return false;
	}
	Request.Builder requestBuilder = new Request.Builder().head().url(url);
	Response response = null;
	try {
	    response = httpClient.newCall(requestBuilder.build()).execute();
	} catch (IOException e) {
	    return false;
	}
	boolean result = response.isSuccessful();
	try {
		response.body().close();
	} catch (IOException e) {
		e.printStackTrace();
	}
	return result;
    }

    public static Response get(String url, Map<String, Object> headers) throws Exception {
	Request.Builder requestBuilder = new Request.Builder().get().url(url);
	if (headers != null && headers.size() > 0) {
	    headers.forEach((key, value) -> {
		requestBuilder.header(key, value.toString());
	    });
	}
	Response response = httpClient.newCall(requestBuilder.build()).execute();
	return response;
    }

    public static Response post(String url, Map<String, Object> headers, Map<String, Object> params) throws Exception {
	Request.Builder requestBuilder = new Request.Builder().url(url);
	if (headers != null && headers.size() > 0) {
	    headers.forEach((key, value) -> {
		requestBuilder.header(key, value.toString());
	    });
	}
	requestBuilder.header("Content-Type", FORM_MIME);
	if (params != null && params.size() > 0) {
	    FormEncodingBuilder builder = new FormEncodingBuilder();
	    params.forEach((key, value) -> {
		builder.add(key, value.toString());
	    });
	    requestBuilder.post(builder.build());
	}
	Response response = httpClient.newCall(requestBuilder.build()).execute();
	return response;
    }

    public static Response post(String url, Map<String, Object> headers, Object entity) throws Exception {
	Request.Builder requestBuilder = new Request.Builder().url(url);
	if (headers != null && headers.size() > 0) {
	    headers.forEach((key, value) -> {
		requestBuilder.header(key, value.toString());
	    });
	}
	MediaType mediaType = MediaType.parse(JSON_MIME + "; charset=utf-8");
	requestBuilder.header("Content-Type", mediaType.toString());
	if (entity != null) {
	    String jsonStr = "";
	    try {
		jsonStr = JSONUtils.obj2jsonNotNull(entity);
	    } catch (Exception e) {
	    }
	    requestBuilder.post(RequestBody.create(mediaType, jsonStr));
	}
	Response response = httpClient.newCall(requestBuilder.build()).execute();
	return response;
    }

    public static Response put(String url, Map<String, Object> headers, Map<String, Object> params) throws Exception {
	Request.Builder requestBuilder = new Request.Builder().url(url);
	if (headers != null && headers.size() > 0) {
	    headers.forEach((key, value) -> {
		requestBuilder.header(key, value.toString());
	    });
	}
	requestBuilder.header("Content-Type", FORM_MIME);
	if (params != null && params.size() > 0) {
	    FormEncodingBuilder builder = new FormEncodingBuilder();
	    params.forEach((key, value) -> {
		builder.add(key, value.toString());
	    });
	    requestBuilder.put(builder.build());
	}
	Response response = httpClient.newCall(requestBuilder.build()).execute();
	return response;
    }

    public static Response put(String url, Map<String, Object> headers, Object entity) throws Exception {
	Request.Builder requestBuilder = new Request.Builder().url(url);
	if (headers != null && headers.size() > 0) {
	    headers.forEach((key, value) -> {
		requestBuilder.header(key, value.toString());
	    });
	}
	MediaType mediaType = MediaType.parse(JSON_MIME + "; charset=utf-8");
	requestBuilder.header("Content-Type", mediaType.toString());
	if (entity != null) {
	    String jsonStr = "";
	    try {
		jsonStr = JSONUtils.obj2jsonNotNull(entity);
	    } catch (Exception e) {
	    }
	    requestBuilder.put(RequestBody.create(mediaType, jsonStr));
	}
	Response response = httpClient.newCall(requestBuilder.build()).execute();
	return response;
    }

    public static Response delete(String url, Map<String, Object> headers) throws Exception {
	Request.Builder requestBuilder = new Request.Builder().delete().url(url);
	if (headers != null && headers.size() > 0) {
	    headers.forEach((key, value) -> {
		requestBuilder.header(key, value.toString());
	    });
	}
	Response response = httpClient.newCall(requestBuilder.build()).execute();
	return response;
    }
}
