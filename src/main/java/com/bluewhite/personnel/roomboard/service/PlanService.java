package com.bluewhite.personnel.roomboard.service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.personnel.roomboard.entity.Plan;

public interface PlanService  extends BaseCRUDService<Plan,Long>{
	
	/**
	 * 按条件查看
	 * @param 
	 * @return
	 */
	public PageResult<Plan> findPage(Plan plan, PageParameter page);
	
	/**
	 * 新增
	 * @param 
	 */
	public Plan addPlan(Plan plan);
	
	/**
	 * 删除
	 * @param 
	 */
	public int deletes(String[] ids);
	
}
