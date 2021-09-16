package com.weservice.catering.wtakeout.modules.app.entity.req;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
@ApiModel("产品信息请求")
public class ProductInfoReq implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * $column.comments
     */
    @TableId
    @ApiModelProperty("产品ID")
    private Integer productId;
    /**
     * 链接
     */
    private String url;
    /**
     * 商品名称
     */
    @ApiModelProperty("产品名称")
    private String productName;
    /**
     * 商品类型（mt：美团，el:饿了吗）
     */
    @ApiModelProperty("产品类型")
    private String productType;
    /**
     * 商户id
     */
    @ApiModelProperty("商户id")
    private Integer merchId;
    /**
     * 商品限额
     */
    @ApiModelProperty("限额")
    private Integer totalLimit;
    /**
     * 商品有效期限
     */
    @ApiModelProperty("有效期")
    private Date limitTime;
    /**
     * 返还积分数额
     */
    @ApiModelProperty("返回数额")
    private BigDecimal returnPoint;
    /**
     * 剩余商品数量
     */
    @ApiModelProperty("剩余数量")
    private Integer totalLeft;
    /**
     * 推荐
     */
    @ApiModelProperty("是否推荐")
    private String recommend;
    /**
     * 修改时间
     */
    private Date updateTime;
    /**
     * 创建时间
     */
    private Date createTime;
    @ApiModelProperty("距离")
    private Long distance;
    @ApiModelProperty("经度")
    private Float longitude;
    @ApiModelProperty("纬度")
    private Float latitude;

}
