package com.weservice.catering.wtakeout.modules.app.constant;

public enum LocationStatisticsEnum {
    PRODUCT_LIST("productList", "查看商品列表"),
    PRODUCT_DETAIL("productDetail", "查看商品详情");
    private String locationType;
    private String desc;

    private LocationStatisticsEnum(String locationType, String desc) {
        this.locationType = locationType;
        this.desc = desc;
    }

    public String getType() {
        return locationType;
    }
}
