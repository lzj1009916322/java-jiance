package com.weservice.catering.wtakeout.modules.appconfig.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.weservice.catering.wtakeout.modules.appconfig.entity.AppUserGuideEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Random;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weservice.catering.wtakeout.common.utils.PageUtils;
import com.weservice.catering.wtakeout.common.utils.Query;

import com.weservice.catering.wtakeout.modules.appconfig.dao.AppLawStatementDao;
import com.weservice.catering.wtakeout.modules.appconfig.entity.AppLawStatementEntity;
import com.weservice.catering.wtakeout.modules.appconfig.service.AppLawStatementService;


@Service("appLawStatementService")
public class AppLawStatementServiceImpl extends ServiceImpl<AppLawStatementDao, AppLawStatementEntity> implements AppLawStatementService {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    DateTimeFormatter simpleDateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AppLawStatementEntity> page = this.page(
                new Query<AppLawStatementEntity>().getPage(params),
                new QueryWrapper<AppLawStatementEntity>().orderByDesc("create_time")
        );

        return new PageUtils(page);
    }
    @Override
    public AppLawStatementEntity getLatest() {
        LambdaQueryWrapper<AppLawStatementEntity> wrapper = Wrappers.<AppLawStatementEntity>lambdaQuery().orderByDesc(AppLawStatementEntity::getCreateTime).last(" limit 1");
        return getBaseMapper().selectOne(wrapper);
    }


    @Override
    public void add(AppLawStatementEntity entity) {
        LocalDateTime now = LocalDateTime.now();
        String name = simpleDateFormatter.format(now) + String.format("%04d", new Random().nextInt(10000));
        entity.setName(name);
        entity.setCreateTime(now);
        entity.setUpdateTime(now);
        entity.setStatus(1);
        save(entity);
    }
    @Override
    public void update(AppLawStatementEntity entity) {
        entity.setUpdateTime( LocalDateTime.now());
        updateById(entity);
    }


}
