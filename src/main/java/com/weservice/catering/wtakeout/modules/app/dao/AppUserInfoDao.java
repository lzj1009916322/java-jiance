package com.weservice.catering.wtakeout.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.weservice.catering.wtakeout.modules.app.entity.AppUserInfoEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * ${comments}
 *
 * @author
 * @email
 * @date 2021-02-01 23:48:00
 */
@Mapper
public interface AppUserInfoDao extends BaseMapper<AppUserInfoEntity> {
    int updateUser(AppUserInfoEntity appUserInfoEntity);
}
