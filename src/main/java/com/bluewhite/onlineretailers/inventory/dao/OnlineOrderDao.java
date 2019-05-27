package com.bluewhite.onlineretailers.inventory.dao;

import java.util.Date;
import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.onlineretailers.inventory.entity.OnlineOrder;

public interface OnlineOrderDao  extends BaseRepository<OnlineOrder, Long>{
	
	
	/**
	 * 
	 * @param status
	 * @param orderTimeBegin
	 * @param orderTimeEnd
	 * @return
	 */
	List<OnlineOrder> findByStatusAndCreatedAtBetween(String status, Date orderTimeBegin, Date orderTimeEnd);

}
