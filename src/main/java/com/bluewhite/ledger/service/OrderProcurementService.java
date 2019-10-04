package com.bluewhite.ledger.service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.ledger.entity.OrderProcurement;

public interface OrderProcurementService extends BaseCRUDService<OrderProcurement, Long> {
	
	
	/**
	 * 分页查看面料采购订单
	 * 
	 * @param mixed
	 * @param page
	 * @return
	 */
	public PageResult<OrderProcurement> findPages(OrderProcurement orderProcurement, PageParameter page);
	
	/**
	 * （采购部）确认库存不足的面料采购订单
	 * @param ids
	 * @return
	 */
	public int confirmOrderProcurement(String ids);

}
