package com.lifeix.football.common.test.generic.ArrayUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.lifeix.football.common.util.ArrayUtil;

/**
 * @author xule
 * @version 2017年3月16日 上午10:18:23
 */
public class ArrayUtilTest {
	@Test
	public void isEmptyTest(){
		Assert.assertTrue(ArrayUtil.isEmpty(new boolean[]{}));
		Assert.assertTrue(!ArrayUtil.isEmpty(new boolean[]{false,true}));
		Assert.assertTrue(ArrayUtil.isEmpty(new char[]{}));
		Assert.assertTrue(!ArrayUtil.isEmpty(new int[]{1,2,3}));
		Assert.assertTrue(ArrayUtil.isEmpty(new byte[]{}));
		Assert.assertTrue(!ArrayUtil.isEmpty(new byte[]{1,2,3}));
		Assert.assertTrue(ArrayUtil.isEmpty(new short[]{}));
		Assert.assertTrue(!ArrayUtil.isEmpty(new short[]{1,2,3}));
		Assert.assertTrue(ArrayUtil.isEmpty(new int[]{}));
		Assert.assertTrue(!ArrayUtil.isEmpty(new int[]{1,2,3}));
		Assert.assertTrue(ArrayUtil.isEmpty(new long[]{}));
		Assert.assertTrue(!ArrayUtil.isEmpty(new long[]{1,2,3}));
		Assert.assertTrue(ArrayUtil.isEmpty(new float[]{}));
		Assert.assertTrue(!ArrayUtil.isEmpty(new float[]{1,2,3}));
		Assert.assertTrue(ArrayUtil.isEmpty(new double[]{}));
		Assert.assertTrue(!ArrayUtil.isEmpty(new double[]{1,2,3}));
		Assert.assertTrue(ArrayUtil.isEmpty(new String[]{}));
		Assert.assertTrue(!ArrayUtil.isEmpty(new String[]{"1","2","3"}));
		System.out.println("ArrayUtilTest.isEmptyTest ok!");
	}
	
	@Test
	public void toArrayTest(){
		/**
		 * 空集合转换结果为空
		 */
		List<Integer> list1=new ArrayList<>();
		Integer[] array = ArrayUtil.toArray(list1, Integer.class);
		Assert.assertNull(array);
		
		/**
		 * 非空集合转换结果非空，且转换前后集合长度相等，且集合中每个下标元素对应相等
		 */
		list1.addAll(new ArrayList<>(Arrays.asList(1,2,4,5)));
		Integer[] array2 = ArrayUtil.toArray(list1, Integer.class);
		Assert.assertNotNull(array2);
		Assert.assertEquals(list1.size(), array2.length);
		for (int i = 0,size=list1.size(); i < size; i++) {
			Assert.assertEquals(list1.get(i), array2[i]);
		}
		System.out.println("ArrayUtilTest.toArrayTest ok!");
	}
	
	
	@Test
	public void toArray2Test(){
		/**
		 * 空集合转换结果为空
		 */
		List<Integer> list1=new ArrayList<>();
		Integer[] array = ArrayUtil.toArray(list1);
		Assert.assertNull(array);
		
		/**
		 * 非空集合转换结果非空，且转换前后集合长度相等，且集合中每个下标元素对应相等
		 */
		list1.addAll(new ArrayList<>(Arrays.asList(1,2,4,5)));
		Integer[] array2 = ArrayUtil.toArray(list1);
		Assert.assertNotNull(array2);
		Assert.assertEquals(list1.size(), array2.length);
		for (int i = 0,size=list1.size(); i < size; i++) {
			Assert.assertEquals(list1.get(i), array2[i]);
		}
		System.out.println("ArrayUtilTest.toArray2Test ok!");
	}
}
