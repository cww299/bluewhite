package com.bluewhite.finance.expenseAccount.service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.finance.expenseAccount.entity.ExpenseAccount;

public interface ExpenseAccountService  extends BaseCRUDService<ExpenseAccount,Long>{
	
	/**
	 * 分页查看报销单
	 * @param expenseAccount
	 * @param page
	 * @return
	 */
	PageResult<ExpenseAccount> findPages(ExpenseAccount expenseAccount, PageParameter page);
	
	/**
	 * 新增报销单
	 * @param expenseAccount
	 * @return
	 */
	public ExpenseAccount addExpenseAccount(ExpenseAccount expenseAccount);
	
	/**
	 * 删除报销单
	 * @param expenseAccount
	 * @return
	 */
	public int deleteAccountService(String ids);
	
	/**
	 * 修改报销单
	 * @param expenseAccount
	 * @return
	 */
	public ExpenseAccount updateExpenseAccount(ExpenseAccount expenseAccount);
	/**
	 * 财务报销单审核放款
	 * @param expenseAccount
	 * @return
	 */
	public ExpenseAccount auditExpenseAccount(ExpenseAccount expenseAccount);

}
