package com.weservice.catering.wtakeout.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;

/**
 *
 *
 * @author
 * @email
 * @date 2020-09-19 19:57:56
 */
@Data
@TableName("sys_organization_code")
public class OrganizationCodeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Integer id;
	/**
	 * 组织层级
	 */
	private Integer level;
	/**
	 * 当前code值
	 */
	private Integer code;

}
