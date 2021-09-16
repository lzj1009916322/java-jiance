package com.weservice.catering.wtakeout.modules.app.controller;

import com.weservice.catering.wtakeout.common.exception.RRException;
import com.weservice.catering.wtakeout.common.utils.PageUtils;
import com.weservice.catering.wtakeout.common.utils.R;
import com.weservice.catering.wtakeout.common.utils.Response;
import com.weservice.catering.wtakeout.modules.app.constant.OrderEnum;
import com.weservice.catering.wtakeout.modules.app.dao.PicInfoDao;
import com.weservice.catering.wtakeout.modules.app.entity.CommonUserInfo;
import com.weservice.catering.wtakeout.modules.app.entity.OrderInfoEntity;
import com.weservice.catering.wtakeout.modules.app.entity.ProductInfoEntity;
import com.weservice.catering.wtakeout.modules.app.entity.ThreadLocalUserInfo;
import com.weservice.catering.wtakeout.modules.app.entity.req.OrderSubmitEntity;
import com.weservice.catering.wtakeout.modules.app.service.OrderInfoService;
import com.weservice.catering.wtakeout.modules.app.service.PicInfoService;
import com.weservice.catering.wtakeout.modules.app.service.ProductInfoService;
import com.weservice.catering.wtakeout.modules.app.utils.IdempotencyUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang.StringUtils;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import static com.weservice.catering.wtakeout.modules.app.constant.OrderEnum.*;


/**
 * ${comments}
 *
 * @author
 * @email
 * @date 2021-02-01 23:48:00
 */
@RestController
@RequestMapping("/order/orderInfo")
@Api(value = "订单信息", tags = "订单")
public class OrderInfoController {
    @Autowired
    private OrderInfoService orderInfoService;
    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private PicInfoService picInfoService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation("订单列表")
//    @RequiresPermissions("user:orderinfo:list")
    public Response<PageUtils<OrderInfoEntity>> list(@RequestParam Map<String, Object> params) {
        String appUserId = ThreadLocalUserInfo.getUserInfo().getAppUserId();
        if (StringUtils.isEmpty(appUserId)) {
            params.put("userId", appUserId);
        }
        PageUtils<OrderInfoEntity> page = orderInfoService.queryPage(params);
        return Response.ok(page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{orderId}")
    @ApiOperation("订单详情")
//    @RequiresPermissions("user:orderinfo:info")
    public Response<OrderInfoEntity> info(@PathVariable("orderId") String orderId) {
        OrderInfoEntity orderInfo = orderInfoService.queryById(orderId);
        return Response.ok(orderInfo);
    }

    /**
     * 信息
     */
    @GetMapping("/cancel/{orderId}")
    @ApiOperation("取消订单")
//    @RequiresPermissions("user:orderinfo:info")
    public Response cancel(@PathVariable("orderId") String orderId) {
        orderInfoService.cancel(orderId);
        return Response.ok();
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @ApiOperation("保存订单")
//    @RequiresPermissions("user:orderinfo:save")
    public Response save(@RequestBody OrderInfoEntity orderInfo) {
        orderInfo.setState(DOING.getStatus());
        orderInfo.setStatus(BUY.getStatus());
        ProductInfoEntity productInfoEntity = productInfoService.getById(orderInfo.getProductId());
        orderInfo.setProductName(productInfoEntity.getProductName());
//        orderInfo.setCostPoint(productInfoEntity.getPrice());
        if (ThreadLocalUserInfo.getUserInfo().isMember()) {
            orderInfo.setReturnPoint(productInfoEntity.getMemberReturnPoint());
        } else {
            orderInfo.setReturnPoint(productInfoEntity.getReturnPoint());
        }
        orderInfo.setUserId(ThreadLocalUserInfo.getUserInfo().getAppUserId());
        orderInfo.setCreateTimeAndTimeoutTime();
        orderInfo.setUpdateTime(new Date());
        orderInfoService.save(orderInfo);
        return Response.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @ApiOperation("修改订单")
    @Transactional(rollbackFor = Exception.class)
//    @RequiresPermissions("user:orderinfo:update")
    public Response update(@RequestBody OrderInfoEntity orderInfo) {
        orderInfo.checkStatus();
        if (ThreadLocalUserInfo.isAdmin() && orderInfo.getStatus().equals(SUCCESS.getStatus())) {
            IdempotencyUtil.set(orderInfo.getOrderId() + ThreadLocalUserInfo.getUserInfo().getUserId());
            orderInfoService.pass(orderInfo);
        }
        orderInfo.setUpdateTime(new Date());
        orderInfoService.updateOrder(orderInfo);
        return Response.ok();
    }


    /**
     * 修改
     */
    @PostMapping("/submit")
    @ApiOperation("订单提交")
    @ApiImplicitParam(name = "OrderSubmitEntity")
//    @RequiresPermissions("user:orderinfo:update")
    public Response submit(@RequestBody @ApiParam("订单提交") @Validated OrderSubmitEntity orderInfo) {
        IdempotencyUtil.setLessTime(orderInfo.getOrderId() + ThreadLocalUserInfo.getUserInfo().getAppUserId());
        orderInfoService.submit(orderInfo);
        return Response.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @ApiOperation("删除订单")
//    @RequiresPermissions("user:orderinfo:delete")
    public Response delete(@RequestBody Integer[] orderIds) {
        orderInfoService.removeByIds(Arrays.asList(orderIds));
        return Response.ok();
    }

//    /**
//     * 删除
//     */
//    @PostMapping("/upload")
//    @ApiOperation("上传图片")
//    @Transactional(rollbackFor = Exception.class)
////    @RequiresPermissions("user:orderinfo:delete")
//    public Response upload(MultipartFile file, Integer orderId, String type, String takeoutOrder, BigDecimal cost) {
//        picInfoService.uploadFile(file, String.valueOf(orderId), type);
//        updateOrderEntityByStatus(orderId, type, takeoutOrder, cost);
//        return Response.ok();
//    }
//
//    public void updateOrderEntityByStatus(Integer orderId, String type, String takeoutOrder, BigDecimal cost) {
//        OrderInfoEntity orderInfoEntity = new OrderInfoEntity();
//        orderInfoEntity.setOrderId(orderId);
//        orderInfoEntity.setTakeoutOrder(takeoutOrder);
//        orderInfoEntity.setCost(cost);
//        if (BUY.getStatus().equals(type)) {
//            orderInfoEntity.setStatus(PRAISE.getStatus());
//            orderInfoService.updateOrder(orderInfoEntity);
//        } else if (PRAISE.getStatus().equals(type)) {
//            orderInfoEntity.setStatus(AUDIT.getStatus());
//            orderInfoService.updateOrder(orderInfoEntity);
//        } else {
//            throw new RRException("未传入正确type");
//        }
//        orderInfoService.updateOrder(orderInfoEntity);
//    }
}
