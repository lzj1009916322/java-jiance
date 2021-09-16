package com.weservice.catering.wtakeout.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * ${comments}
 *
 * @author
 * @email
 * @date 2021-02-01 23:48:00
 */
@Data
@TableName("product_info")
@ApiModel("商品信息")
public class ProductInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * $column.comments
     */
    @TableId
    @ApiModelProperty("商品id")
    private Integer productId;
    @ApiModelProperty("链接")
    private String url;
    @ApiModelProperty("商品名称")
    private String productName;
    @ApiModelProperty("类型，ml美团，el,饿了吗")
    private String productType;
    @ApiModelProperty("商户id")
    private Integer merchId;
    @ApiModelProperty("商品限额")
    private Integer totalLimit;
    @ApiModelProperty("截止时间")
    private Date limitTime;
    @ApiModelProperty("积分返还")
    private BigDecimal returnPoint;
    @ApiModelProperty("最低消费")
    private BigDecimal leastCost;
    @ApiModelProperty("会员返还积分")
    private BigDecimal memberReturnPoint;
    @ApiModelProperty("剩余商品数量")
    private Integer totalLeft;
    @ApiModelProperty("是否腿甲产品，y推荐，n非推荐")
    private String recommend;
    @ApiModelProperty("商品价格")
    @TableField(exist = false)
    private BigDecimal price;
    @TableField(exist = false)
    @ApiModelProperty("距离")
    private Float distance;
    private String address;
    @ApiModelProperty("经度")
    private Float longitude;
    @ApiModelProperty("纬度")
    private Float latitude;
    @ApiModelProperty("商品图片URL")
    private String productPicUrl;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 创建时间
     */
    private Date createTime;
    private Boolean saleFlag;
    @ApiModelProperty("规则")
    private String rules;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getLimitTime() {
        return limitTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")
    public void setLimitTime(Date limitTime) {
        this.limitTime = limitTime;
    }

}
