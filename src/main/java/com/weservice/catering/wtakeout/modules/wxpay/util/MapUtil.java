package com.weservice.catering.wtakeout.modules.wxpay.util;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class MapUtil {
    public static Map<String, String> bean2Map(Object object) {
        Map<String, String> result = new HashMap<String, String>();
        //获得类的的属性名
        Field[] fields = object.getClass().getDeclaredFields();
        try {


            for (Field field : fields) {
                field.setAccessible(true);
                String name = new String(field.getName());

                if (field.get(object) instanceof Integer) {
                    result.put(name, String.valueOf(field.get(object)));
                } else if (field.get(object) instanceof String) {
                    result.put(name, (String) field.get(object));
                } else if (field.get(object) == null) {
                    continue;
                } else {
                    log.error("type error");
                }

            }
        } catch (Exception e) {
            log.error("transfer to map error", e);
        }
        return result;

    }
}
