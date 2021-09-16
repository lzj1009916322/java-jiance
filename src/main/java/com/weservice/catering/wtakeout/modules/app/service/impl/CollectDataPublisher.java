package com.weservice.catering.wtakeout.modules.app.service.impl;

import com.weservice.catering.wtakeout.modules.app.service.CollectDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("collectDataPublisher")
public class CollectDataPublisher {
    @Autowired
    private List<CollectDataService> collectDataServices;

    public void publishLocation(String type, Map<String, Object> param) {
        collectDataServices.parallelStream().forEach(x -> x.acceptLocationParam(type, param));
    }

    public void publishLocation(String type, Object[] args) {
//        collectDataServices.parallelStream().forEach(x -> x.acceptLocationParam(type, param));
    }
}
