package com.weservice.catering.wtakeout.modules.app.utils;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.region.Region;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class COSConfiguration {
    @Value("${cloud.cos.secretId}")
    private String cosSecretId;
    @Value("${cloud.cos.secretKey}")
    private String cosSecretKey;

    @Bean
    public COSClient COSClient() {
        String secretId = cosSecretId;
        String secretKey = cosSecretKey;
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
// 设置 bucket 的区域, COS 地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
        ClientConfig clientConfig = new ClientConfig(new Region("ap-chengdu"));
// 配置使用 https
        clientConfig.setHttpProtocol(HttpProtocol.https);
// 生成 cos 客户端
        return new COSClient(cred, clientConfig);
    }
}
