package com.weservice.catering.wtakeout.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weservice.catering.wtakeout.common.utils.PageUtils;
import com.weservice.catering.wtakeout.modules.app.entity.AppUserInfoEntity;

import java.util.Map;

/**
 * ${comments}
 *
 * @author
 * @email
 * @date 2021-02-01 23:48:00
 */
public interface AppUserInfoService extends IService<AppUserInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void updateEntity(AppUserInfoEntity appUserInfoEntity);
}

