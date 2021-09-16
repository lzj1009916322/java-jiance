package com.weservice.catering.wtakeout.modules.app.entity.req;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.weservice.catering.wtakeout.modules.app.entity.AppUserInfoEntity;
import com.weservice.catering.wtakeout.modules.app.entity.PointNotesEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class WithDrawEntity extends AppUserInfoEntity {
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
}
