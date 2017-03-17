package com.lifeix.football.common.test.generic.JSONUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.lifeix.football.common.model.User;
import com.lifeix.football.common.util.JSONUtils;

/**
 * @author xule
 * @version 2017年3月16日 下午5:01:04
 */
public class JSONUtilsTest {
	@Test
	public void obj2jsonTest(){
		User user = new User("ID", "NICKNAME", "AVATAR");
		try {
			String json = JSONUtils.obj2json(user);
			Assert.assertNotNull(json);
			Assert.assertEquals("{\"id\":\"ID\",\"nickname\":\"NICKNAME\",\"avatar\":\"AVATAR\"}", json);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void obj2jsonNotNullTest(){
		User user = new User();
		user.setId("ID");
		user.setAvatar("AVATAR");
		try {
			String json = JSONUtils.obj2jsonNotNull(user);
			Assert.assertNotNull(json);
			Assert.assertEquals("{\"id\":\"ID\",\"avatar\":\"AVATAR\"}", json);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void json2pojoTest(){
		try {
			User user = JSONUtils.json2pojo("{\"id\":\"ID\",\"nickname\":\"NICKNAME\",\"avatar\":\"AVATAR\"}",User.class);
			Assert.assertNotNull(user);
			Assert.assertEquals("ID", user.getId());
			Assert.assertEquals("NICKNAME", user.getNickname());
			Assert.assertEquals("AVATAR", user.getAvatar());
		} catch (Exception e) {
			Assert.fail();
		}
	}
	
	@Test
	public void json2mapTest(){
		try {
			Map<String, Object> json2map = JSONUtils.json2map("{\"id\":\"ID\",\"nickname\":\"NICKNAME\",\"avatar\":\"AVATAR\"}");
			Assert.assertNotNull(json2map);
			Assert.assertEquals(3, json2map.keySet().size());
			Assert.assertEquals("ID", json2map.get("id"));
			Assert.assertEquals("NICKNAME", json2map.get("nickname"));
			Assert.assertEquals("AVATAR", json2map.get("avatar"));
		} catch (Exception e) {
			Assert.fail();
		}
	}
	
	//TODO 
	@Test
	public void json2map2Test(){
		try {
			Map<String, String> json2map1 = JSONUtils.json2map("{\"id\":\"ID\",\"nickname\":\"NICKNAME\",\"avatar\":\"AVATAR\"}",String.class);
			Assert.assertNotNull(json2map1);
			Assert.assertEquals(3, json2map1.size());
			Assert.assertEquals("ID", json2map1.get("id"));
			Assert.assertEquals("NICKNAME", json2map1.get("nickname"));
			Assert.assertEquals("AVATAR", json2map1.get("avatar"));
			System.out.println(json2map1);
			
			Map<String, User> json2map2 = JSONUtils.json2map("{\"user\":{\"id\":\"ID\",\"nickname\":\"NICKNAME\",\"avatar\":\"AVATAR\"}}",User.class);
			Assert.assertNotNull(json2map2);
			User user = json2map2.get("user");
			Assert.assertEquals("ID", user.getId());
			Assert.assertEquals("NICKNAME", user.getNickname());
			Assert.assertEquals("AVATAR", user.getAvatar());
			System.out.println(json2map2);
		} catch (Exception e) {
			Assert.fail();
		}
	}
	
	@Test
	public void json2listTest(){
		try {
			List<User> json2list = JSONUtils.json2list("[{\"avatar\":\"AVATAR0\",\"id\":\"ID0\",\"nickname\":\"NICKNAME0\"},"
					+ "{\"avatar\":\"AVATAR1\",\"id\":\"ID1\",\"nickname\":\"NICKNAME1\"},"
					+ "{\"avatar\":\"AVATAR2\",\"id\":\"ID2\",\"nickname\":\"NICKNAME2\"}]", User.class);
			Assert.assertNotNull(json2list);
			Assert.assertEquals(3, json2list.size());
			for (User user : json2list) {
				Assert.assertNotNull(user.getId());
				Assert.assertTrue(user.getId().contains("ID"));
				Assert.assertNotNull(user.getNickname());
				Assert.assertTrue(user.getNickname().contains("NICKNAME"));
				Assert.assertNotNull(user.getAvatar());
				Assert.assertTrue(user.getAvatar().contains("AVATAR"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Map<String,User> map=new HashMap<>();
		List<User> list=new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			User user=new User("ID"+i, "NICKNAME"+i, "AVATAR"+i);
			list.add(user);
			map.put("user"+i, user);
		}
		System.out.println(JSONArray.toJSONString(list));
		System.out.println(map.toString());
		
	}
}
