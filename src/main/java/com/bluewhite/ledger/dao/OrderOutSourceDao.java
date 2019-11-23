package com.bluewhite.ledger.dao;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.ledger.entity.OrderMaterial;
import com.bluewhite.ledger.entity.OrderOutSource;
import java.lang.Long;
import java.util.List;

public interface OrderOutSourceDao extends BaseRepository<OrderOutSource, Long>{
	
	/**
	 * 根据合同id查找外发单
	 * @param orderid
	 * @return
	 */
	List<OrderOutSource> findByOrderId(Long orderid);

}
