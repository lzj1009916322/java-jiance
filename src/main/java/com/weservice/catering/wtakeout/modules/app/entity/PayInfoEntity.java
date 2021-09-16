package com.weservice.catering.wtakeout.modules.app.entity;

import cn.hutool.db.DaoTemplate;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("pay_info")
@NoArgsConstructor
@ApiModel("付款信息")
public class PayInfoEntity {
    public PayInfoEntity(BigDecimal money) {
        this.money = money;
    }

    @TableId(type = IdType.INPUT)
    @ApiModelProperty
    private String id;
    @ApiModelProperty("付款金额")
    private BigDecimal money;
    @ApiModelProperty("付款时间")
    private Date createTime;
    private Date updateTime;
    private String flag;
    private String userId;
    private String userName;
    private String type;
}
