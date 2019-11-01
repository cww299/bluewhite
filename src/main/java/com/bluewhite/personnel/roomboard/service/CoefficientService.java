package com.bluewhite.personnel.roomboard.service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.personnel.roomboard.entity.coefficient;

public interface CoefficientService  extends BaseCRUDService<coefficient,Long>{
	
	/**
	 * 按条件查看
	 * @param 
	 * @return
	 */
	public PageResult<coefficient> findPage(coefficient coefficient, PageParameter page);
	
	/**
	 * 新增
	 * @param 
	 */
	public coefficient addCoefficient(coefficient coefficient);
	
	/**
	 * 删除
	 * @param 
	 */
	public int deletes(String[] ids);
	
}
