/**
 * @author xule
 * @version 2017年3月16日 上午11:00:31
 */
package com.lifeix.football.common.test.generic.Base64Util;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.Base64Utils;

import com.lifeix.football.common.util.Base64Util;
import com.lifeix.football.common.util.StringUtil;

/**
 * @author xule
 * @version 2017年3月16日 上午11:00:31
 */
public class Base64UtilTest {
	/**
	 * 测试不同长度字符串编码与解码
	 * @author xule
	 * @version 2017年3月16日  上午11:19:45
	 * @param 
	 * @return void
	 */
	@Test
	public void Test(){
		int []n={1,10,100,1000,10000,100000};
		for (int i = 0,len=n.length; i < len; i++) {
			/**
			 * 获得随机字符串
			 */
			String randomString = StringUtil.getRandomString(n[i]);
			/**
			 * 编码
			 */
			String encoding = Base64Util.encoding(randomString);
			Assert.assertNotNull(encoding);
			/**
			 * 解码
			 */
			String decoding = Base64Util.decoding(encoding);
			Assert.assertNotNull(decoding);
			/**
			 * 结果比较
			 */
			Assert.assertEquals(randomString, decoding);
		}
	}
	
}
