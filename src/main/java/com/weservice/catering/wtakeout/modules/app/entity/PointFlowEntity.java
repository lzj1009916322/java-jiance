package com.weservice.catering.wtakeout.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("point_flow")
public class PointFlowEntity {
    @TableId
    private Long id;
    private BigDecimal originPoint;
    private BigDecimal actualPoint;
    private String type;
    private Date createTime;
    private Date updateTime;
}
