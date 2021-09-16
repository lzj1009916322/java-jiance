package com.weservice.catering.wtakeout.modules.app.utils;

import com.alibaba.fastjson.JSON;
import com.weservice.catering.wtakeout.common.exception.RRException;
import com.weservice.catering.wtakeout.modules.app.config.HttpsClientRequestFactory;
import com.weservice.catering.wtakeout.modules.app.config.WXParamConfig;
import com.weservice.catering.wtakeout.modules.app.entity.res.WXLoginForOpenIdRes;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class WXLoginUtil {
    //    @Autowired
//    private RestTemplate restTemplate;
    @Autowired
    private WXParamConfig wxParamConfig;
    private String wxLoginForOpenIdUrl = "https://api.weixin.qq.com/sns/jscode2session";

    public WXLoginForOpenIdRes login(String code) {
        RestTemplate restTemplate = new RestTemplate();
        String url = generateUriParam(wxLoginForOpenIdUrl);
        url = url + "&js_code=" + code;
        try {
            log.info("log req:{}", url);
            String s = restTemplate.getForEntity(url, String.class).getBody();
            WXLoginForOpenIdRes body = JSON.parseObject(s, WXLoginForOpenIdRes.class);
            log.info("login res :{}", body);
            if (StringUtils.isEmpty(body.getErrCode())) {
                return body;
            } else {
                throw new RRException("登录失败");
            }
        } catch (Exception e) {
            log.error("login error:", e);
            throw new RRException("登录失败");
        }
//        WXLoginForOpenIdRes wxLoginForOpenIdRes = new WXLoginForOpenIdRes();
//        wxLoginForOpenIdRes.setOpenId("123");
//        return wxLoginForOpenIdRes;
    }

    private String generateUriParam(String baseUrl) {
        return baseUrl
                + "?appid=" + wxParamConfig.getOpenId()
                + "&secret=" + wxParamConfig.getSecret()
                + "&grant_type=" + "authorization_code";

    }
}
