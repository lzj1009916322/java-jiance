package com.weservice.catering.wtakeout.modules.app.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weservice.catering.wtakeout.common.exception.RRException;
import com.weservice.catering.wtakeout.common.utils.PageUtils;
import com.weservice.catering.wtakeout.common.utils.Query;
import com.weservice.catering.wtakeout.modules.app.constant.LockConstant;
import com.weservice.catering.wtakeout.modules.app.dao.AppUserInfoDao;
import com.weservice.catering.wtakeout.modules.app.dao.ProductInfoDao;
import com.weservice.catering.wtakeout.modules.app.entity.*;
import com.weservice.catering.wtakeout.modules.app.service.OrderInfoService;
import com.weservice.catering.wtakeout.modules.app.service.ProductInfoService;
import com.weservice.catering.wtakeout.modules.app.utils.IdempotencyUtil;
import com.weservice.catering.wtakeout.modules.app.utils.QueryEntityUtil;
import com.weservice.catering.wtakeout.modules.app.utils.UrlUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import static com.weservice.catering.wtakeout.modules.app.constant.CommonConstant.TIME_OUT;
import static com.weservice.catering.wtakeout.modules.app.constant.OrderEnum.BUY;
import static com.weservice.catering.wtakeout.modules.app.constant.OrderEnum.DOING;


@Service("productInfoService")
@Slf4j
public class ProductInfoServiceImpl extends ServiceImpl<ProductInfoDao, ProductInfoEntity> implements ProductInfoService {
    @Autowired
    private ProductInfoDao productInfoDao;
    @Autowired
    private AppUserInfoDao appUserInfoDao;
    @Autowired
    private OrderInfoService orderInfoService;


    @Override
    public PageUtils<ProductInfoEntity> queryPage(Map<String, Object> params) {
        if (!ThreadLocalUserInfo.isAdmin()) {
            params.put("saleFlag", true);
        }
        IPage<ProductInfoEntity> res = null;
        if (params.get("longitude") == null || params.get("latitude") == null) {
            res = this.page(
                    new Query<ProductInfoEntity>().getPage(params),
                    new QueryWrapper<ProductInfoEntity>(QueryEntityUtil.generateQueryEntity(params, ProductInfoEntity.class))
            );
        } else {
            Page<ProductInfoEntity> page = PageUtils.generatePage(params);
            ProductInfoEntity productInfoEntity = QueryEntityUtil.generateQueryEntity(params, ProductInfoEntity.class);
            res = productInfoDao.getListByLocation(page, productInfoEntity);
        }
        List<ProductInfoEntity> list = res.getRecords().parallelStream().map(UrlUtil::transferUrl).collect(Collectors.toList());
        res.setRecords(list);
        return new PageUtils<>(res);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void buyProduct(List<Integer> ids) {
        try {
            LockConstant.pointLock.lock();
            List<ProductInfoEntity> productInfoEntities = productInfoDao.selectBatchIds(ids);
            productInfoEntities.forEach(x -> {
                IdempotencyUtil.set(String.valueOf(x.getProductId()) + ThreadLocalUserInfo.getUserInfo().getAppUserId());
                Optional<IsBoughtDetailEntity> bought = Optional.ofNullable(orderInfoService.isBought(x.getProductId(), ThreadLocalUserInfo.getUserInfo().getAppUserId()));
                bought.ifPresent(y -> {
                    if (y.isBought()) {
                        throw new RRException("请勿重复购买");
                    }
                });
                Integer left = x.getTotalLeft();
                if (null != left && (left > 0 || left == 0)) {
                    x.setTotalLeft(left - 1);
                } else {
                    throw new RRException("商品已售完");
                }

                x.setUpdateTime(new Date());

                productInfoDao.update(x);
                createOrder(x);
            });
        } catch (Exception e) {
            log.error("buy product error", e);
            throw new RuntimeException("error");
        } finally {
            LockConstant.pointLock.unlock();
        }
    }

    @Override
    public void updateEntity(ProductInfoEntity productInfo) {
        productInfoDao.update(productInfo);
    }

    @Override
    public IsBoughtDetailEntity getDetailByToken(Integer productId, @NotNull Float latitude, @NotNull Float longitude) {
        String appUserId = ThreadLocalUserInfo.getUserInfo().getAppUserId();
        try {
            return CompletableFuture.supplyAsync(() -> orderInfoService.isBought(productId, appUserId)).thenCombine(
                    CompletableFuture.supplyAsync(() -> productInfoDao.getProductById(productId, latitude, longitude)),
                    (x, y) -> {
                        if (null == x) {
                            x = new IsBoughtDetailEntity();
                        }
                        if (null == y) {
                            y = new ProductInfoEntity();
                        }
                        y = UrlUtil.transferUrl(y);
                        BeanUtils.copyProperties(y, x);
                        return x;
                    }).exceptionally(e -> {
                log.error("get product detail error", e);
                throw new RRException("查询失败");
            })
                    .get(TIME_OUT, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("get product detail error", e);
            throw new RRException("获取商品详情失败");
        }

    }

    @Override
    public void modifyTotalLeft(@NotNull Integer deltaTotalLeft, Integer productId) {
        productInfoDao.modifyTotalLeft(deltaTotalLeft, productId);
    }

    @Override
    public void onSale(List<Integer> asList) {
        productInfoDao.modifySaleFlag(asList, true);
    }

    @Override
    public void outSale(List<Integer> asList) {
        productInfoDao.modifySaleFlag(asList, false);
    }

    private void createOrder(ProductInfoEntity x) {
        OrderInfoEntity orderInfoEntity = new OrderInfoEntity();
        orderInfoEntity.setStateAndStatus(DOING, BUY);
        orderInfoEntity.setProductId(x.getProductId());
        orderInfoEntity.setUserId(ThreadLocalUserInfo.getUserInfo().getAppUserId());
        orderInfoEntity.setCreateTimeAndTimeoutTime();
        orderInfoEntity.setUpdateTime(new Date());
//        todo
        if (ThreadLocalUserInfo.getUserInfo().isMember()) {
            orderInfoEntity.setReturnPoint(x.getMemberReturnPoint());
        } else {
            orderInfoEntity.setReturnPoint(x.getReturnPoint());
        }
        orderInfoEntity.setCost(x.getPrice());
        orderInfoEntity.setProductName(x.getProductName());
        orderInfoEntity.setProductPicUrl(x.getProductPicUrl());
        orderInfoEntity.setProductType(x.getProductType());
        orderInfoService.save(orderInfoEntity);
    }

}