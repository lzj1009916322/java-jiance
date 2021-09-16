package com.weservice.catering.wtakeout.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("member_flow")
public class MemberFlowEntity {
    @TableId
    private Long id;
    private BigDecimal pay;
    private String transactionId;
    private Date createTime;
    private Date updateTime;
}
