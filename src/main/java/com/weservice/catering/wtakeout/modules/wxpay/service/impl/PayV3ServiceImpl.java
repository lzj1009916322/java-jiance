package com.weservice.catering.wtakeout.modules.wxpay.service.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.v3.util.AesUtils;
import com.weservice.catering.wtakeout.modules.app.dao.PayInfoDao;
import com.weservice.catering.wtakeout.modules.app.entity.PayInfoEntity;
import com.weservice.catering.wtakeout.modules.app.service.PayNotifyService;
import com.weservice.catering.wtakeout.modules.wxpay.config.WxConfig;
import com.weservice.catering.wtakeout.modules.wxpay.entity.PayV3Notify;
import com.weservice.catering.wtakeout.modules.wxpay.entity.PayV3NotifyDecrypt;
import com.weservice.catering.wtakeout.modules.wxpay.service.PayV3Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author: guohaibin
 * @createDate: 2020/12/2
 */
@Slf4j
@Service
public class PayV3ServiceImpl implements PayV3Service {

    private final WxConfig wxConfig;

    public PayV3ServiceImpl(WxConfig wxConfig) {
        this.wxConfig = wxConfig;
    }

    @Autowired
    private List<PayNotifyService> notifyServices = new ArrayList<>();
    @Autowired
    private PayInfoDao payInfoDao;

    /**
     * 代小程序开发无法使用v3，因为返回的数据无法找到对应的小程序，则无法关联找到api v3 key
     * 只能单个小程序实现
     * 亦或大家都绑定我们的商户，感觉也不太可能
     */
    @Override
    public String payNotify(PayV3Notify payV3Notify) {
        try {
            //  解密
            log.info("payV3Notify:{}", JSON.toJSONString(payV3Notify));
            String decryptToString = AesUtils.decryptToString(payV3Notify.getResource().getAssociated_data(),
                    payV3Notify.getResource().getNonce(), payV3Notify.getResource().getCiphertext(),
                    wxConfig.getApiV3Key());
            PayV3NotifyDecrypt payV3NotifyDecrypt = JSONUtil.toBean(decryptToString, PayV3NotifyDecrypt.class);
            //  订单号
            String outTradeNo = payV3NotifyDecrypt.getOut_trade_no();
            //  判断订单状态
            log.info("payV3NotifyDecrypt:{}", JSON.toJSONString(payV3NotifyDecrypt));
            //  修改订单状态
            PayInfoEntity payInfoEntity = new PayInfoEntity();
            payInfoEntity.setId(outTradeNo);
            payInfoEntity.setFlag("paid");
            payInfoDao.update(payInfoEntity);
            PayInfoEntity byId = payInfoDao.selectById(outTradeNo);
            notifyServices.parallelStream().forEach(x->x.wxNotify(byId));

            return WxPayNotifyResponse.success("处理成功!");
        } catch (Exception e) {
            log.error("微信v3支付回调异常,异常原因:{},参数为:{}", e.getMessage(), payV3Notify);
            return WxPayNotifyResponse.fail(e.getMessage());
        }
    }
}
