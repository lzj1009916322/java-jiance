package com.weservice.catering.wtakeout.modules.app.utils;

import com.weservice.catering.wtakeout.common.exception.RRException;
import com.weservice.catering.wtakeout.modules.app.entity.ThreadLocalUserInfo;

public class IdempotencyUtil {
    public static void set(String key) {
        boolean b = LocalCache.get(key);
        if (b) {
            LocalCache.set(key, "lock");
        } else {
            throw new RRException("请勿重复提交");
        }
    }

    public static void setLessTime(String key) {
        boolean b = LocalCache.getLessTime(key);
        if (b) {
            LocalCache.setLessTime(key, "lock");
        } else {
            throw new RRException("请勿重复提交");
        }
    }
}
