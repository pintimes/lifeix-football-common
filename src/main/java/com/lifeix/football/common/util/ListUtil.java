package com.lifeix.football.common.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author zengguangwei
 * 
 */
public class ListUtil {

    public static <T> List<T> toList(Iterable<T> iterable) {
        if (iterable == null) {
            return null;
        }
        List<T> ts = new ArrayList<T>();
        Iterator<T> iterator = iterable.iterator();
        while (iterator.hasNext()) {
            T next = iterator.next();
            ts.add(next);
        }
        return ts;
    }

    /**
     * 转化为Iterable对象
     * 
     * @param ts
     * @return
     */
    public static <T> Iterable<T> iterable(final List<T> ts) {
        Iterable<T> entites = new Iterable<T>() {

            public Iterator<T> iterator() {
                return ts.iterator();
            }
        };
        return entites;
    }

}
