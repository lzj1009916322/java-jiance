package com.weservice.catering.wtakeout.modules.app.constant;

public enum PicEnum {

    BUY("buy", "购买截图"),

    PRAISE_PIC("praise", "好评截图");

    private String type;
    private String decs;

    private PicEnum(String type, String desc) {
        this.type = type;
        this.decs = desc;
    }

    public  String getType() {
        return type;
    }
}
