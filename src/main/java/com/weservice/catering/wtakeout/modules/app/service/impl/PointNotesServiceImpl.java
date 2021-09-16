package com.weservice.catering.wtakeout.modules.app.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.weservice.catering.wtakeout.common.utils.PageUtils;
import com.weservice.catering.wtakeout.common.utils.Query;
import com.weservice.catering.wtakeout.modules.app.dao.PointNotesDao;
import com.weservice.catering.wtakeout.modules.app.entity.OrderInfoEntity;
import com.weservice.catering.wtakeout.modules.app.entity.PointNotesEntity;
import com.weservice.catering.wtakeout.modules.app.service.PointNotesService;
import com.weservice.catering.wtakeout.modules.app.utils.QueryEntityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

import static com.weservice.catering.wtakeout.modules.app.constant.OrderEnum.FINISH;
import static com.weservice.catering.wtakeout.modules.app.constant.PointEnum.REWARD;


@Service("pointNotesService")
public class PointNotesServiceImpl extends ServiceImpl<PointNotesDao, PointNotesEntity> implements PointNotesService {
    @Autowired
    private PointNotesDao pointNotesDao;

    @Override
    public PageUtils<PointNotesEntity> queryPage(Map<String, Object> params) {
        IPage<PointNotesEntity> page = this.page(
                new Query<PointNotesEntity>().getPage(params),
                new QueryWrapper<>(QueryEntityUtil.generateQueryEntity(params, PointNotesEntity.class)).orderByDesc("create_time")
        );

        return new PageUtils<PointNotesEntity>(page);
    }

    @Override
    public void updatePointNotes(PointNotesEntity pointNotes) {
        pointNotesDao.update(pointNotes);
    }

    @Override
    public void createPointNotesByOrder(OrderInfoEntity orderInfoEntity, String userId) {
        PointNotesEntity pointNotesEntity = new PointNotesEntity();
        pointNotesEntity.setPoint(orderInfoEntity.getReturnPoint());
        pointNotesEntity.setStatus("finish");
        pointNotesEntity.setType(REWARD.getType());
//        pointNotesEntity.setUserId(orderInfoEntity.getUserId());
        pointNotesEntity.setCreateTime(new Date());
        pointNotesEntity.setUpdateTime(new Date());
        pointNotesEntity.setUserId(orderInfoEntity.getUserId());
        this.save(pointNotesEntity);
    }

}