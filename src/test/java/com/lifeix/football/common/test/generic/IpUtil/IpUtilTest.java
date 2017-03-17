package com.lifeix.football.common.test.generic.IpUtil;

import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

import com.lifeix.football.common.util.IpUtil;

/**
 * @author xule
 * @version 2017年3月16日 下午4:50:24
 */
public class IpUtilTest {
	@Test
	public void getLocalHostIPTest(){
		String localHostIP = IpUtil.getLocalHostIP();
		Assert.assertNotNull(localHostIP);
		String regex="([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";//ipv4正则表达式
		Assert.assertTrue(Pattern.matches(regex, localHostIP));
	}
}
