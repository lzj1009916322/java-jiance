/**
 *
 */

package com.weservice.catering.wtakeout.modules.oss.cloud;


import com.weservice.catering.wtakeout.common.utils.ConfigConstant;
import com.weservice.catering.wtakeout.common.utils.Constant;
import com.weservice.catering.wtakeout.common.utils.SpringContextUtils;
import com.weservice.catering.wtakeout.modules.sys.service.SysConfigService;

/**
 * 文件上传Factory
 *
 *
 */
public final class OSSFactory {
    private static SysConfigService sysConfigService;

    static {
        OSSFactory.sysConfigService = (SysConfigService) SpringContextUtils.getBean("sysConfigService");
    }

    public static CloudStorageService build() {
        //获取云存储配置信息
        CloudStorageConfig config = sysConfigService.getConfigObject(ConfigConstant.CLOUD_STORAGE_CONFIG_KEY, CloudStorageConfig.class);

        if (config.getType() == Constant.CloudService.QINIU.getValue()) {
            return new QiniuCloudStorageService(config);
        } else if (config.getType() == Constant.CloudService.ALIYUN.getValue()) {
            return new AliyunCloudStorageService(config);
        } else if (config.getType() == Constant.CloudService.QCLOUD.getValue()) {
//            return new QcloudCloudStorageService(config);
            return null;
        }

        return null;
    }

}
