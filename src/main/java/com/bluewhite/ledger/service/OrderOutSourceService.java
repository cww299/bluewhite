package com.bluewhite.ledger.service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.ledger.entity.OrderOutSource;

public interface OrderOutSourceService extends BaseCRUDService<OrderOutSource, Long> {
	
	/**
	 * 新增外发单
	 * @param orderOutSource
	 */
	public void saveOrderOutSource(OrderOutSource orderOutSource);

}
