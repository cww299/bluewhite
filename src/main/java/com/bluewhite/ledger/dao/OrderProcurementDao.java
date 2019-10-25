package com.bluewhite.ledger.dao;

import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.ledger.entity.OrderProcurement;

public interface OrderProcurementDao  extends BaseRepository<OrderProcurement, Long>{
	
	/**
	 * 库存不符预警
	 * @param inOutError
	 * @return
	 */
	List<OrderProcurement> findByInOutError(Integer inOutError);

}
