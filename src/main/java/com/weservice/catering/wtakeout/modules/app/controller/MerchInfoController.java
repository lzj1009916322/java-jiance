package com.weservice.catering.wtakeout.modules.app.controller;

import com.weservice.catering.wtakeout.common.utils.PageUtils;
import com.weservice.catering.wtakeout.common.utils.R;
import com.weservice.catering.wtakeout.common.utils.Response;
import com.weservice.catering.wtakeout.modules.app.entity.MerchInfoEntity;
import com.weservice.catering.wtakeout.modules.app.service.MerchInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * ${comments}
 *
 * @author
 * @email
 * @date 2021-02-01 23:48:01
 */
@RestController
@RequestMapping("/merch/merchInfo")
@Api(value = "商户信息接口", tags = "商户信息")
public class MerchInfoController {
    @Autowired
    private MerchInfoService merchInfoService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation("商户信息列表")
//    @RequiresPermissions("user:merchinfo:list")
    public Response<PageUtils<MerchInfoEntity>> list(@RequestParam Map<String, Object> params) {
        PageUtils<MerchInfoEntity> page = merchInfoService.queryPage(params);

        return Response.ok(page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
//    @RequiresPermissions("user:merchinfo:info")
    public Response<MerchInfoEntity> info(@PathVariable("id") Integer id) {
        MerchInfoEntity merchInfo = merchInfoService.getById(id);

        return Response.ok(merchInfo);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @ApiOperation("保存商户信息")
//    @RequiresPermissions("user:merchinfo:save")
    public Response save(@RequestBody MerchInfoEntity merchInfo) {
        merchInfoService.save(merchInfo);

        return Response.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @ApiOperation("修改商户信息")
//    @RequiresPermissions("user:merchinfo:update")
    public Response update(@RequestBody MerchInfoEntity merchInfo) {
        merchInfoService.updateById(merchInfo);

        return Response.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @ApiOperation("删除商户信息")
//    @RequiresPermissions("user:merchinfo:delete")
    public Response delete(@RequestBody Integer[] ids) {
        merchInfoService.removeByIds(Arrays.asList(ids));

        return Response.ok();
    }

}
