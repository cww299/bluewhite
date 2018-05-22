package com.bluewhite.production.task.service;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.production.task.entity.Task;
@Service
public interface TaskService extends BaseCRUDService<Task,Long>{
	
	/**
	 * 新增任务
	 * @param task
	 * @return
	 */
	public Task addTask(Task task);
	/**
	 * 分页查询所有任务
	 * @param task
	 * @param page
	 * @return
	 */
	public PageResult<Task>  findPages(Task task, PageParameter page);

}
