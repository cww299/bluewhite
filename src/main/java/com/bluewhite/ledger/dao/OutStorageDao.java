package com.bluewhite.ledger.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.ledger.entity.OutStorage;
import java.lang.Long;

public interface OutStorageDao extends BaseRepository<OutStorage, Long>{
	
	/**
	 * 根据发货单查询出库单
	 * @param sendgoodsid
	 * @return
	 */
	List<OutStorage> findBySendGoodsId(Long sendgoodsid);
	

}
