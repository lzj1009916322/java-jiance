//package com.weservice.catering.wtakeout.modules.wxpay.entity;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONObject;
//import com.github.wxpay.sdk.WXPayUtil;
//import com.weservice.catering.wtakeout.common.exception.RRException;
//import com.weservice.catering.wtakeout.modules.wxpay.util.MapUtil;
//import lombok.Data;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.codec.digest.Md5Crypt;
//
//import java.util.Map;
//import java.util.UUID;
//import java.util.concurrent.ThreadLocalRandom;
//
//@Data
//@Slf4j
//public class UniFieOderEntityReq {
//    private String appid;
//    private String mch_id;
//    private String device_info;
//    private String nonce_str  = UUID.randomUUID().toString();
//    private String sign = Md5Crypt.apr1Crypt(nonce_str);
//    private String sign_type = "MD5";
//    private String body;
//    private String detail;
//    private String attach;
//    private String out_trade_no;
//    private String fee_type = "CNY";
//    private Integer total_fee;
//    private String spbill_create_ip;
//    private String time_start;
//
//    public String toXML() {
//        Map<String, String> stringStringMap = MapUtil.bean2Map(this);
//        try {
//            return WXPayUtil.mapToXml(stringStringMap);
//        } catch (Exception e) {
//            log.error("to xml error", e);
//            throw new RRException("下单失败");
//        }
//    }
//    public static class UniFieOderEntityBuilder{
//
//    }
//}
