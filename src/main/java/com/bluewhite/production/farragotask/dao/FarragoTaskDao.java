package com.bluewhite.production.farragotask.dao;

import java.util.Date;
import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.production.farragotask.entity.FarragoTask;

public interface FarragoTaskDao extends BaseRepository<FarragoTask, Long>{
	
	/**
	 * 根据类型和时间查找
	 * @param type
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<FarragoTask> findByTypeAndAllotTimeBetween(Integer type,Date startTime,Date endTime);

}
