package com.lifeix.football.common.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lifeix.football.common.exception.IllegalparamException;
import com.lifeix.football.common.model.Consumer;
/**
 * API网关注册
 * @author zengguangwei
 *
 */
public class APIGatewayUtil {
	
	
	/**
	 * 注册用户到API网关 返回用户的auth key
	 * 
	 * @description
	 * @version 2016年4月19日上午10:17:10
	 *
	 * @param user
	 * @throws Exception 
	 */
	public static String registToAPIGateway(String host, String userId,String group) throws Exception {
		if (StringUtils.isEmpty(host)) {
			throw new IllegalparamException("host is empty");
		}
		if (StringUtils.isEmpty(userId)) {
			throw new IllegalparamException("consumer.userId is empty");
		}
		if (StringUtils.isEmpty(group)) {
			throw new IllegalparamException("consumer.group is empty");
		}
		String custom_id = userId;
		String username = userId;
		
		/**
		 * 检索Consumer
		 */
		Consumer consumer = retrieveConsumer(host, username);
		if (consumer == null) {
			/**
			 * create Consumer to APIGateway /consumers/ PUT
			 */
			 consumer = createConsumer(host, custom_id,username);
		}
		String kongId = consumer.getId();
		/**
		 * 检索Consumer的Key
		 */
		String key = retrieveConsumerKey(host,kongId);
		if (!StringUtils.isEmpty(key)) {
			return key;
		}
		/**
		 * create a Key for Consumer create /consumers/{id}/key-auth
		 */
		 key = keyAuthConsumer(host, kongId);
		/**
		 * Add Consumer to UserGroup
		 */
		addConsumerToUserGroup(host, kongId, group);
		return key;
	}

	/**
	 * 删除Consumer 同时关联删除Key以及Group等
	 * @description
	 * @author zengguangwei 
	 * @version 2016年5月13日下午3:23:32
	 *
	 * @param host
	 * @param dto
	 * @throws Exception
	 */
	private static void deleteConsumer(String host, Consumer dto) throws Exception {
		HttpUtil.sendDelete(host + "/consumers/" + dto.getCustom_id());
	}

	/**
	 * 参见kong https://getkong.org/plugins/acl/
	 * 
	 * @description
	 * @author zengguangwei
	 * @version 2016年5月13日上午11:59:24
	 *
	 * @param host
	 * @param consumer
	 * @param group
	 * @return
	 * @throws Exception 
	 */
	private static String addConsumerToUserGroup(String host, String consumerId, String groups) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("group", groups);
		String result = HttpUtil.sendPost(host + "/consumers/" + consumerId + "/acls", params);
		Map<String, Object> map=new HashMap<>();
		map = JSONUtils.json2map(result);
		Object key=map.get("group");
		if (key!=null) {
			return key.toString();
		}
		return null;
	}

	/**
	 * 参见kong https://getkong.org/plugins/key-authentication/
	 * 
	 * @description
	 * @author zengguangwei
	 * @version 2016年5月13日上午11:59:08
	 *
	 * @param host
	 * @param consumer
	 * @return
	 * @throws Exception 
	 */
	private static String keyAuthConsumer(String host, String id) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		String result = HttpUtil.sendPut(host + "/consumers/" + id + "/key-auth", params);
		Map<String, Object> map=new HashMap<>();
		map = JSONUtils.json2map(result);
		Object key=map.get("key");
		if (key!=null) {
			return key.toString();
		}
		return null;
	}

	/**
	 * 检索
	 * @description
	 * @author zengguangwei 
	 * @version 2016年9月22日下午7:42:41
	 *
	 * @param host
	 * @param username
	 * @return
	 * @throws Exception
	 */
	private static Consumer retrieveConsumer(String host, String username) throws Exception {
		String result = HttpUtil.sendGet(host + "/consumers/" + username);
		if (StringUtils.isEmpty(result)) {
			return null;
		}
		try {
			return JSONUtils.json2pojo(result, Consumer.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * https://getkong.org/docs/0.8.x/admin-api/#update-or-create-consumer
	 * 
	 * @description
	 * @author zengguangwei
	 * @version 2016年5月13日下午12:00:10
	 *
	 * @param host
	 * @param consumer
	 * @return
	 * @throws Exception 
	 */
	private static Consumer createConsumer(String host, String custom_id,String username) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		/**
		 * 将用户Id作为用户名传入，用户名可能会有重名的情况
		 */
		params.put("custom_id", custom_id);
		params.put("username", username);
		String result = HttpUtil.sendPut(host + "/consumers", params);
		return JSONUtils.json2pojo(result, Consumer.class);
	}

	public static String getAPIGatewayHost() {
		Map<String, String> sysEnvs = EnvironmentUtil.getSysEnvs();
		String host = sysEnvs.get("HOST_APIGATEWAY");
		return host;
	}


//
//	private void deleteConsumerKey(String host, Consumer consumer) {
//		HttpUtil.sendDelete(host + "/consumers/" + consumer.getId() + "/key-auth");
//	}
//
	private static String retrieveConsumerKey(String host, String consumer_id) {
		// {"data":[{"created_at":1463119688000,"consumer_id":"12148d96-695b-46d6-a4af-916e4ca47810","key":"56d99579e4a544a88b3ed34352724b25","id":"834819af-cddc-47b8-8e3c-642cce328987"},{"created_at":1463120654000,"consumer_id":"12148d96-695b-46d6-a4af-916e4ca47810","key":"2045e025e9994862ba12127fc60f3dc5","id":"10abb63a-c494-4480-a7d0-5cda69d440d5"}],"total":2}
		try {
			String result = HttpUtil.sendGet(host + "/consumers/" + consumer_id + "/key-auth");
			if (StringUtils.isEmpty(result)) {
				return null;
			}
			 JSONObject resultJSON = JSONObject.parseObject(result);
			 if (resultJSON==null) {
				return null;
			}
			 JSONArray datasJSON = resultJSON.getJSONArray("data");
			if (datasJSON == null) {
				return null ;
			}
			JSONObject dataJSON = datasJSON.getJSONObject(0);
			if (dataJSON==null) {
				return null;
			}
			return dataJSON.getString("key");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null ;
	}
	
	
	public static void main(String[] args) {
		try {
			String host = "http://192.168.1.17:8001";

			String userId = "zengguangweiUserId";
			String key = APIGatewayUtil.registToAPIGateway(host, userId,"user");
			String newKey = APIGatewayUtil.registToAPIGateway(host, userId,"user");
			System.out.println(key+"--"+newKey);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
