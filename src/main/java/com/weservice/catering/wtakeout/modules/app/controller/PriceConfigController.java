package com.weservice.catering.wtakeout.modules.app.controller;

import java.util.Arrays;
import java.util.Map;

import com.weservice.catering.wtakeout.common.utils.Response;
import com.weservice.catering.wtakeout.modules.app.entity.PriceConfigEntity;
import com.weservice.catering.wtakeout.modules.app.service.PriceConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.weservice.catering.wtakeout.common.utils.PageUtils;


/**
 * ${comments}
 *
 * @author
 * @email
 * @date 2021-04-24 11:42:31
 */
@RestController
@RequestMapping("/price/priceInfo")
@Api(value = "单价", tags = "价格信息")
public class PriceConfigController {
    @Autowired
    private PriceConfigService priceConfigService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation("获取价格列表")
//    @RequiresPermissions("user:priceinfo:list")
    public Response<PageUtils<PriceConfigEntity>> list(@RequestParam Map<String, Object> params) {
        PageUtils<PriceConfigEntity> page = priceConfigService.queryPage(params);
        return Response.ok(page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("user:priceinfo:info")
    public Response<PriceConfigEntity> info(@PathVariable("id") Integer id) {
        PriceConfigEntity priceInfo = priceConfigService.getById(id);

        return Response.ok(priceInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("user:priceinfo:save")
    public Response save(@RequestBody PriceConfigEntity priceInfo) {
        priceConfigService.save(priceInfo);

        return Response.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("user:priceinfo:update")
    public Response update(@RequestBody PriceConfigEntity priceInfo) {
        priceConfigService.updateById(priceInfo);

        return Response.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("user:priceinfo:delete")
    public Response delete(@RequestBody Integer[] ids) {
        priceConfigService.removeByIds(Arrays.asList(ids));

        return Response.ok();
    }

}
