package com.weservice.catering.wtakeout.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 *
 *
 * @author
 * @email
 * @date 2020-09-14 18:31:42
 */
@Data
@TableName("sys_organization")
public class SysOrganizationEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 组织编码
	 */
	@TableId(type = IdType.ASSIGN_UUID)
	private String code;
	/**
	 * 上层组织编码
	 */
	private String superCode;
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
