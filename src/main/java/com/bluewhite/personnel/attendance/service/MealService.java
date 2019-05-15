package com.bluewhite.personnel.attendance.service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.personnel.attendance.entity.Meal;

public interface MealService  extends BaseCRUDService<Meal,Long>{
	
	/**
	 * 按条件查看报餐记录
	 * @param attendanceTime
	 * @return
	 */
	public PageResult<Meal> findPage(Meal meal, PageParameter page);
	
	/**
	 * 新增报餐
	 * @param onlineOrder
	 */
	public Meal addMeal(Meal meal);
}
