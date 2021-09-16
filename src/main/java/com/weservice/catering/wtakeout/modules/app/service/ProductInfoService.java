package com.weservice.catering.wtakeout.modules.app.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weservice.catering.wtakeout.common.utils.PageUtils;
import com.weservice.catering.wtakeout.modules.app.entity.IsBoughtDetailEntity;
import com.weservice.catering.wtakeout.modules.app.entity.ProductInfoEntity;

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
public interface ProductInfoService extends IService<ProductInfoEntity> {

    PageUtils<ProductInfoEntity> queryPage(Map<String, Object> params);

    void buyProduct(List<Integer> asList);

    void updateEntity(ProductInfoEntity productInfo);

    IsBoughtDetailEntity getDetailByToken(Integer productId, @NotNull Float latitude, @NotNull Float longitude);

    void modifyTotalLeft(@NotNull Integer deltaTotalLeft, @NotNull Integer productId);

    void onSale(List<Integer> asList);

    void outSale(List<Integer> asList);
}

