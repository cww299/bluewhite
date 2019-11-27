package com.bluewhite.ledger.dao;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.ledger.entity.ProcessPrice;
import java.lang.Long;
import java.util.List;

public interface ProcessPriceDao extends BaseRepository<ProcessPrice, Long>{
	
	/**
	 * 根据加工单id查找对应工序价格
	 * @param orderoutsourceid
	 * @return
	 */
	List<ProcessPrice> findByOrderOutSourceId(Long orderoutsourceid);
	

}
