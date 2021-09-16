package com.weservice.catering.wtakeout.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weservice.catering.wtakeout.common.utils.PageUtils;
import com.weservice.catering.wtakeout.common.utils.Query;
import com.weservice.catering.wtakeout.modules.app.dao.MerchInfoDao;
import com.weservice.catering.wtakeout.modules.app.entity.MerchInfoEntity;
import com.weservice.catering.wtakeout.modules.app.service.MerchInfoService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("merchInfoService")
public class MerchInfoServiceImpl extends ServiceImpl<MerchInfoDao, MerchInfoEntity> implements MerchInfoService {

    @Override
    public PageUtils<MerchInfoEntity> queryPage(Map<String, Object> params) {
        IPage<MerchInfoEntity> page = this.page(
                new Query<MerchInfoEntity>().getPage(params),
                new QueryWrapper<MerchInfoEntity>()
        );

        return new PageUtils<MerchInfoEntity>(page);
    }

}