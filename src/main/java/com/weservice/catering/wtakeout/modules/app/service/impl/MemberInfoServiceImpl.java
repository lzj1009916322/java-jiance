package com.weservice.catering.wtakeout.modules.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weservice.catering.wtakeout.common.utils.Response;
import com.weservice.catering.wtakeout.modules.app.dao.AppUserInfoDao;
import com.weservice.catering.wtakeout.modules.app.dao.MemberInfoDao;
import com.weservice.catering.wtakeout.modules.app.entity.AppUserInfoEntity;
import com.weservice.catering.wtakeout.modules.app.entity.MemberInfoEntity;
import com.weservice.catering.wtakeout.modules.app.entity.PayInfoEntity;
import com.weservice.catering.wtakeout.modules.app.entity.ThreadLocalUserInfo;
import com.weservice.catering.wtakeout.modules.app.entity.req.WxPrePayReqEntity;
import com.weservice.catering.wtakeout.modules.app.entity.res.WxPrePaytResEntity;
import com.weservice.catering.wtakeout.modules.app.service.MemberInfoService;
import com.weservice.catering.wtakeout.modules.app.service.PayNotifyService;
import com.weservice.catering.wtakeout.modules.wxpay.util.WxPayV3Util;
import org.apache.poi.ss.formula.functions.T;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Service("memberInfoService")
public class MemberInfoServiceImpl extends ServiceImpl<MemberInfoDao, MemberInfoEntity> implements MemberInfoService, PayNotifyService {
    private final String DESC = "会员支付";
    private final String TYPE = "member";
    @Autowired
    private WxPayV3Util wxPayV3Util;
    @Autowired
    private AppUserInfoDao appUserInfoDao;
    @Autowired
    private MemberInfoDao memberInfoDao;

    @Override
    public void queryRecent() {


    }

    @Override
    public Response<WxPrePaytResEntity> prePay(WxPrePayReqEntity wxPrePayReqEntity) {
        WxPrePaytResEntity orderJSAPI = wxPayV3Util.createOrderJSAPI(wxPrePayReqEntity.getMoney(), ThreadLocalUserInfo.getUserInfo().getAppUserId(), DESC, TYPE);
        return Response.ok(orderJSAPI);
    }

    @Override
    public void wxNotify(PayInfoEntity byId) {
        if (TYPE.equals(byId.getType())) {
            String appUserId = ThreadLocalUserInfo.getUserInfo().getAppUserId();
            AppUserInfoEntity appUserInfoEntity = new AppUserInfoEntity();
            appUserInfoEntity.setUserId(appUserId);
            appUserInfoEntity.setMember("1");
            appUserInfoDao.updateUser(appUserInfoEntity);


            MemberInfoEntity memberInfoEntity = this.getById(appUserId);
            if (null == memberInfoEntity) {
                memberInfoEntity.setUserId(appUserId);
                Date date = new Date();
                memberInfoEntity.setCreateTime(date);
                memberInfoEntity.setUpdateTime(date);
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(date);
                calendar.add(Calendar.DAY_OF_WEEK, 1);
                memberInfoEntity.setLimitTime(calendar.getTime());
                this.save(memberInfoEntity);
            } else {
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(memberInfoEntity.getLimitTime());
                calendar.add(Calendar.DAY_OF_WEEK, 1);
                memberInfoEntity.setLimitTime(calendar.getTime());
                memberInfoDao.update(memberInfoEntity);
            }

//
        }
    }
}
