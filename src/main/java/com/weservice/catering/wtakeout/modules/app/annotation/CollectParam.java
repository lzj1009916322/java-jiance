package com.weservice.catering.wtakeout.modules.app.annotation;

import com.weservice.catering.wtakeout.modules.app.constant.LocationStatisticsEnum;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CollectParam {
    LocationStatisticsEnum type();
}
