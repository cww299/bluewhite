package com.bluewhite.finance.tax.service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.finance.expenseAccount.entity.ExpenseAccount;
import com.bluewhite.finance.tax.entity.Tax;

public interface TaxService  extends BaseCRUDService<Tax,Long>{
	
	/**
	 * 分页查看税点单
	 * @param expenseAccount
	 * @param page
	 * @return
	 */
	PageResult<Tax> findPages(Tax tax, PageParameter page);
	
	/**
	 * 新增税点单
	 * @param expenseAccount
	 * @return
	 */
	public Tax addTax(Tax tax);
	
	/**
	 * 删除税点单
	 * @param expenseAccount
	 * @return
	 */
	public int deleteTax(String ids);
	
	/**
	 * 修改税点单
	 * @param expenseAccount
	 * @return
	 */
	public Tax updateTax(Tax tax);
	/**
	 * 财务税点单审核放款
	 * @param expenseAccount
	 * @return
	 */
	public Tax auditTax(Tax tax);

}
