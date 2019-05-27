package com.bluewhite.onlineretailers.inventory.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Transient;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.onlineretailers.inventory.entity.OnlineOrderChild;

public interface OnlineOrderChildDao  extends BaseRepository<OnlineOrderChild, Long>{
	
	
	/***
	 * 获取已发货的销售子单
	 * @param status
	 * @param orderTimeBegin
	 * @param orderTimeEnd
	 * @return
	 */
	List<OnlineOrderChild> findByStatusAndCreatedAtBetween(String status,Date orderTimeBegin,Date orderTimeEnd);

}
