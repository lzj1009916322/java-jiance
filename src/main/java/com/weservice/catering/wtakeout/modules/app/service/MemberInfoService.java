package com.weservice.catering.wtakeout.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weservice.catering.wtakeout.common.utils.Response;
import com.weservice.catering.wtakeout.modules.app.entity.MemberInfoEntity;
import com.weservice.catering.wtakeout.modules.app.entity.req.WxPrePayReqEntity;
import com.weservice.catering.wtakeout.modules.app.entity.res.WxPrePaytResEntity;

public interface MemberInfoService extends IService<MemberInfoEntity> {
    void queryRecent();

    Response<WxPrePaytResEntity> prePay(WxPrePayReqEntity wxPrePayReqEntity);
}
