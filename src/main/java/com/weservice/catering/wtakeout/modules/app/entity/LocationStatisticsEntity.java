package com.weservice.catering.wtakeout.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@TableName("location_statistics")
@NoArgsConstructor
public class LocationStatisticsEntity {
    @TableId
    private Long id;
    private String userId;
    private String type;
    private Float longitude;
    private Float latitude;
    private String address;
    private Date createTime;
    private Date updateTime;

    public LocationStatisticsEntity(String type, Float longitude, Float latitude) {
        this.type = type;
        this.longitude = longitude;
        this.latitude = latitude;
        this.createTime = new Date();
    }
}
