package com.bluewhite.personnel.roomboard.dao;

import java.util.Date;
import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.personnel.roomboard.entity.Plan;

public interface PlanDao extends BaseRepository<Plan, Long>{
	/*根据 时间 查询 */
	public List<Plan> findByTimeBetween(Date beginDate, Date endDate);
}
