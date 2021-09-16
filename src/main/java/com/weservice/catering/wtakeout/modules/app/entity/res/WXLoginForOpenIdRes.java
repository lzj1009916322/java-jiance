package com.weservice.catering.wtakeout.modules.app.entity.res;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class WXLoginForOpenIdRes {

    @JsonProperty("openid")
    private String openId;
    @JsonProperty("session_key")
    private String sessionKey;
    @JsonProperty("unionid")
    private String unionId;
    @JsonProperty("errcode")
    private String errCode;
    @JsonProperty("errmsg")
    private String errMsg;
}
