package com.bluewhite.production.bacth.dao;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.production.bacth.entity.Bacth;
import java.lang.Integer;
import java.util.Date;
import java.util.List;

public interface BacthDao extends BaseRepository<Bacth, Long>{
	
	/**
	 * 根据类型和时间查找
	 * @param type
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<Bacth> findByTypeAndAllotTimeBetween(Integer type,Date startTime,Date endTime);

}
