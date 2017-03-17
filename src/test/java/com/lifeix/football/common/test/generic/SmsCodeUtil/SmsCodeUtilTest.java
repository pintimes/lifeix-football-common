package com.lifeix.football.common.test.generic.SmsCodeUtil;

import java.util.regex.Pattern;
import org.junit.Assert;
import org.junit.Test;
import com.lifeix.football.common.util.SmsCodeUtil;

/**
 * @author xule
 * @version 2017年3月16日 下午6:28:52
 */
public class SmsCodeUtilTest {
	@Test
	public void generateSmsCodeTest(){
		String generateSmsCode = SmsCodeUtil.generateSmsCode();
		Assert.assertNotNull(generateSmsCode);
		Assert.assertEquals(6, generateSmsCode.length());
		String regex="[0-9]{6}";
		Assert.assertTrue(Pattern.matches(regex, generateSmsCode));
	}
}
