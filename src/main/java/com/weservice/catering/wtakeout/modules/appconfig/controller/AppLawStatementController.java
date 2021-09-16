package com.weservice.catering.wtakeout.modules.appconfig.controller;

import java.util.Arrays;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weservice.catering.wtakeout.modules.appconfig.entity.AppLawStatementEntity;
import com.weservice.catering.wtakeout.modules.appconfig.service.AppLawStatementService;
import com.weservice.catering.wtakeout.common.utils.PageUtils;
import com.weservice.catering.wtakeout.common.utils.Response;



/**
 * ${comments}
 *
 * @author
 * @email
 * @date 2021-03-26 10:09:56
 */
@RestController
@RequestMapping("appconfig/applawstatement")
public class AppLawStatementController {
    @Autowired
    private AppLawStatementService appLawStatementService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("appconfig:applawstatement:list")
    public Response<PageUtils<AppLawStatementEntity>> list(@RequestParam Map<String, Object> params){
        PageUtils page = appLawStatementService.queryPage(params);

        return Response.ok(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("appconfig:applawstatement:info")
    public Response<AppLawStatementEntity> info(@PathVariable("id") Integer id){
		AppLawStatementEntity data = appLawStatementService.getById(id);

        return Response.ok(data);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("appconfig:applawstatement:save")
    public Response save(@RequestBody AppLawStatementEntity appLawStatement){
		appLawStatementService.add(appLawStatement);

        return Response.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("appconfig:applawstatement:update")
    public Response update(@RequestBody AppLawStatementEntity appLawStatement){
		appLawStatementService.update(appLawStatement);

        return Response.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("appconfig:applawstatement:delete")
    public Response delete(@RequestBody Integer[] ids){
		appLawStatementService.removeByIds(Arrays.asList(ids));

        return Response.ok();
    }

}
