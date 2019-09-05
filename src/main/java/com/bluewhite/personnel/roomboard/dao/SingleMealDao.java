package com.bluewhite.personnel.roomboard.dao;

import java.util.Date;
import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.personnel.roomboard.entity.SingleMeal;

public interface SingleMealDao extends BaseRepository<SingleMeal, Long>{
	public List<SingleMeal> findByTimeBetween(Date orderTimeBegin, Date orderTimeEnd);
	public List<SingleMeal> findByTimeBetweenAndOrgNameId(Date orderTimeBegin, Date orderTimeEnd,Long orgNameId);
}
