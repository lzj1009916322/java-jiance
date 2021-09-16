package com.weservice.catering.wtakeout.modules.app.controller;


import com.weservice.catering.wtakeout.common.utils.PageUtils;
import com.weservice.catering.wtakeout.common.utils.R;
import com.weservice.catering.wtakeout.common.utils.Response;
import com.weservice.catering.wtakeout.modules.app.entity.AppUserInfoEntity;
import com.weservice.catering.wtakeout.modules.app.entity.ThreadLocalUserInfo;
import com.weservice.catering.wtakeout.modules.app.service.AppUserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;


/**
 * ${comments}
 *
 * @author
 * @email
 * @date 2021-02-01 23:48:00
 */
@RestController
@RequestMapping("/appUser/info")
@Api(value = "微信用户信息", tags = "微信用户")
public class AppUserInfoController {
    @Autowired
    private AppUserInfoService appUserInfoService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation("获取用户信息")
//    @RequiresPermissions("user:info:list")
    public Response<PageUtils<AppUserInfoEntity>> list(@RequestParam Map<String, Object> params) {
        PageUtils<AppUserInfoEntity> page = appUserInfoService.queryPage(params);

        return Response.ok(page);
    }


    /**
     * 信息
     */
    @GetMapping("/infoByToken")
    @ApiOperation("根据token 获取用户信息")
//    @RequiresPermissions("user:info:info")
    public Response<AppUserInfoEntity> info() {
        String userId = ThreadLocalUserInfo.getUserInfo().getAppUserId();
        AppUserInfoEntity info = appUserInfoService.getById(userId);
        return Response.ok(info);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
//    @RequiresPermissions("user:info:save")
    public R save(@RequestBody AppUserInfoEntity info) {
        info.setCreateTime(new Date());
        appUserInfoService.save(info);
        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
//    @RequiresPermissions("user:info:update")
    public R update(@RequestBody AppUserInfoEntity info) {
        if (StringUtils.isEmpty(info.getUserId())) {
            info.setUserId(ThreadLocalUserInfo.getUserInfo().getAppUserId());
        }
        info.setUpdateTime(new Date());
        appUserInfoService.updateEntity(info);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
//    @RequiresPermissions("user:info:delete")
    public R delete(@RequestBody String[] userIds) {
        appUserInfoService.removeByIds(Arrays.asList(userIds));

        return R.ok();
    }

}
