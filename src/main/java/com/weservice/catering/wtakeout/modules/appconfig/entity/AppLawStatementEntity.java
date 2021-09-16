package com.weservice.catering.wtakeout.modules.appconfig.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * ${comments}
 *
 * @author
 * @email
 * @date 2021-03-26 09:51:11
 */
@Data
@TableName("app_law_statement")
public class AppLawStatementEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Integer id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;
	/**
	 * 更新时间
	 */
	private LocalDateTime updateTime;
	/**
	 * 状态：1、生效 2、失败
	 */
	private Integer status;

}
