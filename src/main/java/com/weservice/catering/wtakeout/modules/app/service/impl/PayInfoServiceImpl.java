package com.weservice.catering.wtakeout.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weservice.catering.wtakeout.common.utils.PageUtils;
import com.weservice.catering.wtakeout.common.utils.Query;
import com.weservice.catering.wtakeout.modules.app.dao.PayInfoDao;
import com.weservice.catering.wtakeout.modules.app.entity.PayInfoEntity;
import com.weservice.catering.wtakeout.modules.app.entity.PointNotesEntity;
import com.weservice.catering.wtakeout.modules.app.entity.ThreadLocalUserInfo;
import com.weservice.catering.wtakeout.modules.app.service.PayInfoService;
import com.weservice.catering.wtakeout.modules.app.utils.QueryEntityUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class PayInfoServiceImpl extends ServiceImpl<PayInfoDao, PayInfoEntity> implements PayInfoService {
    @Override
    public PageUtils<PayInfoEntity> queryPage(Map<String, Object> params) {
        params.put("userId", ThreadLocalUserInfo.getUserInfo().getAppUserId());
        params.put("flag", "paid");
        IPage<PayInfoEntity> page = this.page(
                new Query<PayInfoEntity>().getPage(params),
                new QueryWrapper<>(QueryEntityUtil.generateQueryEntity(params, PayInfoEntity.class)).orderByDesc("create_time")
        );

        return new PageUtils<PayInfoEntity>(page);
    }
}
