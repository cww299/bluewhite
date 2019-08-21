package com.bluewhite.production.finance.dao;

import java.util.Date;
import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.production.finance.entity.CollectPay;

public interface CollectPayDao  extends BaseRepository<CollectPay, Long>{
	/**
	 * 根据条件查找
	 * @param userId
	 * @param type
	 * @param orderTimeBegin
	 * @param orderTimeEnd
	 * @return
	 */
	CollectPay findByUserIdAndTypeAndAllotTimeBetween(Long userId, Integer type, Date orderTimeBegin,
			Date orderTimeEnd);
	
	/**
	 * 根据条件查找
	 * @param type
	 * @param orderTimeBegin
	 * @param orderTimeEnd
	 * @return
	 */
	List<CollectPay> findByTypeAndAllotTimeBetween(Integer type, Date orderTimeBegin,
			Date orderTimeEnd);

}
