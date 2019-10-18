package com.bluewhite.ledger.dao;


import java.util.Date;
import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.ledger.entity.Order;

public interface OrderDao extends BaseRepository<Order, Long>{
	
	/**
	 * 根据批次
	 * @param bacthnumber
	 * @return
	 */
	Order findByBacthNumber(String bacthnumber);
	
}
