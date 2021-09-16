package com.weservice.catering.wtakeout.modules.app.controller;

import com.weservice.catering.wtakeout.common.utils.PageUtils;
import com.weservice.catering.wtakeout.common.utils.Response;
import com.weservice.catering.wtakeout.modules.app.entity.MerchBillStatisticsEntity;
import com.weservice.catering.wtakeout.modules.app.entity.ThreadLocalUserInfo;
import com.weservice.catering.wtakeout.modules.app.entity.res.MemberInfoAndDetail;
import com.weservice.catering.wtakeout.modules.app.service.MemberInfoService;
import com.weservice.catering.wtakeout.modules.app.service.MerchBillService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/report")
@Api(value = "会员信息", tags = "会员信息")
public class ReportController {
    @Autowired
    private MerchBillService merchBillService;

    @GetMapping("/billStatistics/list")
    @ResponseBody
    public Response<PageUtils<MerchBillStatisticsEntity>> billStatisticsList(@RequestParam Map<String, Object> params) {
        PageUtils<MerchBillStatisticsEntity> merchBillStatisticsEntityPageUtils = merchBillService.queryPage(params);
        return Response.ok(merchBillStatisticsEntityPageUtils);
    }

    @RequestMapping(value = "/billStatistics/download",method = RequestMethod.GET)
    public void billStatisticsDownload(@RequestParam Map<String, Object> params, HttpServletResponse response) {
        merchBillService.download(params, response);
    }
}
