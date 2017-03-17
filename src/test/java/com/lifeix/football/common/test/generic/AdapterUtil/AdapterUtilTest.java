package com.lifeix.football.common.test.generic.AdapterUtil;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.util.CollectionUtils;
import com.lifeix.football.common.util.AdapterUtil;

/**
 * @author xule
 * @version 2017年3月15日 下午12:03:18
 */
public class AdapterUtilTest {
	/**
	 * 对象转换方法测试，测试指标：
	 * 1、原对象和目标class均不为null时：
	 * 	   （1）转换结果不为空；
	 * 	   （2）同类型、同名属性转换前后值相等；
	 * 	   （3）同类型、不同名属性转换后为空；
	 * 	   （4）不同类型、同名属性转换后为空。
	 * 2、原对象为null、目标class不为null时：
	 * 	   转换结果为空
	 * 3、原对象不为null、目标class为null时：
	 * 	   转换结果为空
	 */
	@Test
	public void toTTest(){
		User user=new User("id", "nickname", "avatar");
		/**
		 * 原对象和目标class均不为null
		 */
		User2 t1 = AdapterUtil.toT(user, User2.class);
		Assert.assertNotNull(t1);//转换结果不能为空
		Assert.assertEquals(user.getId(), t1.getId());//同类型 同名 属性转换前后值相等
		Assert.assertNull(t1.getName());//同类型 不同名 属性转换后为空
		Assert.assertNull(t1.getAvatar());//不同类型 同名 属性转换后为空
		/**
		 * 原对象为null、目标class不为null
		 */
		User2 t2 = AdapterUtil.toT(null, User2.class);
		Assert.assertNull(t2);
		/**
		 * 原对象不为null、目标class为null
		 */
		Object t3=null;
		try {
			t3 = AdapterUtil.toT(user, null);
		} catch (Exception e) {
		}
		Assert.assertNull(t3);
		System.out.println("AdapterUtilTest.toTTest ok!");
	}
	
	@Test
	public void toTsTest(){
		List<User> list=new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			list.add(new User("id"+i, "nickname"+i, "avatar"+i));
		}
		List<User2> list2 = AdapterUtil.toTs(list, User2.class);
		Assert.assertTrue(!CollectionUtils.isEmpty(list2));
		Assert.assertEquals(list.size(), list2.size());
		for (int i = 0,size=list.size(); i < size; i++) {
			User2 user2=list2.get(i);
			Assert.assertNotNull(user2);//转换结果不能为空
			Assert.assertEquals(list.get(i).getId(), user2.getId());//同类型 同名 属性转换前后值相等
			Assert.assertNull(user2.getName());//同类型 不同名 属性转换后为空
			Assert.assertNull(user2.getAvatar());//不同类型 同名 属性转换后为空
		}
		System.out.println("AdapterUtilTest.toTsTest ok!");
	}
}