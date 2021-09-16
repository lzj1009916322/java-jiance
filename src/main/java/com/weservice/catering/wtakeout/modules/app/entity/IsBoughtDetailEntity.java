package com.weservice.catering.wtakeout.modules.app.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class IsBoughtDetailEntity extends ProductInfoEntity {
    @ApiModelProperty(value = "是否购买", name = "bought")
    private boolean isBought;
    @ApiModelProperty(value = "订单编号")
    private String orderId;
    @ApiModelProperty(value = "状态")
    private String orderStatus;
}
