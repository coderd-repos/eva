package com.eva.core.utils;

import java.lang.reflect.Field;

/**
 * Mybatis Plus Wrapper工具类
 * @author Eva
 * @date 2021-05-16 01:39
 */
public class MyBatisPlus {

    /**
     * 将空转为null，用于mybatis plus查询数据时将值为""的字段转为null，防止将空字符串作为条件。
     * @author Eva
     * @date 2021-05-16 01:37
     *
     * @return T
     */
    public <T> T blankToNull(T object) {
        if (object == null) {
            return null;
        }
        try {
            Class clazz = object.getClass();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(object);
                if ("".equals(value)) {
                    field.set(object, null);
                }
            }
            return object;
        } catch (Exception e) {
            throw new RuntimeException("解析参数发生了异常", e);
        }
    }
}
