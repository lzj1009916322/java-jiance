package com.weservice.catering.wtakeout.modules.app.service;

import com.weservice.catering.wtakeout.modules.app.entity.PayInfoEntity;

public interface PayNotifyService {
    public void wxNotify(PayInfoEntity byId);
}
