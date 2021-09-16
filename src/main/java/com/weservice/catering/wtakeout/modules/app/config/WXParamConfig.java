package com.weservice.catering.wtakeout.modules.app.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class WXParamConfig {
    // TODO: 2021/2/15  加解密 
    @Value("${wx.openId}")
    private String openId;
    @Value("${wx.secret}")
    private String secret;

}
