package com.weservice.catering.wtakeout.modules.app.constant;

import com.weservice.catering.wtakeout.common.utils.R;

import java.util.concurrent.locks.ReentrantLock;

public class LockConstant {
    public static ReentrantLock pointLock = new ReentrantLock();
    public static ReentrantLock productLock = new ReentrantLock();
}
