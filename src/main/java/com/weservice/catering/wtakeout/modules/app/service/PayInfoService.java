package com.weservice.catering.wtakeout.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weservice.catering.wtakeout.common.utils.PageUtils;
import com.weservice.catering.wtakeout.common.utils.Response;
import com.weservice.catering.wtakeout.modules.app.entity.MemberInfoEntity;
import com.weservice.catering.wtakeout.modules.app.entity.PayInfoEntity;
import com.weservice.catering.wtakeout.modules.app.entity.req.WxPrePayReqEntity;
import com.weservice.catering.wtakeout.modules.app.entity.res.WxPrePaytResEntity;

import java.util.Map;

public interface PayInfoService extends IService<PayInfoEntity> {

    PageUtils<PayInfoEntity> queryPage(Map<String, Object> params);

}
