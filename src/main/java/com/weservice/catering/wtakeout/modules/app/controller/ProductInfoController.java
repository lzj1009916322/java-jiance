package com.weservice.catering.wtakeout.modules.app.controller;

import com.weservice.catering.wtakeout.common.utils.PageUtils;
import com.weservice.catering.wtakeout.common.utils.Response;
import com.weservice.catering.wtakeout.modules.app.annotation.CollectParam;
import com.weservice.catering.wtakeout.modules.app.constant.LocationStatisticsEnum;
import com.weservice.catering.wtakeout.modules.app.entity.BuyEntity;
import com.weservice.catering.wtakeout.modules.app.entity.IsBoughtDetailEntity;
import com.weservice.catering.wtakeout.modules.app.entity.ProductInfoEntity;
import com.weservice.catering.wtakeout.modules.app.service.LocationStatisticsService;
import com.weservice.catering.wtakeout.modules.app.service.ProductInfoService;
import com.weservice.catering.wtakeout.modules.app.utils.IdempotencyUtil;
import com.weservice.catering.wtakeout.modules.app.utils.UrlUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
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
@RequestMapping("/product/productInfo")
@Api(value = "商品", tags = "商品")
public class ProductInfoController {
    @Autowired
    private ProductInfoService productInfoService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation("商品列表")
    @CollectParam(type = LocationStatisticsEnum.PRODUCT_LIST)
    //    @RequiresPermissions("user:productinfo:list")
    public Response<PageUtils<ProductInfoEntity>> list(@RequestParam Map<String, Object> params) {
        PageUtils<ProductInfoEntity> page = productInfoService.queryPage(params);
        return Response.ok(page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @ApiOperation("商品列表")
//    @RequiresPermissions("user:productinfo:info")
    public Response<ProductInfoEntity> info(@PathVariable("id") Integer id) {
        ProductInfoEntity productInfo = productInfoService.getById(id);
        ProductInfoEntity productInfoEntity = UrlUtil.transferUrl(productInfo);
        return Response.ok(productInfoEntity);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @ApiOperation("保存商品")
//    @RequiresPermissions("user:productinfo:save")
    public Response<?> save(@RequestBody ProductInfoEntity productInfo) {
        productInfo.setCreateTime(new Date());
        productInfo.setUpdateTime(new Date());
        productInfo.setTotalLeft(productInfo.getTotalLimit());
        productInfo.setRules(productInfo.getRules().replace("；", ";"));
        productInfoService.save(productInfo);
        return Response.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @ApiOperation("修改商品")
//    @RequiresPermissions("user:productinfo:update")
    public Response<?> update(@RequestBody ProductInfoEntity productInfo) {
        productInfo.setUpdateTime(new Date());
        productInfo.setRules(productInfo.getRules().replace("；", ";"));
        productInfoService.updateEntity(productInfo);
        return Response.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @ApiOperation("删除商品")
//    @RequiresPermissions("user:productinfo:delete")
    public Response<?> delete(@RequestBody Integer[] ids) {
//        for (Integer) {
//
//        }
        productInfoService.removeByIds(Arrays.asList(ids));
        return Response.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/onSale")
    @ApiOperation("删除商品")
//    @RequiresPermissions("user:productinfo:delete")
    public Response<?> onSale(@RequestBody Integer[] ids) {
//        for (Integer) {
//
//        }
        productInfoService.onSale(Arrays.asList(ids));
        return Response.ok();
    }

    @PostMapping("/outSale")
    @ApiOperation("删除商品")
//    @RequiresPermissions("user:productinfo:delete")
    public Response<?> outSale(@RequestBody Integer[] ids) {
//        for (Integer) {
//
//        }
        productInfoService.outSale(Arrays.asList(ids));
        return Response.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/buy")
    @ApiOperation("产品购买")
//    @RequiresPermissions("user:productinfo:delete")
    public Response<?> buy(@RequestBody BuyEntity buyEntity) {
        productInfoService.buyProduct(buyEntity.getIds());
        return Response.ok();
    }

    @GetMapping("/detail")
    @ApiOperation("获取商品详情，并判断该商品是否已购买")
    @ApiImplicitParams({@ApiImplicitParam(name = "productId", value = "产品ID", dataType = "Integer", required = true),
            @ApiImplicitParam(name = "latitude", value = "纬度", dataType = "Float", required = true),
            @ApiImplicitParam(name = "longitude", value = "经度", dataType = "Float", required = true)})
    @CollectParam(type = LocationStatisticsEnum.PRODUCT_DETAIL)
    public Response<IsBoughtDetailEntity> getDetailByToken(@NotNull Integer productId, @NotNull Float latitude, @NotNull Float longitude) {
        IsBoughtDetailEntity isBoughtDetailEntity = productInfoService.getDetailByToken(productId, latitude, longitude);
        return Response.ok(isBoughtDetailEntity);
    }
}
