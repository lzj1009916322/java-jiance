/**
 *
 */

package com.weservice.catering.wtakeout.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.weservice.catering.wtakeout.modules.sys.entity.SysUserTokenEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统用户Token
 *
 *
 */
@Mapper
public interface SysUserTokenDao extends BaseMapper<SysUserTokenEntity> {

    SysUserTokenEntity queryByToken(String token);

    SysUserTokenEntity queryByUserId(Long userId);

    SysUserTokenEntity queryByAppUserId(String appUserId);

    void updateByUserId(SysUserTokenEntity sysUserTokenEntity);

    void updateByAppUserId(SysUserTokenEntity tokenEntity);
}
