package com.weservice.catering.wtakeout.modules.app.utils;


import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.weservice.catering.wtakeout.common.exception.RRException;

import java.util.concurrent.TimeUnit;

public class LocalCache {
    static Cache<String, String> cache = Caffeine.newBuilder()
            //指定初始大小
            .initialCapacity(1000)
            .expireAfterWrite(30, TimeUnit.SECONDS)
            .build();
    static Cache<String, String> cacheLessTime = Caffeine.newBuilder()
            //指定初始大小
            .initialCapacity(1000)
            .expireAfterWrite(5, TimeUnit.SECONDS)
            .build();
    public static boolean get(String key) {
        String ifPresent = cache.getIfPresent(key);
        return ifPresent == null;
    }

    public static void set(String key, String value) {
        cache.put(key, value);
    }


    public static boolean getLessTime(String key) {
        String ifPresent = cacheLessTime.getIfPresent(key);
        return ifPresent == null;
    }

    public static void setLessTime(String key, String value) {
        cacheLessTime.put(key, value);
    }
}
