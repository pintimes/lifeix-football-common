package com.lifeix.football.common.util;

import java.lang.reflect.Array;
import java.util.List;

/**
 * 
 * @author zengguangwei
 * @desciprtion 数组工具
 */
public class ArrayUtil {

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
}
