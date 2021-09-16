/**
 *
 *
 */

package com.weservice.catering.wtakeout.modules.app.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 登录表单
 *
 *
 */
@Data
@ApiModel(value = "app用户登录")
public class LoginForm {
    @ApiModelProperty(value = "wx.login获取的code")
    @NotBlank(message="wx.login获取的code")
    private String code;

//    @ApiModelProperty(value = "密码")
//    @NotBlank(message="密码不能为空")
//    private String password;

}
