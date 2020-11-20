package com.bluewhite.production.work.service;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.production.work.entity.TaskAllocation;

/**
 * @author cww299
 * @date 2020/11/17
 */
@Service
public interface TaskAllocationService extends BaseCRUDService<TaskAllocation, Long> {

	/**
	 * 分页查询任务分配列表
	 * @param allocation
	 * @param page
	 * @return
	 */
	public PageResult<TaskAllocation> findPages(TaskAllocation allocation, PageParameter page);

	/**
	 * 保存任务分配
	 * @param allocation
	 */
	public void saveTaskAllocation(TaskAllocation allocation);

	/**
	 * 删除任务分配
	 * @param ids
	 * @return
	 */
	public int deleteTaskAllocation(String ids);

	/**
	 * 开始或暂停任务
	 * @param ids
	 * @param remark
	 * @return
	 */
	public int startOrPause(String ids, String remark, int status);

	/**
	 * 退回任务数量
	 * @param allocationId
	 * @param number
	 * @param remark
	 */
	public void returns(Long allocationId, int number, String remark);

	/**
	 * 完成任务数量
	 * @param allocationId
	 * @param number
	 */
	public void finish(Long allocationId, int number);

	/**
	 * 一键完成任务
	 * @param ids
	 * @return
	 */
	public int finishs(String ids);

}
