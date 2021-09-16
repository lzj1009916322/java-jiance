package com.weservice.catering.wtakeout.modules.app.job;


import com.weservice.catering.wtakeout.modules.app.dao.AppUserInfoDao;
import com.weservice.catering.wtakeout.modules.app.dao.MemberInfoDao;
import com.weservice.catering.wtakeout.modules.app.entity.AppUserInfoEntity;
import com.weservice.catering.wtakeout.modules.app.service.PayNotifyService;
import com.weservice.catering.wtakeout.modules.job.task.ITask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("memberTask")
@Slf4j
public class MemberJob implements ITask {
    @Autowired
    private MemberInfoDao memberInfoDao;
    @Autowired
    private AppUserInfoDao appUserInfoDao;


    @Override
    public void run(String params) {
        updateUserInfoByMemberInfo();
    }

    @Scheduled(initialDelay = 5,cron = "0/30 * * * * ?")
    public void updateUserInfoByMemberInfo() {
        try {
            log.info("start updateUserInfoByMemberInfo");
            List<AppUserInfoEntity> appUserInfoEntities = memberInfoDao.selectExceedMember();
            appUserInfoEntities.forEach(x -> {
                x.setMember("0");
                appUserInfoDao.updateUser(x);
            });
        } catch (Exception e) {
            log.error("updateUserInfoByMemberInfo", e);
        }

    }
}
