package com.weservice.catering.wtakeout.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.weservice.catering.wtakeout.modules.app.entity.PayInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface PayInfoDao extends BaseMapper<PayInfoEntity> {
    int update(PayInfoEntity payInfoEntity);

    int add(PayInfoEntity payInfoEntity);

    List<PayInfoEntity> selectByTime(@Param("beginTime") Date beginTime, @Param("endTime") Date endTime,@Param("flag")String flag);
}
