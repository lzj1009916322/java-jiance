package com.weservice.catering.wtakeout.modules.app.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.weservice.catering.wtakeout.modules.app.entity.ProductInfoEntity;
import com.weservice.catering.wtakeout.modules.app.entity.req.ProductInfoReq;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

/**
 * ${comments}
 *
 * @author
 * @email
 * @date 2021-02-01 23:48:00
 */
@Mapper
public interface ProductInfoDao extends BaseMapper<ProductInfoEntity> {
    IPage<ProductInfoEntity> getListByLocation(Page<?> page, @Param("req") ProductInfoEntity req);

    ProductInfoEntity getProductById(@Param("productId") Integer productId, @NotNull @Param("latitude") Float latitude, @NotNull @Param("longitude") Float longitude);

    int update(ProductInfoEntity productInfoEntity);

    int modifyTotalLeft(@Param("deltaTotalLeft") Integer i, @Param("productId") Integer productId);

    int modifySaleFlag(@Param("list") List<Integer> list, @Param("saleFlag") boolean saleFlag);
}
