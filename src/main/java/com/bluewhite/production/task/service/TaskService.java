package com.bluewhite.production.task.service;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
	public Task addTask(Task task,HttpServletRequest request);
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
	 * 二楼机工添加返工任务
	 * @param task
	 */
	public Task addReTask(Task task);
	
	
	/**
	 * 删除返工
	 * @param ids
	 */
	public void deleteReTask(String ids);
	
	/**
	 * 加绩
	 * @param taskIds
	 * @param ids
	 * @param performance
	 * @param performanceNumber
	 * @param update
	 */
	public void giveTaskPerformance(String[] taskIds, String[] ids, String[] performance, Double[] performanceNumber,
			Integer update);
	
	/**
	 * 获取该员工当天做过的所有任务
	 * @param valueOf
	 * @param getfristDayOftime
	 * @param lastDayOftime
	 * @return
	 */
	public List<Task> findByUserIdAndAllotTime(String userid, Date beginTime, Date endTime);
	
	/**
	 * 根据类型和时间查找
	 * @param type
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<Task> findByTypeAndAllotTimeBetween(Integer type,Date startTime,Date endTime);
	

}
