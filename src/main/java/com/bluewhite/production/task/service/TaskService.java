package com.bluewhite.production.task.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.production.task.entity.Task;
@Service
public interface TaskService extends BaseCRUDService<Task,Long>{
	
	/**
	 * 新增任务
	 * 1,同时计算出 预计完成时间 ， 任务价值(预计成本费用)，b工资净值 入库
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
	
	/**
	 * 删除任务，同时删除B工资，更新批次的数值
	 * @param id
	 */
	public void deleteTask(String ids);
	
	/**
	 * 根据时间占比，组装出新任务
	 * @param task
	 */
	public List<Task> assembleTask(Task task);
	
	/**
	 * 批量修改任务
	 * @param ids
	 */
	public int updateTask (String ids) throws Exception;
	
	/**
	 * 暂停开始实时任务时间
	 * @param id
	 */
	public void getTaskActualTime(Long id,Integer status)  throws Exception;
	
	/**
	 * 修改任务
	 * @param task
	 * @return
	 */
	public Task upTask(Task task);
	
	/**
	 * 根据实际时间的到任务数量
	 * @param task
	 */
	public Integer getTaskNumber(Task task);
	
	/**
	 * 新增返工任务
	 * @param task
	 * @return
	 */
	public Task addRework(Task task);

}
