package com.bluewhite.production.finance.dao;

import java.util.Date;
import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.production.farragotask.entity.FarragoTask;
import com.bluewhite.production.finance.entity.UsualConsume;

public interface UsualConsumeDao extends BaseRepository<UsualConsume, Long>{
	
	/**
	 * 根据类型和时间查找
	 * @param type
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<UsualConsume> findByTypeAndConsumeDateBetween(Integer type,Date startTime,Date endTime);

}
