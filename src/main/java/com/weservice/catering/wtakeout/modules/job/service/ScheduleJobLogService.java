/**
 *
 *
 */

package com.weservice.catering.wtakeout.modules.job.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.weservice.catering.wtakeout.common.utils.PageUtils;
import com.weservice.catering.wtakeout.modules.job.entity.ScheduleJobLogEntity;

import java.util.Map;

/**
 * 定时任务日志
 *
 *
 */
public interface ScheduleJobLogService extends IService<ScheduleJobLogEntity> {

	PageUtils queryPage(Map<String, Object> params);
	
}
