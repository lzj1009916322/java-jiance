package com.weservice.catering.wtakeout.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
@TableName("member_info")
@ApiModel("会员信息")
public class MemberInfoEntity {
    @TableId
    private String userId;
    @ApiModelProperty("到期时间")
    private Date limitTime;
    private Date createTime;
    private Date updateTime;
}
