package com.weservice.catering.wtakeout.modules.app.controller;

import com.weservice.catering.wtakeout.common.utils.R;
import com.weservice.catering.wtakeout.common.utils.Response;
import com.weservice.catering.wtakeout.modules.app.entity.PicInfoEntity;
import com.weservice.catering.wtakeout.modules.app.entity.res.COSPicUrlRes;
import com.weservice.catering.wtakeout.modules.app.service.PicInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

@RestController
@RequestMapping("/pic/picInfo")
@Api(value = "图片信息", tags = "订单")
public class PicController {
    @Autowired
    private PicInfoService picInfoService;

    @PostMapping("/upload")
    @ApiOperation("上传图片")
    @ApiImplicitParams({@ApiImplicitParam(name = "file", value = "文件"),
            @ApiImplicitParam(name = "bizId", value = "业务id"),
            @ApiImplicitParam(name = "type", value = "文件类型")})
    @Transactional(rollbackFor = Exception.class)
//    @RequiresPermissions("user:orderinfo:update")
    public Response uploadPic(MultipartFile file, String bizId, String type) {
        picInfoService.uploadFile(file, bizId, type);
        return Response.ok();
    }

    @DeleteMapping("/deleteCOS")
    public Response deletePic(String fullPath) {
        picInfoService.deleteFile(fullPath);
        return Response.ok();
    }

    @PostMapping("/upload2COS")
    @ApiOperation("上传图片到对象存储")
    public Response<COSPicUrlRes> upLoad2COS(MultipartFile file) {
        COSPicUrlRes cosPicUrlRes = picInfoService.uploadFile2COs(file);
        return Response.ok(cosPicUrlRes);
    }

    @PostMapping("/uploadEditorImg2COS")
    @ApiOperation("上传图片到对象存储")
    public R uploadEditorImg2COS(MultipartFile upload) {
        COSPicUrlRes cosPicUrlRes = picInfoService.uploadFile2COs(upload);
        return R.ok().put("url", cosPicUrlRes.getUrl());
    }
}
