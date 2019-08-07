package com.bluewhite.personnel.roomboard.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.personnel.roomboard.entity.Meal;

public interface MealDao extends BaseRepository<Meal, Long>{
	
	/**
	 * 按吃饭时间 添加方式
	 * @return
	 */
	public List<Meal> findByTypeAndTradeDaysTimeBetween(Integer type,Date orderTimeBegin, Date orderTimeEnd);
	
	/**
	 * 按吃饭时间区间
	 * @return
	 */
	public List<Meal> findByTradeDaysTimeBetween(Date orderTimeBegin, Date orderTimeEnd);
	@Modifying
	@Query(nativeQuery= true,value ="delete from person_application_meal where id in (?1)")
	public void deleteList(List<Long> ids);
	

}
