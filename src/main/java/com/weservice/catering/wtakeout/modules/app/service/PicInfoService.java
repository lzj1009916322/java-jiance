package com.weservice.catering.wtakeout.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weservice.catering.wtakeout.common.utils.PageUtils;
import com.weservice.catering.wtakeout.modules.app.entity.PicInfoEntity;
import com.weservice.catering.wtakeout.modules.app.entity.res.COSPicUrlRes;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * ${comments}
 *
 * @author
 * @email
 * @date 2021-02-06 21:09:46
 */
public interface PicInfoService extends IService<PicInfoEntity> {

    PageUtils<PicInfoEntity> queryPage(Map<String, Object> params);

    Map<String, List<PicInfoEntity>> queryMapByBizId(String bizId);

    void uploadFile(MultipartFile file, String bizId, String type);

    void deleteFile(String fullPath);

    COSPicUrlRes uploadFile2COs(MultipartFile file);
}

