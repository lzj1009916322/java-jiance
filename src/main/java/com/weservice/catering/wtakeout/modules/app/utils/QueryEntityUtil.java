package com.weservice.catering.wtakeout.modules.app.utils;

import com.alibaba.fastjson.JSON;
import com.weservice.catering.wtakeout.common.exception.RRException;

import java.util.Map;

public class QueryEntityUtil {
    public static <T> T generateQueryEntity(Map<String, Object> params, Class<T> clazz) {
        if (params != null) {
            String s = JSON.toJSONString(params);
            return JSON.parseObject(s, clazz);
        } else {
            try {
                return clazz.newInstance();
            } catch (Exception e) {
                throw new RRException("参数错误");
            }
        }
    }

    public static void setUserIdForPara(Map<String, Object> params, String userId) {
        params.put("userId", userId);
    }
}
