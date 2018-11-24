package com.bluewhite.finance.ledger.service;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.finance.ledger.entity.Order;

@Service
public interface OrderService extends BaseCRUDService<Order,Long>{
	
	public PageResult<Order>  findPages(Order order, PageParameter page);

	public void addOrder(Order order);
	
	public void deleteOrder(String ids);
}
