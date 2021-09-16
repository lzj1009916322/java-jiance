package com.weservice.catering.wtakeout.modules.app.entity.req;

import com.baomidou.mybatisplus.annotation.TableId;
import com.weservice.catering.wtakeout.modules.app.entity.OrderInfoEntity;
import com.weservice.catering.wtakeout.modules.app.entity.PicInfoEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.aspectj.weaver.ast.Or;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@ApiModel("订单提交")
@Data
public class OrderSubmitEntity {
    /**
     * 订单号
     */
    @TableId
    @ApiModelProperty("订单ID")
    @NotNull
    private String orderId;
    @ApiModelProperty("图片列表")
    @Size(min = 1, max = 4)
    private List<String> images;
    @ApiModelProperty("订单")
    private String takeoutOrder;
    @ApiModelProperty("实付金额")
    private BigDecimal cost;

    public OrderInfoEntity transfer2OrderInfoEntity(OrderInfoEntity orderInfoEntity) {
        orderInfoEntity.setOrderId(orderId);
        orderInfoEntity.setTakeoutOrder(takeoutOrder);
        orderInfoEntity.setCost(cost);
        orderInfoEntity.setUpdateTime(new Date());
        return orderInfoEntity;
    }

    public List<PicInfoEntity> transfer2PicInfoEntity(String type) {
        return images.stream().map(x -> {
            PicInfoEntity picInfoEntity = new PicInfoEntity();
            picInfoEntity.setPicUrl(x);
            picInfoEntity.setCreateTime(new Date());
            picInfoEntity.setUpdateTime(new Date());
            picInfoEntity.setBizId(String.valueOf(orderId));
            picInfoEntity.setType(type);
            return picInfoEntity;
        }).collect(Collectors.toList());
    }
}
