package com.weservice.catering.wtakeout.modules.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weservice.catering.wtakeout.modules.app.dao.AppUserConfigDao;
import com.weservice.catering.wtakeout.modules.app.dao.LocationStatisticsDao;
import com.weservice.catering.wtakeout.modules.app.entity.AppUserConfigEntity;
import com.weservice.catering.wtakeout.modules.app.entity.LocationStatisticsEntity;
import com.weservice.catering.wtakeout.modules.app.service.AppUserConfigService;
import org.springframework.stereotype.Service;

@Service("appUserConfigService")
public class AppUserConfigServiceImpl extends ServiceImpl<AppUserConfigDao, AppUserConfigEntity> implements AppUserConfigService {
}
