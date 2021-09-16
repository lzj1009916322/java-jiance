package com.weservice.catering.wtakeout.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.weservice.catering.wtakeout.modules.app.entity.PointNotesEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * ${comments}
 *
 * @author
 * @email
 * @date 2021-02-01 23:48:00
 */
@Mapper
public interface PointNotesDao extends BaseMapper<PointNotesEntity> {
    int update(PointNotesEntity pointNotesEntity);
}
