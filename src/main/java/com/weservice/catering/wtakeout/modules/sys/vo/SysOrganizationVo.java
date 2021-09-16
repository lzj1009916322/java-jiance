package com.weservice.catering.wtakeout.modules.sys.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 *
 *
 * @author
 * @email
 * @date 2020-09-19 16:31:42
 */
@Data
public class SysOrganizationVo {
    /**
     * 组织编码
     */
    @TableId
    private String code;
    /**
     * 上层组织编码
     */
    private String superCode;
    /**
     * 上层组织编码
     */
    private String superName;
    /**
     * 名称
     */
    private String name;
    /**
     * 层级
     */
    private Integer level;
    /**
     * 状态：0正常，1禁用，9删除
     */
    private Integer status;
}
