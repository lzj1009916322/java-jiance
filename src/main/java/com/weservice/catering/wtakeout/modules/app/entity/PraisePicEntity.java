package com.weservice.catering.wtakeout.modules.app.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class PraisePicEntity {
    @JSONField(name = "picId")
    private Integer praisePicId;
    /**
     * 图片url
     */
    @JSONField(name = "picUrl")
    private String praisePicUrl;
    @JSONField(name = "bizId")
    private String praiseBizId;
    @JSONField(name = "type")
    private String praiseType;
}
