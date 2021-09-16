package com.weservice.catering.wtakeout.modules.app.entity.res;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("微信预支付返回")
public class WxPrePaytResEntity {
    @ApiModelProperty("appId")
    private String appId;
    @ApiModelProperty("时间搓")
    private String timeStamp;
    @ApiModelProperty("nonceStr")
    private String nonceStr;
    @ApiModelProperty("预支付ID")
    private String preId;
    @ApiModelProperty("签名类型")
    private String signType;
    private String paySign;
    private String tradeNo;
}
