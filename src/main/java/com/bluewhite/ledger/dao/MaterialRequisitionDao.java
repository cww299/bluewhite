package com.bluewhite.ledger.dao;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.ledger.entity.MaterialRequisition;
import java.lang.Long;
import java.util.List;

public interface MaterialRequisitionDao extends BaseRepository<MaterialRequisition, Long>{
	
	/**
	 * 根据生产计划单查询领料单
	 * @param orderid
	 * @return
	 */
	List<MaterialRequisition> findByOrderId(Long orderid);
	
}
