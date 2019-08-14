package com.bluewhite.personnel.roomboard.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.personnel.attendance.entity.AttendanceTime;
import com.bluewhite.personnel.attendance.entity.PersonVariable;
import com.bluewhite.personnel.roomboard.entity.Meal;

public interface MealService  extends BaseCRUDService<Meal,Long>{
	
	/**
	 * 按条件查看报餐记录
	 * @param attendanceTime
	 * @return
	 */
	public PageResult<Meal> findPage(Meal meal, PageParameter page);
	
	
	/**
	 * 按条件查看报餐记录(去除分页)
	 * @param attendanceTime
	 * @return
	 */
	public List<Meal> findMeal(Meal meal);
	/**
	 * 新增报餐
	 * @param onlineOrder
	 */
	public Meal addMeal(Meal meal);
	
	public	PersonVariable findByType(Integer type);
	
	/**
	 * 修改字典表中的报餐价格
	 * @param onlineOrder
	 */
	public PersonVariable updateperson(PersonVariable personVariable);
	
	/**
	 * 报餐汇总
	 * @param userId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List<Map<String, Object>> findMealSummary(Meal meal);
	
	/**
	 * 同步吃饭
	 * @param onlineOrder
	 */
	public int InitMeal(AttendanceTime attendanceTime) throws ParseException;
	
}
