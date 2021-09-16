package com.weservice.catering.wtakeout.modules.app.entity;

import com.weservice.catering.wtakeout.modules.sys.entity.SysUserEntity;
import lombok.Data;

@Data
public class CommonUserInfo {
    private Long userId;
    private String appUserId;
    private String tel;
    private boolean isMember = false;

    public static CommonUserInfo transfer2CommonUserInfo(AppUserInfoEntity appUserInfoEntity) {
        CommonUserInfo commonUserInfo = new CommonUserInfo();
        commonUserInfo.setAppUserId(appUserInfoEntity.getUserId());
        commonUserInfo.setTel(appUserInfoEntity.getPhone());
        if ("1".equals(appUserInfoEntity.getMember())) {
            commonUserInfo.setMember(true);
        }
        return commonUserInfo;
    }

    public static CommonUserInfo transfer2CommonUserInfo(SysUserEntity sysUserEntity) {
        CommonUserInfo commonUserInfo = new CommonUserInfo();
        commonUserInfo.setUserId(sysUserEntity.getUserId());
        commonUserInfo.setTel(sysUserEntity.getMobile());
        return commonUserInfo;
    }
}
