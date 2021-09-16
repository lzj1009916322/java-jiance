package com.weservice.catering.wtakeout.modules.appconfig.controller;

import com.weservice.catering.wtakeout.common.utils.PageUtils;
import com.weservice.catering.wtakeout.common.utils.Response;
import com.weservice.catering.wtakeout.modules.appconfig.entity.AppUserGuideEntity;
import com.weservice.catering.wtakeout.modules.appconfig.service.AppUserGuideService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * ${comments}
 *
 * @author
 * @email
 * @date 2021-03-24 19:48:52
 */
@RestController
@RequestMapping("appconfig/appuserguide")
public class AppUserGuideController {
    @Autowired
    private AppUserGuideService appUserGuideService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("appconfig:appuserguide:list")
    public Response<PageUtils<AppUserGuideEntity>> list(@RequestParam Map<String, Object> params){
        PageUtils page = appUserGuideService.queryPage(params);

        return Response.ok(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("appconfig:appuserguide:info")
    public Response<AppUserGuideEntity> info(@PathVariable("id") Integer id){
		AppUserGuideEntity appUserGuide = appUserGuideService.getById(id);

        return Response.ok(appUserGuide);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("appconfig:appuserguide:save")
    public Response save(@RequestBody AppUserGuideEntity appUserGuide){
		appUserGuideService.add(appUserGuide);

        return Response.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("appconfig:appuserguide:update")
    public Response update(@RequestBody AppUserGuideEntity appUserGuide){
		appUserGuideService.update(appUserGuide);

        return Response.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("appconfig:appuserguide:delete")
    public Response delete(@RequestBody Integer[] ids){
		appUserGuideService.removeByIds(Arrays.asList(ids));

        return Response.ok();
    }

}
