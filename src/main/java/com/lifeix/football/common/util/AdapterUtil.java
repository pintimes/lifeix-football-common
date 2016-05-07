package com.lifeix.football.common.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

/**
 * 转换器
 * @author zengguangwei
 *
 */
public class AdapterUtil {

    public static <M, N> M toT(N n, Class<M> classname) {
        if (n == null) {
            return null;
        }
        M m = null;
        try {
            m = classname.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        BeanUtils.copyProperties(n, m);

        return m;
    }

    public static <M, N> List<M> toTs(List<N> ns, Class<M> classname) {
        if (CollectionUtils.isEmpty(ns)) {
            return new ArrayList<>();
        }
        List<M> ms = new ArrayList<M>(ns.size());
        for (N n : ns) {
            M m = toT(n, classname);
            if (m == null) {
                continue;
            }
            ms.add(m);
        }
        return ms;
    }

}
