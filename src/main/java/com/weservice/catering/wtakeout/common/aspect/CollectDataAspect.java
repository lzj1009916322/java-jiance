package com.weservice.catering.wtakeout.common.aspect;

import com.weservice.catering.wtakeout.modules.app.annotation.CollectParam;
import com.weservice.catering.wtakeout.modules.app.constant.LocationStatisticsEnum;
import com.weservice.catering.wtakeout.modules.app.entity.ThreadLocalUserInfo;
import com.weservice.catering.wtakeout.modules.app.service.impl.CollectDataPublisher;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Slf4j
@Component
public class CollectDataAspect {
    @Autowired
    CollectDataPublisher collectDataPublisher;

    @Pointcut("@annotation(com.weservice.catering.wtakeout.modules.app.annotation.CollectParam)")
    public void collectDataParma() {
    }

    @Before("collectDataParma()")
    public void collectData(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();

        MethodSignature methodSignature = (MethodSignature) signature;
        CollectParam annotation = methodSignature.getMethod().getAnnotation(CollectParam.class);
        LocationStatisticsEnum type = annotation.type();
        try {
            Map<String, Object> stringObjectMap = makeParamMap(joinPoint);
            stringObjectMap.put("userId", ThreadLocalUserInfo.getUserInfo().getAppUserId());
            collectDataPublisher.publishLocation(type.getType(), stringObjectMap);
        } catch (Exception e) {
            log.error("collect data error:", e);
        }

    }

    private Map<String, Object> makeParamMap(JoinPoint joinPoint) {
        Signature signature = joinPoint.getSignature();
        Method method = ((MethodSignature) signature).getMethod();
        DefaultParameterNameDiscoverer defaultParameterNameDiscoverer = new DefaultParameterNameDiscoverer();
        String[] parameterNames = defaultParameterNameDiscoverer.getParameterNames(method);
        Object[] args = joinPoint.getArgs();
        Map<String, Object> map = new HashMap<>();
        int length = parameterNames.length;
        for (int i = 0; i < length; i++) {
            map.put(parameterNames[i], args[i]);
        }
        return map;
    }
}
