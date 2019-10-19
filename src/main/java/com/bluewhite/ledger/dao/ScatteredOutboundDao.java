package com.bluewhite.ledger.dao;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.ledger.entity.ScatteredOutbound;
import java.lang.Long;
import java.util.List;

public interface ScatteredOutboundDao extends BaseRepository<ScatteredOutbound, Long> {
	
	/**
	 * 根据采购单查找分散出库记录
	 * @param orderprocurementid
	 * @return
	 */
	List<ScatteredOutbound> findByOrderProcurementId(Long orderprocurementid);

}
