package com.weservice.catering.wtakeout.modules.app.constant;

public enum PointEnum {
    REWARD("reward", "奖励"),

    WITH_DRAW("withdraw", "提现");

    private String type;
    private String desc;

    private PointEnum(String type, String desc) {
        this.type = type;
        this.desc = desc;
    }

    public String getType() {
        return type;
    }
}
