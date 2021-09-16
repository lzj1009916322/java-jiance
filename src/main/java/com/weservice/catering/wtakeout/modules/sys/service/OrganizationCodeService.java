package com.weservice.catering.wtakeout.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weservice.catering.wtakeout.common.utils.PageUtils;
import com.weservice.catering.wtakeout.modules.sys.entity.OrganizationCodeEntity;

import java.util.Map;

/**
 *
 *
 * @author
 * @email
 * @date 2020-09-19 19:57:56
 */
public interface OrganizationCodeService extends IService<OrganizationCodeEntity> {

    PageUtils queryPage(Map<String, Object> params);

    Integer getOneCode(Integer level) throws IllegalArgumentException;
}

