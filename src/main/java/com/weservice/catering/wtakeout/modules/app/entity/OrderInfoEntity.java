package com.weservice.catering.wtakeout.modules.app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.weservice.catering.wtakeout.modules.app.constant.OrderEnum;
import com.weservice.catering.wtakeout.modules.app.dao.PicInfoDao;
import com.weservice.catering.wtakeout.modules.app.service.PicInfoService;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.weservice.catering.wtakeout.modules.app.constant.OrderEnum.*;

/**
 * ${comments}
 *
 * @author
 * @email
 * @date 2021-02-01 23:48:00
 */
@Data
@TableName("order_info")
@ApiModel("订单信息")
public class OrderInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 订单号
     */
    @TableId(type = IdType.ASSIGN_UUID)
    @ApiModelProperty
    private String orderId;
    /**
     * 用户id
     */
    @ApiModelProperty
    private String userId;
    /**
     * 商品id
     */
    @ApiModelProperty
    private Integer productId;
    @ApiModelProperty
    private String productName;
    @ApiModelProperty("产品图片")
    private String productPicUrl;
    @ApiModelProperty("购买截图")
    @TableField(exist = false)
    private List<PicInfoEntity> buyPics;
    @ApiModelProperty("好评截图")
    @TableField(exist = false)
    private List<PicInfoEntity> praisePics;
    /**
     * 是否有效（y有效，n无效）
     */
    @ApiModelProperty("doing进行中，finish已完成")
    private String state;
    @ApiModelProperty("状态: buy 已购买，待上传购买截图->praise 已上传购买截图，待好评截图-> audit待后台管理员审核->success 成功->fail 失败")
    private String status;
    /**
     * 失败原因
     */
    @ApiModelProperty("失败原因")
    private String failReason;
    @ApiModelProperty("返还积分")
    private BigDecimal returnPoint;
    @ApiModelProperty("外卖平台")
    private String productType;
    @ApiModelProperty("花费金额")
    private BigDecimal cost;
    @ApiModelProperty("外卖订单")
    private String takeoutOrder;
    @ApiModelProperty("过期时间")
    private Date timeoutTime;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;


    public void setStateAndStatus(OrderEnum state, OrderEnum status) {
        this.setState(state.getStatus());
        this.setStatus(status.getStatus());
    }

    public void cancel() {
        this.setState(FINISH.getStatus());
        this.setStatus(CANCEL.getStatus());
    }

    public void checkStatus() {
        if (SUCCESS.getStatus().equals(this.getStatus()) || AUDIT_FAIL.getStatus().equals(this.getStatus())) {
            this.setState(FINISH.getStatus());
        }
    }

    /**
     * 生成时间
     */
    public void setCreateTimeAndTimeoutTime() {
        Date date = new Date();
        this.createTime = date;
        this.timeoutTime = new Date(date.getTime() + 60000 * 30);
    }
}
