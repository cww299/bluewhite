package com.bluewhite.finance.wage.service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.finance.wage.entity.Wage;

public interface WageService  extends BaseCRUDService<Wage, Long> {
	/**
	 * 分页查看
	 * 
	 * @param expenseAccount
	 * @param page
	 * @return
	 */
	PageResult<Wage> findPages(Wage wage, PageParameter page);
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	public int deleteWage(String[] ids);
	

	/**
	 * 新增or修改
	 * 
	 * @param expenseAccount
	 * @return
	 */
	public Wage addWage(Wage wage);
}
