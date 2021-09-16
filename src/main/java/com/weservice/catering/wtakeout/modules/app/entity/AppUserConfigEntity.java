package com.weservice.catering.wtakeout.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.weservice.catering.wtakeout.modules.app.entity.res.AppLoginRes;
import lombok.Data;

@Data
@TableName("user_config")
public class AppUserConfigEntity {
    @TableId
    private Long id;
    private String shareBackgroundColor;
    private String showNotice;
    private String contactWx;
}
