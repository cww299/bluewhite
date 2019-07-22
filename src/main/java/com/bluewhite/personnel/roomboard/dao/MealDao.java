package com.bluewhite.personnel.roomboard.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.personnel.roomboard.entity.Meal;

public interface MealDao extends BaseRepository<Meal, Long>{
	
	/**
	 * 按签到时间区间和员工id查询
	 * @param userId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List<Meal> findByTypeAndTradeDaysTimeBetween(Integer type,Date orderTimeBegin, Date orderTimeEnd);
	
	@Modifying
	@Query(nativeQuery= true,value ="delete from person_application_meal where id in (?1)")
	public void deleteList(List<Long> ids);
	

}
