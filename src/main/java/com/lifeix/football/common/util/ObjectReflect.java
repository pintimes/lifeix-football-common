package com.lifeix.football.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc 获取对象中的非空属性和值
 * @author gcc
 *
 */
public class ObjectReflect<T> {

    public Map<String, Object> getNotNullAttribute(Object model, T t) throws NoSuchMethodException,
            SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Map<String, Object> map = new HashMap<String, Object>();
        if (model == null) {
            return map;
        }
        Field[] field = model.getClass().getDeclaredFields(); // 获取实体类的所有属性，返回Field数组
        for (int j = 0; j < field.length; j++) { // 遍历所有属性
            String name = field[j].getName(); // 获取属性的名字
            String Uppername = name.substring(0, 1).toUpperCase() + name.substring(1); // 将属性的首字符大写，方便构造get，set方法
            String type = field[j].getGenericType().toString(); // 获取属性的类型
            if (t != null && type.equals(t.getClass().toString())) { // 如果type是类类型，则前面包含"class
                                                                     // "，后面跟类名
                Method m = model.getClass().getMethod("get" + Uppername);
                @SuppressWarnings("unchecked")
                T value = (T) m.invoke(model); // 调用getter方法获取属性值
                if (value != null) {
                    map.put(name, value);
                }
            }
            if (type.equals("class java.lang.String")) { // 如果type是类类型，则前面包含"class "，后面跟类名
                Method m = model.getClass().getMethod("get" + Uppername);
                String value = (String) m.invoke(model); // 调用getter方法获取属性值
                if (value != null) {
                    map.put(name, value);
                }
            }
            if (type.equals("java.util.List<java.lang.String>")) { // 如果type是类类型，则前面包含"class "，后面跟类名
                Method m = model.getClass().getMethod("get" + Uppername);
                @SuppressWarnings("unchecked")
                List<String> value = (List<String>) m.invoke(model); // 调用getter方法获取属性值
                if (value != null && value.size() > 0) {
                    map.put(name, value);
                }
            }
            if (type.equals("class java.lang.Integer")) {
                Method m = model.getClass().getMethod("get" + Uppername);
                Integer value = (Integer) m.invoke(model);
                if (value != null) {
                    map.put(name, value);
                }
            }
            if (type.equals("class java.lang.Short")) {
                Method m = model.getClass().getMethod("get" + Uppername);
                Short value = (Short) m.invoke(model);
                if (value != null) {
                    map.put(name, value);
                }
            }
            if (type.equals("class java.lang.Double")) {
                Method m = model.getClass().getMethod("get" + Uppername);
                Double value = (Double) m.invoke(model);
                if (value != null) {
                    map.put(name, value);
                }
            }
            if (type.equals("class java.lang.Long")) {
                Method m = model.getClass().getMethod("get" + Uppername);
                Long value = (Long) m.invoke(model);
                if (value != null) {
                    map.put(name, value);
                }
            }

            if (type.equals("class java.lang.Boolean")) {
                Method m = model.getClass().getMethod("get" + Uppername);
                Boolean value = (Boolean) m.invoke(model);
                if (value != null) {
                    map.put(name, value);
                }
            }
            if (type.equals("class java.util.Date")) {
                Method m = model.getClass().getMethod("get" + Uppername);
                Date value = (Date) m.invoke(model);
                if (value != null) {
                    map.put(name, value);
                }
            }
        }
        return map;
    }

}
