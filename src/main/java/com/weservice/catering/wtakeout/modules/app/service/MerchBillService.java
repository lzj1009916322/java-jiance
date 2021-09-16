package com.weservice.catering.wtakeout.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weservice.catering.wtakeout.common.utils.PageUtils;
import com.weservice.catering.wtakeout.modules.app.entity.MerchBillExcelEntity;
import com.weservice.catering.wtakeout.modules.app.entity.MerchBillStatisticsEntity;
import com.weservice.catering.wtakeout.modules.app.entity.PicInfoEntity;
import com.weservice.catering.wtakeout.modules.app.entity.res.COSPicUrlRes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * ${comments}
 *
 * @author
 * @email
 * @date 2021-02-06 21:09:46
 */
public interface MerchBillService {

    PageUtils<MerchBillStatisticsEntity> queryPage(Map<String, Object> params);

    void download(Map<String, Object> params, HttpServletResponse response);
}

