package com.weservice.catering.wtakeout.modules.appconfig.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weservice.catering.wtakeout.common.utils.PageUtils;
import com.weservice.catering.wtakeout.modules.appconfig.entity.AppUserGuideEntity;

import java.util.Map;

/**
 * ${comments}
 *
 * @author
 * @email
 * @date 2021-03-24 19:48:52
 */
public interface AppUserGuideService extends IService<AppUserGuideEntity> {

    PageUtils queryPage(Map<String, Object> params);

    AppUserGuideEntity getLatest();

    void add(AppUserGuideEntity entity);

    void update(AppUserGuideEntity entity);
}

