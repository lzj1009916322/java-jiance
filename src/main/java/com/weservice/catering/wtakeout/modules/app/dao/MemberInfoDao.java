package com.weservice.catering.wtakeout.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.weservice.catering.wtakeout.modules.app.entity.AppUserInfoEntity;
import com.weservice.catering.wtakeout.modules.app.entity.MemberInfoEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberInfoDao extends BaseMapper<MemberInfoEntity> {
    void update(MemberInfoEntity memberInfoEntity);

    List<AppUserInfoEntity> selectExceedMember();
}
