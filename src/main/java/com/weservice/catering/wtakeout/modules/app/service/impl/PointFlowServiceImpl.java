package com.weservice.catering.wtakeout.modules.app.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weservice.catering.wtakeout.modules.app.dao.PointFlowDao;
import com.weservice.catering.wtakeout.modules.app.entity.PointFlowEntity;
import com.weservice.catering.wtakeout.modules.app.service.PointFlowService;
import org.springframework.stereotype.Service;

@Service("pointFlowService")
public class PointFlowServiceImpl extends ServiceImpl<PointFlowDao, PointFlowEntity> implements PointFlowService {
}
