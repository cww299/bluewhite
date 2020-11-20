package com.bluewhite.production.work.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.production.work.entity.TaskAllocation;
import com.bluewhite.production.work.entity.TaskProcess;

/**
 * @author cww299
 * @date 2020/11/17
 */
@Service
public interface TaskProcessService extends BaseCRUDService<TaskProcess, Long> {

	/**
	 * 根据任务id，任务分配id 获取任务进程
	 * @param taskId 任务id
	 * @param taskAllocationId 分配任务id
	 * @return
	 */
	List<TaskProcess> getAll(Long taskId, Long taskAllocationId);

	/**
	 * 删除分配任务的相关进度
	 * @param list
	 * @return
	 */
	int deleteAllocation(List<TaskAllocation> list);

}
