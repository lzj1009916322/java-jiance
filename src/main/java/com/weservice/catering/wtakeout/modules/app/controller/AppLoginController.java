/**
 *
 */

package com.weservice.catering.wtakeout.modules.app.controller;


import com.weservice.catering.wtakeout.common.exception.RRException;
import com.weservice.catering.wtakeout.common.utils.R;
import com.weservice.catering.wtakeout.common.utils.Response;
import com.weservice.catering.wtakeout.modules.app.entity.AppUserConfigEntity;
import com.weservice.catering.wtakeout.modules.app.entity.AppUserInfoEntity;
import com.weservice.catering.wtakeout.modules.app.entity.req.AppUserOfUnionInfo;
import com.weservice.catering.wtakeout.modules.app.entity.res.AppLoginRes;
import com.weservice.catering.wtakeout.modules.app.entity.res.WXLoginForOpenIdRes;
import com.weservice.catering.wtakeout.modules.app.form.LoginForm;
import com.weservice.catering.wtakeout.modules.app.service.AppUserConfigService;
import com.weservice.catering.wtakeout.modules.app.service.AppUserInfoService;
import com.weservice.catering.wtakeout.modules.app.service.UserService;
import com.weservice.catering.wtakeout.modules.app.utils.JwtUtils;
import com.weservice.catering.wtakeout.modules.app.utils.SetTimestampForEntity;
import com.weservice.catering.wtakeout.modules.app.utils.WXLoginUtil;
import com.weservice.catering.wtakeout.modules.sys.service.SysUserTokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * APP登录授权
 *
 *
 */
@RestController
@RequestMapping("/app")
@Api(value = "APP登录接口", tags = "app登录接口")
public class AppLoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private AppUserConfigService appUserConfigService;
    @Autowired
    private AppUserInfoService appUserInfoService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private WXLoginUtil wxLoginUtil;
    @Autowired
    private SysUserTokenService sysUserTokenService;

    /**
     * 登录
     */
    @PostMapping("/login")
    @ApiOperation(value = "登录")
    @Transactional(rollbackFor = Exception.class)
    public Response<AppLoginRes> login(@RequestBody LoginForm form) {
        WXLoginForOpenIdRes login = wxLoginUtil.login(form.getCode());
        String openId = login.getOpenId();
        //生成token
        CompletableFuture<AppUserConfigEntity> future = CompletableFuture.supplyAsync(() -> appUserConfigService.getById(1));
        String token = sysUserTokenService.createTokenForAppUser(openId);
        checkAndUpdateUser(openId);
        AppLoginRes res = new AppLoginRes();
        res.setToken(token);
        res.setUserId(openId);
        res.setExpireTime(jwtUtils.getExpire());
        try {
            Optional<AppUserConfigEntity> appUserConfigEntity = Optional.ofNullable(future.get());
            appUserConfigEntity.ifPresent(x -> {
                res.setShareBackgroundColor(x.getShareBackgroundColor());
                res.setContactWx(x.getContactWx());
                res.setShowNotice(x.getShowNotice());
            });

        } catch (Exception e) {
            throw new RRException("获取用户配置失败");
        }
        return Response.ok(res);
    }

    @PostMapping("/updateUserInfo")
    @ApiOperation("登录")
    @Transactional(rollbackFor = Exception.class)
    public Response updateUserInfo(@RequestBody AppUserInfoEntity appUserInfoEntity) {
        appUserInfoService.updateEntity(appUserInfoEntity);
        return Response.ok();
    }

    private void checkAndUpdateUser(String openId) {
        if (StringUtils.isEmpty(openId)) {
            return;
        }
        AppUserInfoEntity appUserInfoEntity = appUserInfoService.getById(openId);
        if (null == appUserInfoEntity || StringUtils.isEmpty(appUserInfoEntity.getUserId())) {
            AppUserInfoEntity appUserInfoEntityInsert = new AppUserInfoEntity();
            appUserInfoEntityInsert.setUserId(openId);
            appUserInfoEntityInsert.setCreateTime(new Date());
//            SetTimestampForEntity.setCreateTime(appUserInfoEntityInsert);
            appUserInfoService.save(appUserInfoEntityInsert);
        }
    }

}
