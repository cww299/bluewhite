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
	public int confirmOrderProcurement(OrderProcurement orderProcurement);
	
	/**
	 * 新增采购单
	 * @param orderProcurement
	 */
	public void saveOrderProcurement(OrderProcurement orderProcurement);
	
	/**
	 * 删除采购单
	 * @param ids
	 * @return
	 */
	public int deleteOrderProcurement(String ids);

}
