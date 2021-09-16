package com.weservice.catering.wtakeout.modules.app.constant;

public enum OrderEnum {

    BUY("buy", "待上传购买截图"),
    PRAISE("praise", "待上好评截图"),
    AUDIT("audit", "待管理员审核"),
    SUCCESS("success", "成功"),
    AUDIT_FAIL("auditFail", "失败"),
    TIMEOUT("timeout", "超时"),
    CANCEL("cancel", "取消"),
    DOING("doing", "进行中"),
    FINISH("finish", "已完成");


    private String status;
    private String decs;

    private OrderEnum(String status, String desc) {
        this.status = status;
        this.decs = desc;
    }

    public String getStatus() {
        return status;
    }
}
