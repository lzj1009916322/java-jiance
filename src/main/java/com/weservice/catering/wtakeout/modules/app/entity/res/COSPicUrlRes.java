package com.weservice.catering.wtakeout.modules.app.entity.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("图片上传返回")
@Data
@NoArgsConstructor
public class COSPicUrlRes {
    @ApiModelProperty("url")
    private String url;
    private String urlPrefix = "https://wservice-1300628366.cos.ap-chengdu.myqcloud.com/";

    public COSPicUrlRes(String url) {
        this.url = urlPrefix + url;
    }
}
