package com.lifeix.football.common.test.generic.MD5Util;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Test;

import com.lifeix.football.common.util.MD5Util;

/**
 * @author xule
 * @version 2017年3月16日 下午5:53:26
 */
public class MD5UtilTest {
	@Test
	public void getMD5Test(){
		int n=10000;
		Set<String> set=new HashSet<>();
		for (int i = 0; i < n; i++) {
			String string = UUID.randomUUID().toString();
			String md5 = MD5Util.getMD5(string);
			Assert.assertNotNull(md5);
			/**
			 * 验证不同字符串的md5值不同
			 */
			if (set.contains(md5)) {
				Assert.fail();
			}
			/**
			 * 验证相同字符串的md5值相同
			 */
			String md5_ = MD5Util.getMD5(string);
			Assert.assertNotNull(md5_);
			Assert.assertEquals(md5, md5_);
			set.add(md5);
		}
	}
}
