package com.lifeix.football.common.test.generic.MapUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.lifeix.football.common.util.MapUtil;

/**
 * @author xule
 * @version 2017年3月16日 下午5:43:07
 */
public class MapUtilTest {
	
	@Test
	public void getMapValueTest(){
		Map<String,Integer> map=new HashMap<>();
		map.put("a", 1);
		map.put("b", 2);
		map.put("c", 3);
		List<Integer> list = MapUtil.getMapValue(map);
		Assert.assertNotNull(list);
		Assert.assertEquals(3,list.size());
		Set<Integer> set=new HashSet<>(map.values());
		Set<Integer> set2=new HashSet<>(list);
		Assert.assertTrue(set.equals(set2));
	}
	
	@Test
	public void isEmptyTest(){
		Map<String,String> map=new HashMap<>();
		Assert.assertTrue(MapUtil.isEmpty(map));
		map.put("a", "1");
		Assert.assertFalse(MapUtil.isEmpty(map));
	}
}
