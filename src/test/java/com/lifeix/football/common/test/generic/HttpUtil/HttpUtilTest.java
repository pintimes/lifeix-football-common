/**
 * @author xule
 * @version 2017年3月16日 下午4:17:04
 */
package com.lifeix.football.common.test.generic.HttpUtil;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.lifeix.football.common.util.HttpUtil;

/**
 * @author xule
 * @version 2017年3月16日 下午4:17:04
 */
public class HttpUtilTest {
	@Test
	public void sendHeadTest(){
		String url="https://resources.c-f.com/wemedia/images/FtY-34Fo8rXsOalFoloyWU4_7PMK.jpg";
		try {
			boolean sendHead = HttpUtil.sendHead(url);
			Assert.assertTrue(sendHead);
		} catch (Exception e) {
			Assert.fail();
		}
	}
	
	@Test
	public void sendGetTest(){
		String url="https://api.c-f.com/football/wemedia/posts/search";
		try {
			String sendGet = HttpUtil.sendGet(url);
			Assert.assertNotNull(sendGet);
			Assert.assertTrue(sendGet.contains("No API key found in headers or querystring"));
		} catch (Exception e) {
			Assert.fail();
		}
		
		url="https://api.c-f.com/football/wemedia/posts/search?key=visitor";
		try {
			String sendGet = HttpUtil.sendGet(url);
			Assert.assertNotNull(sendGet);
		} catch (Exception e) {
			Assert.fail();
		}
	}
	
	@Test
	public void sendGet2Test(){
		String url="https://api.c-f.com/football/wemedia/posts/search";
		try {
			Map<String, String> headers=new HashMap<>();
			headers.put("key", "visitor");
			String sendGet = HttpUtil.sendGet(url, headers);
			Assert.assertNotNull(sendGet);
		} catch (Exception e) {
			Assert.fail();
		}
	}
}
