package com.weservice.catering.wtakeout.modules.wxpay.constant;

public enum URLEnum {

    UNIFIED_ORDER("/v3/pay/transactions/jsapi","统一下单");

    private String url;
    private String desc;


    URLEnum(String url, String dex) {
    }

    public String getUrl() {
        return this.url;
    }
}
