package com.bluewhite.onlineretailers.inventory.dao;

import java.util.Date;
import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.onlineretailers.inventory.entity.OnlineOrder;

public interface OnlineOrderDao  extends BaseRepository<OnlineOrder, Long>{
	
	
	/**
	 * 根据状态和时间查询销售单
	 * @param status
	 * @param orderTimeBegin
	 * @param orderTimeEnd
	 * @return
	 */
	List<OnlineOrder> findByStatusAndCreatedAtBetween(String status,Date orderTimeBegin, Date orderTimeEnd);
	
	
	/**
	 * 查询销售单
	 * @param status
	 * @param orderTimeBegin
	 * @param orderTimeEnd
	 * @return
	 */
	List<OnlineOrder> findByFlagAndCreatedAtBetween(Integer flag,Date orderTimeBegin, Date orderTimeEnd);
	
}
