package com.lifeix.football.common.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

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
	public static String registToAPIGateway(String host, Consumer dto) throws Exception {
		if (StringUtils.isEmpty(host)) {
			throw new IllegalparamException("host is empty");
		}
		if (dto == null) {
			throw new IllegalparamException("consumer is empty");
		}
		if (StringUtils.isEmpty(dto.getCustom_id())) {
			throw new IllegalparamException("consumer.custom_id is empty");
		}
		if (StringUtils.isEmpty(dto.getGroups())) {
			throw new IllegalparamException("consumer.group is empty");
		}
		/**
		 * 删除Consummer
		 */
		deleteConsumer(host, dto);
		/**
		 * create or update Consumer to APIGateway /consumers/ PUT
		 */
		Consumer consumer = saveOrUpdateConsumer(host, dto.getCustom_id(),dto.getUsername());
		/**
		 * create or update a Key for Consumer create /consumers/{id}/key-auth
		 */
		String key = keyAuthConsumer(host, consumer);
		/**
		 * Add Consumer to UserGroup
		 */
		addConsumerToUserGroup(host, consumer.getId(), dto.getGroups());
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
	private static String addConsumerToUserGroup(String host, String consumerId, String group) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("group", group);
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
	private static String keyAuthConsumer(String host, Consumer consumer) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		String result = HttpUtil.sendPut(host + "/consumers/" + consumer.getId() + "/key-auth", params);
		Map<String, Object> map=new HashMap<>();
		map = JSONUtils.json2map(result);
		Object key=map.get("key");
		if (key!=null) {
			return key.toString();
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
	private static Consumer saveOrUpdateConsumer(String host, String custom_id,String username) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		/**
		 * 将用户Id作为用户名传入，用户名可能会有重名的情况
		 */
		params.put("username", username);
		params.put("custom_id", custom_id);
		String result = HttpUtil.sendPut(host + "/consumers", params);
		return JSONUtils.json2pojo(result, Consumer.class);
	}

	public static String getAPIGatewayHost() {
		Map<String, String> sysEnvs = EnvironmentUtil.getSysEnvs();
		String host = sysEnvs.get("HOST_APIGATEWAY");
		return host;
	}

//	private Consumer retrieveConsumer(String host, String custom_id) {
//		String result = HttpUtil.sendGet(host + "/consumers/" + custom_id);
//		if (StringUtils.isEmpty(result)) {
//			return null;
//		}
//		try {
//			return JSONUtils.json2pojo(result, Consumer.class);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	private void deleteConsumerKey(String host, Consumer consumer) {
//		HttpUtil.sendDelete(host + "/consumers/" + consumer.getId() + "/key-auth");
//	}
//
//	private String retrieveConsumerKey(String host, String consumer_id) {
//		Map<String, String> params = new HashMap<String, String>();
//		String result = HttpUtil.sendGet(host + "/consumers/" + consumer_id + "/key-auth");
//		// {"data":[{"created_at":1463119688000,"consumer_id":"12148d96-695b-46d6-a4af-916e4ca47810","key":"56d99579e4a544a88b3ed34352724b25","id":"834819af-cddc-47b8-8e3c-642cce328987"},{"created_at":1463120654000,"consumer_id":"12148d96-695b-46d6-a4af-916e4ca47810","key":"2045e025e9994862ba12127fc60f3dc5","id":"10abb63a-c494-4480-a7d0-5cda69d440d5"}],"total":2}
//		return result;
//	}
	public static void main(String[] args) {
		try {
			APIGatewayUtil util = new APIGatewayUtil();
			String host = "http://192.168.1.17:8001";

			Consumer consumer = new Consumer();
			consumer.setGroups("admin");
			String name = "zengguangwei3";
			consumer.setCustom_id(name);
			consumer.setUsername(name);
			String key = APIGatewayUtil.registToAPIGateway(host, consumer);
			System.out.println(key);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
