package com.weservice.catering.wtakeout.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weservice.catering.wtakeout.modules.sys.entity.SysOrganizationEntity;
import com.weservice.catering.wtakeout.modules.sys.vo.SysOrganizationVo;
import org.apache.ibatis.annotations.Mapper;

/**
 *
 *
 * @author
 * @email
 * @date 2020-09-14 18:31:42
 */
@Mapper
public interface SysOrganizationDao extends BaseMapper<SysOrganizationEntity> {

    IPage<SysOrganizationVo> selectPageVo(Page<SysOrganizationVo> page, String name, String superName);

}
