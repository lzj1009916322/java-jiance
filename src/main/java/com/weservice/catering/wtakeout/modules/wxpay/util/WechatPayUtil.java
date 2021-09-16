//package com.weservice.catering.wtakeout.modules.wxpay.util;
//
//import com.github.wxpay.sdk.WXPayConstants;
//import com.github.wxpay.sdk.WXPayRequest;
//import com.weservice.catering.wtakeout.modules.wxpay.WXPayConfigImpl;
//import com.weservice.catering.wtakeout.modules.wxpay.entity.UniFieOderEntityReq;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.concurrent.ThreadLocalRandom;
//
//@Service("wchatPay")
//@Slf4j
//public class WechatPayUtil {
//    @Autowired
//    private WXPayConfigImpl weChatUtil;
//
//    public void unifiedOrder() {
//        try {
//            UniFieOderEntityReq uniFieOderEntityReq = new UniFieOderEntityReq();
//            WXPayRequest wxPayRequest = new WXPayRequest(weChatUtil);
//            String s = wxPayRequest.requestWithoutCert(WXPayConstants.UNIFIEDORDER_URL_SUFFIX, uniFieOderEntityReq.getNonce_str(), uniFieOderEntityReq.toXML(), true);
//            log.info("send request:{}", s);
//        } catch (Exception e) {
//            log.error("wxpayconfig error:", e);
//        }
//    }
//}
