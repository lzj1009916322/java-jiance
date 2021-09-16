package com.weservice.catering.wtakeout.modules.app.entity.res;

import com.weservice.catering.wtakeout.modules.app.entity.AppUserConfigEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("登录返回")
public class AppLoginRes  extends AppUserConfigEntity {
    @ApiModelProperty("token")
    private String token;
    @ApiModelProperty("过期时间")
    private long expireTime;
    @ApiModelProperty("userId")
    private String userId;

}
