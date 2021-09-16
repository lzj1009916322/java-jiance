package com.weservice.catering.wtakeout.modules.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weservice.catering.wtakeout.modules.app.constant.ThreadPool;
import com.weservice.catering.wtakeout.modules.app.dao.LocationStatisticsDao;
import com.weservice.catering.wtakeout.modules.app.entity.LocationStatisticsEntity;
import com.weservice.catering.wtakeout.modules.app.service.LocationStatisticsService;
import com.weservice.catering.wtakeout.modules.app.utils.QueryEntityUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service("locationStatisticsService")
@Slf4j
public class LocationStatisticsServiceImpl extends ServiceImpl<LocationStatisticsDao, LocationStatisticsEntity> implements LocationStatisticsService {


    public void doSaveLocationRecords(String type, Map<String, Object> param) {
        Object longitudeObject = param.get("longitude");
        Object latitudeObject = param.get("latitude");
        if (longitudeObject != null && latitudeObject != null && type != null) {
            try {
                LocationStatisticsEntity locationStatisticsEntity = QueryEntityUtil.generateQueryEntity(param, LocationStatisticsEntity.class);
                locationStatisticsEntity.setCreateTime(new Date());
                this.save(locationStatisticsEntity);
            } catch (Exception e) {
                log.error("insert error:", e);
            }
        }
        ;
    }

    @Override
    public void acceptLocationParam(String type, Map<String, Object> param) {
        ThreadPool.locationThread.submit(() -> doSaveLocationRecords(type, param));
    }
}
