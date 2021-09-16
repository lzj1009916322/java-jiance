package com.weservice.catering.wtakeout.modules.app.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weservice.catering.wtakeout.modules.app.entity.OrderInfoEntity;
import com.weservice.catering.wtakeout.modules.app.entity.MerchBillStatisticsEntity;
import com.weservice.catering.wtakeout.modules.app.entity.req.MerchBillReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface ReportDao {
    IPage<MerchBillStatisticsEntity> getMerchBillStatistics(Page<MerchBillStatisticsEntity> page, @Param("req") MerchBillReq req);

    List<OrderInfoEntity> getOrderList(MerchBillReq merchBillReq);

}
