package com.weservice.catering.wtakeout.modules.appconfig.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.weservice.catering.wtakeout.modules.sys.entity.OrganizationCodeEntity;
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

import com.weservice.catering.wtakeout.modules.appconfig.dao.AppUserGuideDao;
import com.weservice.catering.wtakeout.modules.appconfig.entity.AppUserGuideEntity;
import com.weservice.catering.wtakeout.modules.appconfig.service.AppUserGuideService;


@Service("appUserGuideService")
public class AppUserGuideServiceImpl extends ServiceImpl<AppUserGuideDao, AppUserGuideEntity> implements AppUserGuideService {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    DateTimeFormatter simpleDateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AppUserGuideEntity> page = this.page(
                new Query<AppUserGuideEntity>().getPage(params),
                new QueryWrapper<AppUserGuideEntity>().orderByDesc("create_time")
        );

        return new PageUtils(page);
    }
    @Override
    public AppUserGuideEntity getLatest() {
        LambdaQueryWrapper<AppUserGuideEntity> wrapper = Wrappers.<AppUserGuideEntity>lambdaQuery().orderByDesc(AppUserGuideEntity::getCreateTime).last(" limit 1");
        return getBaseMapper().selectOne(wrapper);
    }

    @Override
    public void add(AppUserGuideEntity entity) {
        LocalDateTime now = LocalDateTime.now();
        String name = simpleDateFormatter.format(now) + String.format("%04d", new Random().nextInt(10000));
        entity.setName(name);
        entity.setCreateTime(now);
        entity.setUpdateTime(now);
        entity.setStatus(1);
        save(entity);
    }
    @Override
    public void update(AppUserGuideEntity entity) {
        entity.setUpdateTime( LocalDateTime.now());
        updateById(entity);
    }
}
