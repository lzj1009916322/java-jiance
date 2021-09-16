package com.weservice.catering.wtakeout.modules.app.entity.req;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class WxPrePayReqEntity {
    private BigDecimal money;
}
