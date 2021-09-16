package com.weservice.catering.wtakeout.modules.app.job;

import com.weservice.catering.wtakeout.modules.app.constant.LockConstant;
import com.weservice.catering.wtakeout.modules.app.constant.OrderEnum;
import com.weservice.catering.wtakeout.modules.app.dao.AppUserInfoDao;
import com.weservice.catering.wtakeout.modules.app.dao.OrderInfoDao;
import com.weservice.catering.wtakeout.modules.app.dao.ProductInfoDao;
import com.weservice.catering.wtakeout.modules.app.entity.AppUserInfoEntity;
import com.weservice.catering.wtakeout.modules.app.entity.OrderInfoEntity;
import com.weservice.catering.wtakeout.modules.app.entity.ProductInfoEntity;
import com.weservice.catering.wtakeout.modules.job.task.ITask;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Component("orderTask")
@Slf4j
public class OrderJob implements ITask {
    @Autowired
    private OrderInfoDao orderInfoDao;
    @Autowired
    private ProductInfoDao productInfoDao;

//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//
//    }

    @Override
    public void run(String params) {
        dealTimeout();
    }

    @Scheduled(initialDelay = 10, cron = "0/30 * * * * ?")
    public void dealTimeout() {
        log.info("start  order job");
        List<OrderInfoEntity> orderInfoEntities = orderInfoDao.selectUnfinished();
        orderInfoEntities.forEach(this::updateOrderAndProduct);

    }

    public void updateOrderAndProduct(OrderInfoEntity orderInfo) {
        orderInfo.setStateAndStatus(OrderEnum.FINISH, OrderEnum.TIMEOUT);
        orderInfo.setFailReason("超时未消费");
        orderInfo.setUpdateTime(new Date());
        orderInfoDao.update(orderInfo);
        LockConstant.pointLock.lock();
        try {
//            ProductInfoEntity productInfoEntity = productInfoDao.selectById(orderInfo.getProductId());
//            productInfoEntity.setTotalLeft(productInfoEntity.getTotalLeft() + 1);
//            productInfoDao.update(productInfoEntity);
            productInfoDao.modifyTotalLeft(1, orderInfo.getProductId());
        } catch (Exception e) {
            log.error("job error:", e);
            throw new RuntimeException(e);
        } finally {
            LockConstant.pointLock.unlock();
        }
    }


}
