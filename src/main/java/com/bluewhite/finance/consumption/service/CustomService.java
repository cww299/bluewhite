package com.bluewhite.finance.consumption.service;

import java.util.List;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.finance.consumption.entity.Consumption;
import com.bluewhite.finance.consumption.entity.Custom;

public interface CustomService  extends BaseCRUDService<Custom, Long> {
	
	/**
	 * 根据类型查找
	 * @param type
	 * @return
	 */
	List<Custom> findCustom(Integer type);
	
	/**
	 * 分页查看
	 * 
	 * @param expenseAccount
	 * @param page
	 * @return
	 */
	PageResult<Custom> findPages(Custom custom, PageParameter page);

}
