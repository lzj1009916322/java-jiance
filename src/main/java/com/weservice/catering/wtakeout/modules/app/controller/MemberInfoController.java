package com.weservice.catering.wtakeout.modules.app.controller;

import com.weservice.catering.wtakeout.common.utils.Response;
import com.weservice.catering.wtakeout.modules.app.entity.MemberInfoEntity;
import com.weservice.catering.wtakeout.modules.app.entity.ThreadLocalUserInfo;
import com.weservice.catering.wtakeout.modules.app.entity.req.WxPrePayReqEntity;
import com.weservice.catering.wtakeout.modules.app.entity.res.WxPrePaytResEntity;
import com.weservice.catering.wtakeout.modules.app.service.MemberInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/member/memberInfo")
@Api(value = "会员信息", tags = "会员信息")
public class MemberInfoController {
    @Autowired
    private MemberInfoService memberInfoService;

    @GetMapping("/query")
    @ApiOperation("查询会员信息")
    public Response<MemberInfoEntity> query(@RequestParam Map<String, Object> params) {
        return Response.ok(memberInfoService.getById(ThreadLocalUserInfo.getUserInfo().getAppUserId()));
//        List<>memberInfoService.queryRecent();
    }

    @PostMapping("/prePay")
    @ApiOperation("预付款")
    public Response<WxPrePaytResEntity> pay(@RequestBody WxPrePayReqEntity wxPrePayReqEntity) {
        return memberInfoService.prePay(wxPrePayReqEntity);
    }
}
