package com.weservice.catering.wtakeout.modules.app.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * ${comments}
 *
 * @author
 * @email
 * @date 2021-02-06 21:09:46
 */
@Data
@TableName("pic_info")
public class PicInfoEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId
    private Integer picId;
    /**
     * 图片url
     */
    private String picUrl;
    private String bizId;
    private String type;
    /**
     * $column.comments
     */
    private Date createTime;
    /**
     * $column.comments
     */
    private Date updateTime;

}
