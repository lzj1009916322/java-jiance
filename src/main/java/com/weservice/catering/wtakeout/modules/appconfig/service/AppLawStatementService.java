package com.weservice.catering.wtakeout.modules.appconfig.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weservice.catering.wtakeout.common.utils.PageUtils;
import com.weservice.catering.wtakeout.modules.appconfig.entity.AppLawStatementEntity;

import java.util.Map;

/**
 * ${comments}
 *
 * @author
 * @email
 * @date 2021-03-26 09:51:11
 */
public interface AppLawStatementService extends IService<AppLawStatementEntity> {

    PageUtils queryPage(Map<String, Object> params);

    AppLawStatementEntity getLatest();

    void add(AppLawStatementEntity entity);

    void update(AppLawStatementEntity entity);
}

