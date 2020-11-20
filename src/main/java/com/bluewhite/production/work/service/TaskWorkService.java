package com.bluewhite.production.work.service;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.production.work.entity.TaskWork;

/**
 * @author cww299
 * @date 2020/11/17
 */
@Service
public interface TaskWorkService extends BaseCRUDService<TaskWork, Long> {

	/**
	 * 分页查找任务列表
	 * @param task
	 * @param page
	 * @return
	 */
	public PageResult<TaskWork> findPages(TaskWork task, PageParameter page);

	/**
	 * 保存任务
	 * @param task
	 */
	public void saveTask(TaskWork task);

	/**
	 * 删除任务
	 * @param ids
	 * @return
	 */
	public int deleteTask(String ids);

}
