package com.bluewhite.production.farragotask.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.production.farragotask.entity.FarragoTask;

@Service
public interface FarragoTaskService extends BaseCRUDService<FarragoTask,Long>{

	public PageResult<FarragoTask> findPages(FarragoTask farragoTask, PageParameter page);
	/**
	 * 新增杂工任务
	 * @param farragoTask
	 * @return
	 */
	public FarragoTask addFarragoTask(FarragoTask farragoTask);
	
	/**
	 * 修改杂工任务
	 * @param farragoTask
	 * @return
	 */
	public FarragoTask updateFarragoTask(FarragoTask farragoTask);
	
	/**
	 * 删除杂工任务
	 * @param id
	 */
	public void deleteFarragoTask(Long id);
	/**
	 * 根据类型和时间查找
	 * @param type
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<FarragoTask> findByTypeAndAllotTimeBetween(Integer type,Date startTime,Date endTime);

}
