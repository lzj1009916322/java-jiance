package com.weservice.catering.wtakeout.modules.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
@TableName("user_info")
@ApiModel
public class AppUserInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId(value = "user_id", type = IdType.INPUT)
    @ApiModelProperty("用户Id ，openId")
    private String userId;
    @ApiModelProperty("union_id")
    private String unionId;
    /**
     * 电话
     */
    private String phone;
    /**
     * 是否会员（0，否，1是）
     */
    @ApiModelProperty("是否会员 0，否，1是")
    private String member;
    /**
     * 积分
     */
    @ApiModelProperty("积分")
    private BigDecimal point;
    /**
     * 地址
     */
    private String address;
    @ApiModelProperty("昵称")
    private String nickName;
    @ApiModelProperty("性别 0 未知，1 男性 2 女性")
    private String gender;
    @ApiModelProperty("头像")
    private String avatarUrl;
    @ApiModelProperty("微信收款码")
    private String wechatPic;
    @ApiModelProperty("支付宝收款码")
    private String aliPayPic;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;

}
