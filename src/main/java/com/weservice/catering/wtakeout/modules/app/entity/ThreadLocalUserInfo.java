package com.weservice.catering.wtakeout.modules.app.entity;

import com.weservice.catering.wtakeout.modules.sys.entity.SysUserEntity;
import org.apache.commons.lang.StringUtils;

public class ThreadLocalUserInfo {
    private static ThreadLocal<CommonUserInfo> userInfo = new ThreadLocal<>();

    public static void setUserInfo(CommonUserInfo user) {
        userInfo.set(user);
    }

    public static CommonUserInfo getUserInfo() {
        return userInfo.get();
    }

    public static void remove() {
        userInfo.remove();
    }

    public static boolean isAdmin() {
        if (null != userInfo && null != userInfo.get() && null != userInfo.get().getUserId()) {
            return true;
        }
        return false;
    }
}
