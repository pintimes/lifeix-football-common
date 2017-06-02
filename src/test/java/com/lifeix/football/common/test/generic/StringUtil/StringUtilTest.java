package com.lifeix.football.common.test.generic.StringUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.lifeix.football.common.util.ArrayUtil;
import com.lifeix.football.common.util.StringUtil;

/**
 * @author xule
 * @version 2017年3月16日 下午6:34:26
 */
public class StringUtilTest {
	@Test
	public void firstUpcaseTest(){
		String string1 = StringUtil.firstUpcase("test");
		Assert.assertNotNull(string1);
		Assert.assertEquals("Test",string1);
		
		String string2 = StringUtil.firstUpcase("1test");
		Assert.assertNotNull(string2);
		Assert.assertEquals("1test",string2);
	}
	
	
	@Test
	public void replaceRowTest(){
		String replaceRow = StringUtil.replaceRow("12\n3n\r\t");
		System.out.println(replaceRow);
	}
	
	@Test
	public void replaceBlankTest(){
		String replaceRow = StringUtil.replaceBlank("1 \f2  \f\f\f\n\n\n3n\r\t\\");
		System.out.println(replaceRow);
	}
	
	@Test
	public void getFixlengthStringTest(){
		/**
		 * 字符串长度小于最大长度
		 */
		String s1="123";
		String result2 = StringUtil.getFixlengthString(s1, 5);
		Assert.assertEquals(s1,result2);
		/**
		 * 字符串长度等于最大长度
		 */
		String s2="123";
		String result3 = StringUtil.getFixlengthString(s2, 3);
		Assert.assertEquals(s2,result3);
		/**
		 * 字符串长度大于最大长度
		 */
		String s3="123";
		String result4 = StringUtil.getFixlengthString(s3, 2);
		Assert.assertEquals("12",result4);
	}
	
	@Test
	public void strToListTest(){
		String string1="1,2,,3";
		List<String> list1 = StringUtil.strToList(string1, ",");
		Assert.assertNotNull(list1);
		Assert.assertEquals(4, list1.size());
		Assert.assertTrue(list1.contains("1"));
		Assert.assertTrue(list1.contains("2"));
		Assert.assertTrue(list1.contains(""));
		Assert.assertTrue(list1.contains("3"));
		
		String string2="1,2,,3";
		List<String> list2 = StringUtil.strToList(string2, ",,");
		Assert.assertNotNull(list2);
		Assert.assertEquals(2, list2.size());
		Assert.assertTrue(list2.contains("1,2"));
		Assert.assertTrue(list2.contains("3"));
	}
	
	@Test
	public void getRandomStringTest(){
		String string1 = StringUtil.getRandomString(0);
		Assert.assertEquals(0, string1.length());
		
		String string2 = StringUtil.getRandomString(10);
		Assert.assertEquals(10, string2.length());
	}
	
}
