package com.weservice.catering.wtakeout.modules.sys.constants;

/**
 * @author
 * @date 2020/09/19 18:40
 */
public enum OrganizationStatusEnum {
    AVAILABLE(0),
    DISABLED(1),
    DELETED(9);
    private Integer value;

    private OrganizationStatusEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }}

