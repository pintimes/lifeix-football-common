package com.lifeix.football.common.util;

import java.lang.reflect.Array;
import java.util.List;

/**
 * 
 * @author zengguangwei
 * @desciprtion 数组工具
 */
public class ArrayUtil {

	public static boolean isEmpty(boolean[] arrays) {
		return arrays == null || arrays.length == 0;
	}
	public static boolean isEmpty(char[] arrays) {
		return arrays == null || arrays.length == 0;
	}
	public static boolean isEmpty(byte[] arrays) {
		return arrays == null || arrays.length == 0;
	}
	public static boolean isEmpty(short[] arrays) {
		return arrays == null || arrays.length == 0;
	}
	public static boolean isEmpty(int[] arrays) {
		return arrays == null || arrays.length == 0;
	}
	public static boolean isEmpty(long[] arrays) {
		return arrays == null || arrays.length == 0;
	}
	public static boolean isEmpty(float[] arrays) {
		return arrays == null || arrays.length == 0;
	}
	public static boolean isEmpty(double[] arrays) {
		return arrays == null || arrays.length == 0;
	}
    public static <T> boolean isEmpty(T[] arrays) {
        return arrays == null || arrays.length == 0;
    }

    /**
     * list转数组
     * 
     * @param ts
     * @param classname
     * @return
     */
    public static <M> M[] toArray(List<M> ts, Class<M> classname) {
        if (ts == null || ts.size() == 0) {
            return null;
        }
        @SuppressWarnings("unchecked")
        M[] array = (M[]) Array.newInstance(classname, ts.size());
        M[] result = ts.toArray(array);
        return result;
    }
    
    public static <M> M[] toArray(List<M> ts) {
    	if (ts==null||ts.size()==0) {
			return null;
		}
    	Class<? extends Object> class1 = ts.get(0).getClass();
    	@SuppressWarnings("unchecked")
    	M[] array = (M[]) Array.newInstance(class1, ts.size());
        M[] result = ts.toArray(array);
        return result;
    }
}
