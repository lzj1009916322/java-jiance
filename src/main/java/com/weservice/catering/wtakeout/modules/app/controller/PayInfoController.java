package com.weservice.catering.wtakeout.modules.app.controller;

import com.weservice.catering.wtakeout.common.utils.PageUtils;
import com.weservice.catering.wtakeout.common.utils.Response;
import com.weservice.catering.wtakeout.modules.app.entity.OrderInfoEntity;
import com.weservice.catering.wtakeout.modules.app.entity.PayInfoEntity;
import com.weservice.catering.wtakeout.modules.app.entity.ThreadLocalUserInfo;
import com.weservice.catering.wtakeout.modules.app.service.PayInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/pay/payInfo")
@Api(value = "付款信息", tags = "付款信息")
public class PayInfoController {
    @Autowired
    private PayInfoService payInfoService;

    @GetMapping("/list")
    @ApiOperation(notes = "付款信息列表",value = "Response<PageUtils<T>>")
//    @RequiresPermissions("user:orderinfo:list")
    public Response<PageUtils<PayInfoEntity>> list(@RequestParam Map<String, Object> params) {
        String appUserId = ThreadLocalUserInfo.getUserInfo().getAppUserId();
        if (StringUtils.isEmpty(appUserId)) {
            params.put("userId", appUserId);
        }
        PageUtils<PayInfoEntity> page = payInfoService.queryPage(params);
        return Response.ok(page);
    }
}
