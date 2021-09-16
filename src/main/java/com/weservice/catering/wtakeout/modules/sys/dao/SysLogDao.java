/**
 *
 *
 */

package com.weservice.catering.wtakeout.modules.sys.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.weservice.catering.wtakeout.modules.sys.entity.SysLogEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统日志
 *
 *
 */
@Mapper
public interface SysLogDao extends BaseMapper<SysLogEntity> {
	
}
