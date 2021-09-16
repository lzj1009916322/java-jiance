//package com.weservice.catering.wtakeout.modules.wxpay;
//
//import com.github.wxpay.sdk.IWXPayDomain;
//import com.github.wxpay.sdk.WXPayConfig;
//import lombok.Setter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.io.InputStream;
//
//@Setter
//@Component("wxconfig")
//public class WXPayConfigImpl extends WXPayConfig {
//    @Value("${wx.appId}")
//    private String appID;
//    @Value("${wx.mcId}")
//    private String mchId;
//    @Value("${wx.mchKey}")
//    private String key;
//    private InputStream certStream;
//    @Autowired
//    private IWXPayDomain wxPayDomain;
//
//    @Override
//    public String getAppID() {
//        return this.appID;
//    }
//
//    @Override
//    public String getMchID() {
//        return this.mchId;
//    }
//
//    @Override
//    public String getKey() {
//        return this.key;
//    }
//
//    @Override
//    public InputStream getCertStream() {
//        return this.certStream;
//    }
//
//    @Override
//    public IWXPayDomain getWXPayDomain() {
//        return this.wxPayDomain;
//    }
//}
