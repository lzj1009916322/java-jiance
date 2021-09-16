package com.weservice.catering.wtakeout.modules.wxpay.controller;

import com.alibaba.fastjson.JSON;
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryResult;
import com.weservice.catering.wtakeout.modules.wxpay.entity.PayV3Notify;
import com.weservice.catering.wtakeout.modules.wxpay.entity.WXResponse;
import com.weservice.catering.wtakeout.modules.wxpay.service.PayV3Service;
import com.weservice.catering.wtakeout.modules.wxpay.util.WxPayV3Util;
import oracle.jdbc.proxy.annotation.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class WXPayActionController {
    @Autowired
    private PayV3Service payV3Service;
    @Autowired
    private WxPayV3Util wxPayV3Util;

    @PostMapping("/wxNotify/orderAction")
    public String orderAction(@RequestBody PayV3Notify payV3Notify) {
        return payV3Service.payNotify(payV3Notify);
    }

    @GetMapping("/wxNotify/pay")
    public String test(String tranId) {
        WxPayOrderQueryResult wxPayOrderQueryResult = wxPayV3Util.queryOrder(tranId);
        return JSON.toJSONString(wxPayOrderQueryResult);
//        wxPayV3Util.createOrderJSAPI(new BigDecimal(0.1), "oiaNe5dPBG1vCzPs6re66WbiXKyg", "test", null);
//        return null;
    }
}
