package com.weservice.catering.wtakeout.modules.wxpay.entity;

import lombok.Data;

@Data
public class WXResponse {
    private String code;
    private String message;

    public WXResponse ok() {
        this.code = "200";
        this.message = "成功";
        return this;
    }

    public WXResponse fail() {
        this.code = "500";
        this.message = "系统错误";
        return this;
    }
}
