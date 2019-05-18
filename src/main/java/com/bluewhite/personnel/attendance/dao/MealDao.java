package com.bluewhite.personnel.attendance.dao;

import java.util.Date;
import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.personnel.attendance.entity.Meal;

public interface MealDao extends BaseRepository<Meal, Long>{
	
	/**
	 * 按签到时间区间和员工id查询
	 * @param userId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List<Meal> findByUserIdAndOrgNameIdAndTradeDaysTimeBetween(Long userId,Long orgNameId,Date orderTimeBegin, Date orderTimeEnd);
	

}
