package com.weservice.catering.wtakeout.modules.app.job;

import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryResult;
import com.weservice.catering.wtakeout.modules.app.dao.PayInfoDao;
import com.weservice.catering.wtakeout.modules.app.entity.PayInfoEntity;
import com.weservice.catering.wtakeout.modules.app.service.PayNotifyService;
import com.weservice.catering.wtakeout.modules.job.task.ITask;
import com.weservice.catering.wtakeout.modules.wxpay.util.WxPayV3Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component("wxPayQueryJob")
@Slf4j
public class WxPayQueryJob implements ITask {
    @Autowired
    private PayInfoDao payInfoDao;
    @Autowired
    private WxPayV3Util wxPayV3Util;
    @Autowired
    private List<PayNotifyService> notifyServices = new ArrayList<>();

    @Override
    public void run(String params) {
        queryWxPay();
    }

    @Scheduled(initialDelay = 15, cron = "0/30 * * * * ?")
    public void queryWxPay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, (calendar.get(Calendar.HOUR_OF_DAY) - 5));
        Date beginTime = calendar.getTime();
        List<PayInfoEntity> payInfoEntities = payInfoDao.selectByTime(beginTime, null, null);
        payInfoEntities.stream().filter(x -> !"paid".equals(x.getFlag())).forEach(x -> {
            String id = x.getId();
            WxPayOrderQueryResult wxPayOrderQueryResult = wxPayV3Util.queryOrder(id);
            if ("SUCCESS".equals(wxPayOrderQueryResult.getTradeState())) {
                log.info("update pay info");
                x.setFlag("paid");
                payInfoDao.update(x);
                notifyServices.parallelStream().forEach(y -> y.wxNotify(x));
            }

        });
    }
}
