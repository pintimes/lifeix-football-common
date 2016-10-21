package com.lifeix.football.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.hibernate.validator.internal.util.privilegedactions.GetAnnotationParameter;
import org.springframework.util.CollectionUtils;

/**
 * @description 发送https请求工具类，无论网站是否具有受信任的证书，都会将其设置为受信任的网站，因此使用该工具类需要注意请求链接的安全问题
 * @author xule
 */
public class HttpsUtil {
	
	/**
	 * @description 
	 * @author xule
	 */
	private static class TrustAnyTrustManager implements X509TrustManager {
	    
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
    
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
    
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }
    
	/**
	 * @description 
	 * @author xule
	 */
    private static class TrustAnyHostnameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    /**
     * @name getSSLContext
     * @description 获得SSLContext
     * @author xule
     * @version 2016年9月27日 下午4:54:21
     * @param 
     * @return SSLContext
     * @throws
     */
    private static SSLContext getSSLContext(){
    	SSLContext sc=null;
		try {
			sc = SSLContext.getInstance("SSL");
			sc.init(null, new TrustManager[]{new TrustAnyTrustManager()}, new java.security.SecureRandom());
		} catch (NoSuchAlgorithmException | KeyManagementException e) {
			e.printStackTrace();
		}
        return sc;
    }
    /**
     * @name getResult
     * @description 获得请求结果字符串，请求失败返回null
     * @author xule
     * @version 2016年9月27日 下午4:54:21
     * @param 
     * @return SSLContext
     * @throws
     */
    private static String getResult(HttpsURLConnection conn){
    	try {
			if (conn.getResponseCode() == 200) {  
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));  
				String inputLine;  
				String result="";
				while ((inputLine = in.readLine()) != null) {  
					result += inputLine;  
				}  
				in.close();  
				return result;  
			}else {
				return conn.getResponseCode()+" "+conn.getResponseMessage();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    /**
	 * @name getParam
	 * @description 根据post或put请求map拼接参数
	 * @author xule
	 * @version 2016年10月21日 上午11:26:46
	 * @param 
	 * @return String
	 * @throws 
	 */
	private static String getParam(Map<String, Object> map) {
		if (CollectionUtils.isEmpty(map)) {
			return "";
		}
		String param="";
    	Set<String> keySet = map.keySet();
    	for (String key : keySet) {
			if (!"".equals(param)) {//不是一个参数，需要拼接“&”
				param+="&"+key+"="+map.get(key);
			}else{//是一个参数，需要拼接“?”
				param+="?"+key+"="+map.get(key);
			}
		}
    	return param;
	}
	
	
	/**
     * @name sendGet
     * @description 发送https的get请求
     * @author xule
     * @version 2016年9月27日 下午4:46:13
     * @param link 请求地址，也就是url
     * @return String 请求失败返回null
     * @throws Exception
     */
    public static String sendGet(String link) throws Exception{
    	URL url = new URL(link);
    	HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
    	conn.setSSLSocketFactory(getSSLContext().getSocketFactory());
    	conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
    	conn.connect();  
    	return getResult(conn);
    }
	
    /**
     * @name sendPost
     * @description 发送https的post请求
     * @author xule
     * @version 2016年9月27日 下午4:46:13
     * @param link 请求地址，也就是url
     * 		  param 请求参数，用'&'拼接成字符串（应该有更好的方式，但是我还没有找到）
     * @return String 请求失败返回null
     * @throws Exception
     */
    public static String sendPost(String link , Map<String, Object> map) throws Exception{
    	String param=getParam(map);
        URL url = new URL(link+param);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setSSLSocketFactory(getSSLContext().getSocketFactory());
        conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
        conn.setDoInput(true);  
        conn.setDoOutput(true);  
        conn.setUseCaches(false);  
        conn.setConnectTimeout(50000);//设置连接超时
        conn.setReadTimeout(50000);//设置读取超时
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");  
        conn.connect();  
		return getResult(conn);  
    }
    
    /**
     * @name sendPut
     * @description 发送https的put请求
     * @author xule
     * @version 2016年9月27日 下午4:46:13
     * @param link 请求地址，也就是url
     * 		  param 请求参数，用'&'拼接成字符串（应该有更好的方式，但是我还没有找到）
     * @return String 请求失败返回null
     * @throws Exception
     */
    public static String sendPut(String link, Map<String, Object> map) throws Exception{
    	String param=getParam(map);
    	URL url = new URL(link+param);
    	HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
    	conn.setSSLSocketFactory(getSSLContext().getSocketFactory());
    	conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
    	conn.setRequestMethod("PUT");
    	conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");  
    	conn.connect();  
    	return getResult(conn);
    }
    
//    /**
//     * @name sendDelete
//     * @description 发送https的delete请求
//     * @author xule
//     * @version 2016年9月27日 下午4:46:13
//     * @param link 请求地址，也就是url
//     * 		  param 请求参数，用'&'拼接成字符串（应该有更好的方式，但是我还没有找到）
//     * @return String 请求失败返回null
//     * @throws Exception
//     */
//    public static String sendDelete(String link, String param) throws Exception{
//    	URL url = new URL(link+"?"+param);
//    	HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
//    	conn.setSSLSocketFactory(getSSLContext().getSocketFactory());
//    	conn.setHostnameVerifier(new TrustAnyHostnameVerifier());
//    	conn.setRequestMethod("DELETE");
//    	conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");  
//    	conn.connect();  
//    	return getResult(conn);
//    }
}
