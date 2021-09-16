package com.weservice.catering.wtakeout.modules.app.controller;

import com.weservice.catering.wtakeout.common.utils.Response;
import com.weservice.catering.wtakeout.modules.app.entity.ThreadLocalUserInfo;
import com.weservice.catering.wtakeout.modules.app.entity.res.MemberInfoAndDetail;
import com.weservice.catering.wtakeout.modules.app.service.MemberInfoService;
import com.weservice.catering.wtakeout.modules.appconfig.entity.AppLawStatementEntity;
import com.weservice.catering.wtakeout.modules.appconfig.entity.AppUserGuideEntity;
import com.weservice.catering.wtakeout.modules.appconfig.service.AppLawStatementService;
import com.weservice.catering.wtakeout.modules.appconfig.service.AppUserGuideService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/miscellaneous")
@Api(value = "杂项信息", tags = "杂项信息")
public class MiscellaneousController {
    @Autowired
    private AppUserGuideService appUserGuideService;

    @Autowired
    private AppLawStatementService appLawStatementService;

    @GetMapping("/getAppUserGuide")
    public Response<AppUserGuideEntity> getAppUserGuide() {
        AppUserGuideEntity data = appUserGuideService.getLatest();
        return Response.ok(data);
    }
    @GetMapping("/getLawStatement")
    public Response<AppLawStatementEntity> getLawStatement() {
        AppLawStatementEntity data = appLawStatementService.getLatest();
        return Response.ok(data);
    }
}
