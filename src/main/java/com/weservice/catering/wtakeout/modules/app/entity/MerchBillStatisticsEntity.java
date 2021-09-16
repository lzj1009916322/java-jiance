package com.weservice.catering.wtakeout.modules.app.entity;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class MerchBillStatisticsEntity {
    private Integer productId;
    private String productName;
    private Integer sum;
    private BigDecimal sumCost;
    private BigDecimal sumReturnPoint;
}
