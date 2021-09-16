package com.weservice.catering.wtakeout.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * ${comments}
 *
 * @author
 * @email
 * @date 2021-02-01 23:48:01
 */
@Data
@TableName("merch_info")
public class MerchInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * $column.comments
     */
    @TableId
    private Integer merchId;
    /**
     * 商户名称
     */
    private String merchName;
    private Float longitude;
    private Float latitude;
    /**
     * 商户地址
     */
    private String address;

    /**
     * 商户电话
     */
    private String phone;
    /**
     * $column.comments
     */
    private Date createTime;
    /**
     * $column.comments
     */
    private Date updateTime;

}
