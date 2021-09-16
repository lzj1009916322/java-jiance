package com.weservice.catering.wtakeout.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weservice.catering.wtakeout.modules.app.entity.IsBoughtDetailEntity;
import com.weservice.catering.wtakeout.modules.app.entity.OrderInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * ${comments}
 *
 * @author
 * @email
 * @date 2021-02-01 23:48:00
 */
@Mapper
public interface OrderInfoDao extends BaseMapper<OrderInfoEntity> {
    IPage<OrderInfoEntity> queryByPage(Page<?> page, @Param("req") OrderInfoEntity orderInfoEntity);

    int update(OrderInfoEntity orderInfoEntity);

    List<OrderInfoEntity> selectUnfinished();

    List<OrderInfoEntity> queryByCondition();

    IsBoughtDetailEntity isBought(@Param("productId") Integer productId, @Param("userId") String userId);

    OrderInfoEntity queryById(@Param("orderId") String orderId);
}
