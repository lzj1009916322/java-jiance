package com.weservice.catering.wtakeout.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weservice.catering.wtakeout.common.utils.Query;
import com.weservice.catering.wtakeout.modules.app.dao.PriceConfigDao;
import com.weservice.catering.wtakeout.modules.app.entity.PriceConfigEntity;
import com.weservice.catering.wtakeout.modules.app.service.PriceConfigService;
import com.weservice.catering.wtakeout.modules.app.utils.QueryEntityUtil;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.weservice.catering.wtakeout.common.utils.PageUtils;


@Service("priceInfoService")
public class PriceConfigServiceImpl extends ServiceImpl<PriceConfigDao, PriceConfigEntity> implements PriceConfigService {

    @Override
    public PageUtils<PriceConfigEntity> queryPage(Map<String, Object> params) {
        IPage<PriceConfigEntity> page = this.page(
                new Query<PriceConfigEntity>().getPage(params),
                new QueryWrapper<>(QueryEntityUtil.generateQueryEntity(params, PriceConfigEntity.class))
        );

        return new PageUtils<PriceConfigEntity>(page);
    }

}