package com.bluewhite.ledger.dao;

import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.ledger.entity.OutStorage;

public interface OutStorageDao extends BaseRepository<OutStorage, Long>{
	
	/**
	 * 根据发货单查询出库单
	 * @param sendgoodsid
	 * @return
	 */
	List<OutStorage> findBySendGoodsId(Long sendgoodsid);
	
	/**
	 * 根据加工单单查询出库单
	 * @param orderoutsourceid
	 * @return
	 */
	List<OutStorage> findByOrderOutSourceId(Long orderoutsourceid);
	

}
