package com.weservice.catering.wtakeout.modules.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weservice.catering.wtakeout.modules.app.dao.MemberFlowDao;
import com.weservice.catering.wtakeout.modules.app.entity.MemberFlowEntity;
import com.weservice.catering.wtakeout.modules.app.service.MemberFlowService;
import org.springframework.stereotype.Service;

@Service("memberFlowService")
public class MemberFlowServiceImpl extends ServiceImpl<MemberFlowDao, MemberFlowEntity> implements MemberFlowService {
}
