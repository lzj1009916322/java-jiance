package com.weservice.catering.wtakeout.modules.app.utils;

import com.qiniu.util.StringUtils;
import com.weservice.catering.wtakeout.modules.app.entity.ProductInfoEntity;
import com.weservice.catering.wtakeout.modules.app.entity.ThreadLocalUserInfo;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

@Slf4j
public class UrlUtil {
    private static final String EL = "el";
    private static final String MT = "mt";
    private static final String MT_SPLIT = "/poi/";
    private static final String EL_SPLIT_ID = "id=";
    private static final String EL_SPLIT_AND = "&";

    public static void main(String[] args) throws InterruptedException {
IdempotencyUtil.set("123");
Thread.sleep(4000);
IdempotencyUtil.set("123");
        BigDecimal bigDecimal = new BigDecimal(10);
        BigDecimal bigDecimal1 = new BigDecimal(11);
        int i = bigDecimal.subtract(bigDecimal1).compareTo(new BigDecimal(0));
        System.out.printf(String.valueOf(i));

    }

    public static ProductInfoEntity transferUrl(ProductInfoEntity productInfoEntity) {
        if (ThreadLocalUserInfo.isAdmin()) {
            return productInfoEntity;
        }
        if (StringUtils.isNullOrEmpty(productInfoEntity.getUrl())) {
            return productInfoEntity;
        }
        try {
            if (MT.equals(productInfoEntity.getProductType()) && productInfoEntity.getUrl().contains("?")) {
                String url = productInfoEntity.getUrl();
                String[] split = url.split(MT_SPLIT);
                String[] split1 = split[1].split("\\?");
                productInfoEntity.setUrl(split1[0]);
            }
            if (EL.equals(productInfoEntity.getProductType()) && productInfoEntity.getUrl().contains(EL_SPLIT_ID) && productInfoEntity.getUrl().contains(EL_SPLIT_AND)) {
                String url = productInfoEntity.getUrl();
                String[] split = url.split(EL_SPLIT_ID);
                String s1 = split[1].split(EL_SPLIT_AND)[0];
                productInfoEntity.setUrl(s1);
            }
            return productInfoEntity;
        } catch (Exception e) {
            log.error("translate error:{}:", productInfoEntity.getUrl(), e);
            return productInfoEntity;
        }
    }
}
