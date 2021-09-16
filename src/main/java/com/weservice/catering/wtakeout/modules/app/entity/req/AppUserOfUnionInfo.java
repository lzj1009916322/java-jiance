package com.weservice.catering.wtakeout.modules.app.entity.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("微信用户信息")
public class AppUserOfUnionInfo {
    @ApiModelProperty("openId")
    private String openId;
    @ApiModelProperty("union_id")
    private String unionId;
    @ApiModelProperty("昵称")
    private String nickName;
    @ApiModelProperty("头像")
    private String avatarUrl;
    //      0 未知 1 男性 2女性
    @ApiModelProperty("性别, 0 未知 1 男性 2女性")
    private String gender;
    @ApiModelProperty("国籍")
    private String country;
    @ApiModelProperty("省份")
    private String province;
    private String city;
    private String language;

}
