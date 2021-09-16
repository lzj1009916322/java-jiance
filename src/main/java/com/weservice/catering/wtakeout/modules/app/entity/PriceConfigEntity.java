package com.weservice.catering.wtakeout.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ${comments}
 *
 * @author
 * @email
 * @date 2021-04-24 11:42:31
 */
@Data
@TableName("price_config")
@ApiModel("价格信息")
public class PriceConfigEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * $column.comments
     */
    @TableId
    private Integer id;
    /**
     * 类型
     */
    @ApiModelProperty("类型，条目 member:会员价格")
    private String type;
    /**
     * 单价
     */
    @ApiModelProperty(value = "价格,单位元", example = "1.00")
    private BigDecimal price;
    /**
     * 单位
     */
    @ApiModelProperty(value = "单位，week: 周", example = "week")
    private String unit;

}
