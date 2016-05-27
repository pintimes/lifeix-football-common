package com.lifeix.football.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MapUtil {

	public static <K, V> List<V> getMapValue(Map<K, V> map){
		Set<K> set = map.keySet();
		List<V> result = new ArrayList<>(map.size());
		for (K k : set) {
			result.add(map.get(k));
		}
		return result ;
	}
	
}
