package com.weservice.catering.wtakeout.modules.wxpay.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.URLUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.BaseWxPayRequest;
import com.github.binarywang.wxpay.bean.result.WxPayOrderQueryResult;
import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.github.binarywang.wxpay.v3.Credentials;
import com.github.binarywang.wxpay.v3.Validator;
import com.github.binarywang.wxpay.v3.WxPayV3HttpClientBuilder;
import com.github.binarywang.wxpay.v3.auth.AutoUpdateCertificatesVerifier;
import com.github.binarywang.wxpay.v3.auth.PrivateKeySigner;
import com.github.binarywang.wxpay.v3.auth.WxPayCredentials;
import com.github.binarywang.wxpay.v3.auth.WxPayValidator;
import com.github.binarywang.wxpay.v3.util.PemUtils;
import com.github.binarywang.wxpay.v3.util.SignUtils;
import com.weservice.catering.wtakeout.common.exception.RRException;
import com.weservice.catering.wtakeout.config.OrderIdGenerator;
import com.weservice.catering.wtakeout.modules.app.dao.PayInfoDao;
import com.weservice.catering.wtakeout.modules.app.entity.PayInfoEntity;
import com.weservice.catering.wtakeout.modules.app.entity.ThreadLocalUserInfo;
import com.weservice.catering.wtakeout.modules.app.entity.res.WxPrePaytResEntity;
import com.weservice.catering.wtakeout.modules.wxpay.config.WxConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.security.PrivateKey;
import java.util.Date;

/**
 * <p>
 * 微信支付工具类
 * </p>
 *
 * @author: guohaibin
 * @createDate: 2020/12/1
 */
@Component
@Slf4j
public class WxPayV3Util {

    @Value("${wx.v3PayNotifyUrl}")
    private String v3PayNotifyUrl;
    @Autowired
    private WxConfig wxConfig;
    @Autowired
    private PayInfoDao payInfoDao;
    private final WxPayService wxPayService;

    public WxPayV3Util(WxPayService wxPayService) {
        this.wxPayService = wxPayService;
    }

    @Transactional(rollbackFor = Exception.class)
    public WxPrePaytResEntity createOrderJSAPI(BigDecimal total, String openId, String desc, String type) {
        try {
            OrderIdGenerator orderIdGenerator = new OrderIdGenerator();
            PayInfoEntity payInfoEntity = new PayInfoEntity(total);
            String s = orderIdGenerator.nextUUID(payInfoEntity);
            payInfoEntity.setId(s);
            payInfoEntity.setCreateTime(new Date());
            payInfoEntity.setUpdateTime(new Date());
            payInfoEntity.setType(type);
            payInfoEntity.setUserId(ThreadLocalUserInfo.getUserInfo().getAppUserId());
            payInfoEntity.setFlag("pre");
            payInfoDao.insert(payInfoEntity);

            WxPayMpOrderResult orderJSAPI = createOrderJSAPI(wxConfig.getAppId(), wxConfig.getMchId(), wxConfig.getApiV3Key(), desc, total, s, openId, wxConfig.getPrivateKeyPath(), wxConfig.getPrivateCertPath(), false);
            WxPrePaytResEntity wxPrePaytResEntity = new WxPrePaytResEntity();
            BeanUtils.copyProperties(orderJSAPI, wxPrePaytResEntity);
            wxPrePaytResEntity.setPreId(orderJSAPI.getPackageValue().split("=")[1]);
            wxPrePaytResEntity.setTradeNo(s);
            return wxPrePaytResEntity;
        } catch (Exception e) {
            log.error("pay error:", e);
            throw new RRException("支付失败");
        }
    }

    /**
     * <p>
     * JSAPI支付
     * </p>
     *
     * @param appId       小程序appId
     * @param mchId       小程序绑定商户号
     * @param apiV3Key    api V3 密钥
     * @param description 商品描述(128)
     * @param total       订单总金额(分)
     * @param outTradeNo  订单号(需要生成)
     * @param openId      用户openId
     * @param privateKey  私钥
     * @param privateCert 证书
     * @param isUrl       私钥和证书是否为url资源，false则表示文件资源
     * @return com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult
     * @author guohaibin
     * @date 2020-12-02 13:56:02
     */
    public WxPayMpOrderResult createOrderJSAPI(String appId, String mchId, String apiV3Key,
                                               String description, BigDecimal total, String outTradeNo,
                                               String openId, String privateKey, String privateCert, boolean isUrl) throws Exception {
        WxPayService wxPayService = new WxPayServiceImpl();
        WxPayConfig wxPayConfig = createV3WxPayConfig(mchId, apiV3Key, privateKey, privateCert, isUrl);
        wxPayService.setConfig(wxPayConfig);
        //  设置参数
        JSONObject data = new JSONObject();
        data.put("appid", appId);
        data.put("mchid", mchId);
        data.put("description", description);
        data.put("out_trade_no", outTradeNo);
        data.put("notify_url", v3PayNotifyUrl);
        JSONObject amount = new JSONObject();
        amount.put("total", BaseWxPayRequest.yuanToFen(total.toString()));
        data.put("amount", amount);
        JSONObject payer = new JSONObject();
        payer.put("openid", openId);
        data.put("payer", payer);
        //  调接口
        String result = wxPayService.postV3("https://api.mch.weixin.qq.com/v3/pay/transactions/jsapi",
                data.toString());
        JSONObject prepayObject = JSONUtil.parseObj(result);
        //  获取paySign
        if (prepayObject.containsKey("prepay_id")) {
            //  预支付签名
            String packageStr = "prepay_id=" + JSONUtil.parseObj(result).getStr("prepay_id");
            String nonceStr = SignUtils.genRandomStr();
            String timeStamp = String.valueOf(System.currentTimeMillis());
            String signType = "RSA";
            //  构造paySign
            String signStr = appId + "\n" + timeStamp + "\n" + nonceStr + "\n" + packageStr + "\n";
            String paySign = SignUtils.sign(signStr, getPrivateKey(privateKey, isUrl));
            return new WxPayMpOrderResult(null, timeStamp, nonceStr, packageStr, signType, paySign);
        } else {
            //  TODO 报错并打印有用信息
            throw new WxPayException("v3获取预支付异常");
        }
    }

    /**
     *
     */
    public WxPayOrderQueryResult queryOrder(String tranId) {
        try {
            return queryOrder(wxConfig.getMchId(), tranId, wxConfig.getApiV3Key(), wxConfig.getPrivateKeyPath(), wxConfig.getPrivateCertPath(), false);
        } catch (Exception e) {
            log.error("queryOrder", e);
            throw new RRException("error");
        }
    }

    /**
     * <p>
     * 查询订单
     * </p>
     *
     * @param mchId       商户号
     * @param outTradeNo  订单号
     * @param apiV3Key    api V3 密钥
     * @param privateKey  私钥
     * @param privateCert 证书
     * @param isUrl       私钥和证书是否为url资源，false则表示文件资源
     * @return com.github.binarywang.wxpay.bean.result.WxPayOrderQueryResult
     * @author guohaibin
     * @date 2020-12-02 15:33:15
     */
    public WxPayOrderQueryResult queryOrder(String mchId, String outTradeNo, String apiV3Key,
                                            String privateKey, String privateCert, boolean isUrl) throws Exception {
        WxPayService wxPayService = new WxPayServiceImpl();
        WxPayConfig wxPayConfig = createV3WxPayConfig(mchId, apiV3Key, privateKey, privateCert, isUrl);
        wxPayService.setConfig(wxPayConfig);
        String result = wxPayService.getV3(URLUtil.toURI("https://api.mch.weixin.qq.com/v3/pay/transactions/out-trade-no/" +
                outTradeNo + "?mchid=" + mchId));
        System.out.println(result);
        WxPayOrderQueryResult wxPayOrderQueryResult = JSON.parseObject(result, WxPayOrderQueryResult.class);
        return wxPayOrderQueryResult;
    }

    /**
     * <p>
     * 生成v3 http client并返回WxPayConfig
     * </p>
     *
     * @param mchId       商户号
     * @param apiV3Key    api V3 密钥
     * @param privateKey  私钥
     * @param privateCert 证书
     * @param isUrl       私钥和证书是否为url资源，false则表示文件资源
     * @return com.github.binarywang.wxpay.config.WxPayConfig
     * @author guohaibin
     * @date 2020-12-02 13:53:14
     */
    private WxPayConfig createV3WxPayConfig(String mchId, String apiV3Key,
                                            String privateKey, String privateCert, boolean isUrl) {
        WxPayConfig wxPayConfig = new WxPayConfig();
        if (isUrl) {
            //  手动构建http client - 通过url的方式来读取证书/密钥
            Credentials credentials = new WxPayCredentials(
                    mchId,
                    new PrivateKeySigner(
                            PemUtils.loadCertificate(URLUtil.getStream(URLUtil.url(privateCert))).getSerialNumber().toString(16),
                            PemUtils.loadPrivateKey(URLUtil.getStream(URLUtil.url(privateKey)))
                    )
            );
            Validator validator = new WxPayValidator(
                    new AutoUpdateCertificatesVerifier(
                            credentials, apiV3Key.getBytes()
                    )
            );
            CloseableHttpClient client = WxPayV3HttpClientBuilder.create()
                    .withCredentials(credentials)
                    .withValidator(validator)
                    .build();
            wxPayConfig.setApiV3HttpClient(client);
        } else {
            //  自动构建apiV3HttpClient时以下参数必须设置
            //  com.github.binarywang.wxpay.config.WxPayConfig.initApiV3HttpClient
            wxPayConfig.setMchId(mchId);
            wxPayConfig.setPrivateKeyPath(privateKey);
            wxPayConfig.setPrivateCertPath(privateCert);
            wxPayConfig.setApiV3Key(apiV3Key);
        }
        return wxPayConfig;
    }

    /**
     * <p>
     * 根据私钥资源形式获取私钥
     * url：则以url方式获取
     * 文件：则以读文件方式获取
     * </p>
     *
     * @param privateKey
     * @param isUrl
     * @return java.security.PrivateKey
     * @author guohaibin
     * @date 2020-12-02 13:54:30
     */
    private PrivateKey getPrivateKey(String privateKey, boolean isUrl) {
        return isUrl ? PemUtils.loadPrivateKey(URLUtil.getStream(URLUtil.url(privateKey))) :
                PemUtils.loadPrivateKey(FileUtil.getInputStream(privateKey));
    }

}
