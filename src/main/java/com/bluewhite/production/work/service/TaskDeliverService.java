package com.bluewhite.production.work.service;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.production.work.entity.TaskDeliver;

@Service
public interface TaskDeliverService extends BaseCRUDService<TaskDeliver, Long> {

	
	/**
	 * 分页查询
	 * @param deliver
	 * @param page
	 * @return
	 */
	public PageResult<TaskDeliver> findPages(TaskDeliver deliver, PageParameter page);
	
	/**
	 * 预警分页查询
	 * @param deliver
	 * @param page
	 * @return
	 */
	public PageResult<TaskDeliver> warnList(TaskDeliver deliver, PageParameter page);

	/**
	 * 保存交付计划
	 * @param deliver
	 */
	public void saveDeliver(TaskDeliver deliver);

}
