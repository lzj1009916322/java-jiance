package com.weservice.catering.wtakeout.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weservice.catering.wtakeout.common.utils.PageUtils;
import com.weservice.catering.wtakeout.modules.app.entity.IsBoughtDetailEntity;
import com.weservice.catering.wtakeout.modules.app.entity.OrderInfoEntity;
import com.weservice.catering.wtakeout.modules.app.entity.req.OrderSubmitEntity;

import java.util.List;
import java.util.Map;

/**
 * ${comments}
 *
 * @author
 * @email
 * @date 2021-02-01 23:48:00
 */
public interface OrderInfoService extends IService<OrderInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
    OrderInfoEntity queryById(String orderId);

    void pass(OrderInfoEntity orderInfo);

    int updateOrder(OrderInfoEntity orderInfo);

    List<OrderInfoEntity> queryByCondition(OrderInfoEntity orderInfo);

    IsBoughtDetailEntity isBought(Integer productId, String userId);

    void cancel(String orderId);

    void submit(OrderSubmitEntity orderInfo);
}

