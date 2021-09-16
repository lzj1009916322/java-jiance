package com.weservice.catering.wtakeout.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weservice.catering.wtakeout.common.utils.PageUtils;
import com.weservice.catering.wtakeout.modules.app.entity.PriceConfigEntity;

import java.util.Map;

/**
 * ${comments}
 *
 * @author
 * @email
 * @date 2021-04-24 11:42:31
 */
public interface PriceConfigService extends IService<PriceConfigEntity> {

    PageUtils<PriceConfigEntity> queryPage(Map<String, Object> params);
}

