package com.bluewhite.finance.consumption.service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.finance.consumption.entity.Consumption;

public interface ConsumptionService  extends BaseCRUDService<Consumption,Long>{
	
	/**
	 * 分页查看
	 * @param expenseAccount
	 * @param page
	 * @return
	 */
	PageResult<Consumption> findPages(Consumption consumption, PageParameter page);
	
	/**
	 * 新增
	 * @param expenseAccount
	 * @return
	 */
	public Consumption addConsumption(Consumption consumption);
	
	/**
	 * 删除
	 * @param expenseAccount
	 * @return
	 */
	public int deleteConsumption(String ids);
	
	/**
	 * 修改
	 * @param expenseAccount
	 * @return
	 */
	public Consumption updateConsumption(Consumption consumption);
	
	/**
	 * 财务审核放款
	 * @param expenseAccount
	 * @return
	 */
	public Consumption auditConsumption(Consumption consumption);

}
