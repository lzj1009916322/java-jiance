package com.weservice.catering.wtakeout.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weservice.catering.wtakeout.common.utils.PageUtils;
import com.weservice.catering.wtakeout.modules.app.entity.MerchInfoEntity;

import java.util.Map;

/**
 * ${comments}
 *
 * @author
 * @email
 * @date 2021-02-01 23:48:01
 */
public interface MerchInfoService extends IService<MerchInfoEntity> {

    PageUtils<MerchInfoEntity> queryPage(Map<String, Object> params);
}

