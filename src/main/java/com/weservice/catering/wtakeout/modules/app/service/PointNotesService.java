package com.weservice.catering.wtakeout.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weservice.catering.wtakeout.common.utils.PageUtils;
import com.weservice.catering.wtakeout.modules.app.entity.OrderInfoEntity;
import com.weservice.catering.wtakeout.modules.app.entity.PointNotesEntity;

import java.util.Map;

/**
 * ${comments}
 *
 * @author
 * @email
 * @date 2021-02-01 23:48:00
 */
public interface PointNotesService extends IService<PointNotesEntity> {

    PageUtils<PointNotesEntity> queryPage(Map<String, Object> params);

    void updatePointNotes(PointNotesEntity pointNotes);

    void createPointNotesByOrder(OrderInfoEntity orderInfoEntity, String userId);
}

