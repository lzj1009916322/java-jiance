//package com.weservice.catering.wtakeout.modules.wxpay;
//
//import com.github.wxpay.sdk.IWXPayDomain;
//import com.github.wxpay.sdk.WXPayConfig;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//@Service
//public class IWXPayDomainImpl implements IWXPayDomain {
//    @Value("${wx.domain}")
//    private String domain;
//
//    @Override
//    public void report(String s, long l, Exception e) {
//
//    }
//
//    @Override
//    public DomainInfo getDomain(WXPayConfig wxPayConfig) {
//        return new DomainInfo(domain, true);
//    }
//}
