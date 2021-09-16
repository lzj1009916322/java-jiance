package com.weservice.catering.wtakeout.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weservice.catering.wtakeout.common.utils.PageUtils;
import com.weservice.catering.wtakeout.common.utils.Query;
import com.weservice.catering.wtakeout.modules.app.dao.AppUserInfoDao;
import com.weservice.catering.wtakeout.modules.app.entity.AppUserInfoEntity;
import com.weservice.catering.wtakeout.modules.app.service.AppUserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("infoService")
@Slf4j
public class AppUserInfoServiceImpl extends ServiceImpl<AppUserInfoDao, AppUserInfoEntity> implements AppUserInfoService {
    @Autowired
    private AppUserInfoDao appUserInfoDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AppUserInfoEntity> page = this.page(
                new Query().getPage(params),
                new QueryWrapper<AppUserInfoEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void updateEntity(AppUserInfoEntity appUserInfoEntity) {
        log.info("app userInfo:{}", appUserInfoEntity);
        appUserInfoDao.updateUser(appUserInfoEntity);
    }

}