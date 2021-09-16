package com.weservice.catering.wtakeout.modules.app.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Date;

@Slf4j
public class SetTimestampForEntity {
    public static void setUpdateTime(Object o) {
        setPropertyByField(o, "updateTime", new Date());
    }

    public static void setCreateTime(Object o) {
        setPropertyByField(o, "createTime", new Date());
    }

    public static void setPropertyByField(Object o, String fieldName, Object value) {
        Class<?> clazz = o.getClass();
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(value, o);
        } catch (Exception e) {
            log.error("set  field error:", e);
        }
    }
}
