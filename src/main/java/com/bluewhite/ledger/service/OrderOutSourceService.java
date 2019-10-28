package com.bluewhite.ledger.service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.ledger.entity.Order;
import com.bluewhite.ledger.entity.OrderOutSource;

public interface OrderOutSourceService extends BaseCRUDService<OrderOutSource, Long> {

	/**
	 * 新增外发单
	 * 
	 * @param orderOutSource
	 */
	public void saveOrderOutSource(OrderOutSource orderOutSource);

	/**
	 * 分页查看外发单
	 * 
	 * @param order
	 * @param page
	 * @return
	 */
	public PageResult<OrderOutSource> findPages(OrderOutSource orderOutSource, PageParameter page);

	/**
	 * 新增外发单
	 * 
	 * @param orderOutSource
	 */
	public int deleteOrderOutSource(String ids);

}
