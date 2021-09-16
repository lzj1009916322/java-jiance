package com.weservice.catering.wtakeout.modules.sys.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.weservice.catering.wtakeout.common.utils.PageUtils;
import com.weservice.catering.wtakeout.modules.sys.entity.SysOrganizationEntity;
import com.weservice.catering.wtakeout.modules.sys.vo.SysOrganizationVo;

import java.util.Map;

/**
 *
 *
 * @author
 * @email
 * @date 2020-09-14 18:31:42
 */
public interface SysOrganizationService extends IService<SysOrganizationEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPageVo(Page<SysOrganizationVo> page, String name , String superName);

    Boolean addOrganization(SysOrganizationEntity entity);
}

