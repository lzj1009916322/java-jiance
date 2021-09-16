package com.weservice.catering.wtakeout.modules.app.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weservice.catering.wtakeout.common.exception.RRException;
import com.weservice.catering.wtakeout.common.utils.PageUtils;
import com.weservice.catering.wtakeout.modules.app.constant.LockConstant;
import com.weservice.catering.wtakeout.modules.app.constant.OrderEnum;
import com.weservice.catering.wtakeout.modules.app.dao.AppUserInfoDao;
import com.weservice.catering.wtakeout.modules.app.dao.OrderInfoDao;
import com.weservice.catering.wtakeout.modules.app.entity.*;
import com.weservice.catering.wtakeout.modules.app.entity.req.OrderSubmitEntity;
import com.weservice.catering.wtakeout.modules.app.service.OrderInfoService;
import com.weservice.catering.wtakeout.modules.app.service.PicInfoService;
import com.weservice.catering.wtakeout.modules.app.service.PointNotesService;
import com.weservice.catering.wtakeout.modules.app.service.ProductInfoService;
import com.weservice.catering.wtakeout.modules.app.utils.QueryEntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.weservice.catering.wtakeout.modules.app.constant.OrderEnum.*;


@Service("orderInfoService")
public class OrderInfoServiceImpl extends ServiceImpl<OrderInfoDao, OrderInfoEntity> implements OrderInfoService {
    @Autowired
    private PicInfoService picInfoService;
    @Resource
    private AppUserInfoDao appUserInfoDao;
    @Autowired
    private OrderInfoDao orderInfoDao;
    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private PointNotesService pointNotesService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<OrderInfoEntity> page = PageUtils.generatePage(params);
        OrderInfoEntity orderInfoEntity = QueryEntityUtil.generateQueryEntity(params, OrderInfoEntity.class);
        orderInfoEntity.setUserId(ThreadLocalUserInfo.getUserInfo().getAppUserId());
        IPage<OrderInfoEntity> orderInfoEntityIPage = orderInfoDao.queryByPage(page, orderInfoEntity);
        List<OrderInfoEntity> records = orderInfoEntityIPage.getRecords();
        List<OrderInfoEntity> res = records.parallelStream().map(x -> {
            Map<String, List<PicInfoEntity>> stringListMap = picInfoService.queryMapByBizId(String.valueOf(x.getOrderId()));
            List<PicInfoEntity> buyPics = stringListMap.get(BUY.getStatus());
            List<PicInfoEntity> praisePics = stringListMap.get(PRAISE.getStatus());
            if (buyPics != null) {
                x.setBuyPics(buyPics);
            }
            if (praisePics != null) {
                x.setPraisePics(praisePics);
            }
            return x;

        }).collect(Collectors.toList());
        orderInfoEntityIPage.setRecords(res);
        return new PageUtils(orderInfoEntityIPage);

    }

    @Override
    public OrderInfoEntity queryById(String orderId) {
        OrderInfoEntity res = orderInfoDao.queryById(orderId);
        return res;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void pass(OrderInfoEntity orderInfo) {
        OrderInfoEntity byId = this.getById(orderInfo.getOrderId());
        LockConstant.pointLock.lock();
        try {
            OrderInfoEntity orderInfoEntity = orderInfoDao.queryById(orderInfo.getOrderId());
            if (SUCCESS.getStatus().equals(orderInfoEntity.getStatus())) {
                throw new RRException("请勿重复提交");
            }
            BigDecimal returnPoint = byId.getReturnPoint() == null ? new BigDecimal(0) : byId.getReturnPoint();
            AppUserInfoEntity appUserInfoEntity = appUserInfoDao.selectById(byId.getUserId());
            BigDecimal now = appUserInfoEntity.getPoint() == null ? new BigDecimal(0) : appUserInfoEntity.getPoint();
            appUserInfoEntity.setPoint(now.add(returnPoint));
            appUserInfoDao.updateUser(appUserInfoEntity);
            pointNotesService.createPointNotesByOrder(byId, ThreadLocalUserInfo.getUserInfo().getAppUserId());
        } catch (Exception e) {
            log.error("通过失败：", e);
            throw new RRException("通过失败");
        } finally {
            LockConstant.pointLock.unlock();
        }

    }

    @Override
    public int updateOrder(OrderInfoEntity orderInfo) {
        return orderInfoDao.update(orderInfo);
    }

    @Override
    public List<OrderInfoEntity> queryByCondition(OrderInfoEntity orderInfo) {
        return orderInfoDao.queryByCondition();
    }

    @Override
    public IsBoughtDetailEntity isBought(Integer productId, String userId) {
        return orderInfoDao.isBought(productId, userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancel(String orderId) {
        OrderInfoEntity orderInfoEntity = orderInfoDao.queryById(orderId);
        if (AUDIT.getStatus().equals(orderInfoEntity.getStatus())) {
            throw new RRException("该订单已进入最后审批阶段");
        }
        orderInfoEntity.cancel();
        orderInfoEntity.setUpdateTime(new Date());
        productInfoService.modifyTotalLeft(1, orderInfoEntity.getProductId());
        orderInfoDao.update(orderInfoEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submit(OrderSubmitEntity orderInfo) {
        OrderInfoEntity orderInfoEntityUpdate = new OrderInfoEntity();
        orderInfoEntityUpdate.setOrderId(orderInfo.getOrderId());
        String newStatus = "";
        OrderInfoEntity orderInfoEntity = orderInfoDao.queryById(orderInfo.getOrderId());
        String status = orderInfoEntity.getStatus();
        if (BUY.getStatus().equals(status)) {
            newStatus = PRAISE.getStatus();
        } else if (PRAISE.getStatus().equals(status)) {
            newStatus = AUDIT.getStatus();
        } else {
            throw new RRException("该订单异常");
        }
        orderInfoEntityUpdate = orderInfo.transfer2OrderInfoEntity(orderInfoEntityUpdate);
        List<PicInfoEntity> picInfoEntities = orderInfo.transfer2PicInfoEntity(status);
        orderInfoEntityUpdate.setStatus(newStatus);
        orderInfoDao.update(orderInfoEntityUpdate);
        picInfoService.saveBatch(picInfoEntities);
    }
}