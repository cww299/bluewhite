package com.bluewhite.ledger.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.ledger.entity.OrderOutSource;

public interface OrderOutSourceDao extends BaseRepository<OrderOutSource, Long>{
	
	/**
	 * 根据合同id查找外发单
	 * @param orderid
	 * @return
	 */
	List<OrderOutSource> findByMaterialRequisitionId(Long materialRequisitionId);
	
}
