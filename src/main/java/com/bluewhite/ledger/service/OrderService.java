package com.bluewhite.ledger.service;

import java.util.List;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.ledger.entity.Order;

public interface OrderService extends BaseCRUDService<Order,Long>{
	
	/**
	 * 分页查看订单
	 * @param order
	 * @param page
	 * @return
	 */
	public PageResult<Order>  findPages(Order order, PageParameter page);
	
	/**
	 * 查看订单
	 * @param order
	 * @param page
	 * @return
	 */
	public List<Order>  findList(Order order);

	/**
	 * 新增订单
	 * @param order
	 */
	public List<Order> addOrder(Order order);
	
	/**
	 * 删除订单
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	public int deleteOrder(String ids);
	
}
