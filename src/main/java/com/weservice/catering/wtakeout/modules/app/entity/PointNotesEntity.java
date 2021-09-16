package com.weservice.catering.wtakeout.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
@TableName("point_notes")
@ApiModel("积分记录信息，流水")
public class PointNotesEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * $column.comments
     */
    @TableId
    @ApiModelProperty("id")
    private Integer id;
    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private String userId;
    /**
     * 积分
     */
    @ApiModelProperty("积分")
    private BigDecimal point;
    /**
     * 商品id
     */
    @ApiModelProperty("状态，success,doing,fail")
    private String status;
    @ApiModelProperty("转账金额")
    private BigDecimal money;
    @TableField(exist = false)
    @ApiModelProperty("阿里支付图片")
    private String aliPayPicUrl;
    @TableField(exist = false)
    @ApiModelProperty("微信收款码")
    private String wechatPicUrl;
    @TableField(exist = false)
    @ApiModelProperty("用户电话")
    private String phone;
    @TableField(exist = false)
    @ApiModelProperty("用户昵称")
    private String nickName;
    @ApiModelProperty("类型")
    private String type;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;

}
