package com.bluewhite.ledger.service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.ledger.entity.ScatteredOutbound;

public interface ScatteredOutboundService extends BaseCRUDService<ScatteredOutbound,Long> {
	
	/**
	 * 将耗料单转换成分散出库单，新增分散出库单
	 * @param ids
	 */
	public int saveScatteredOutbound(String ids);

}
