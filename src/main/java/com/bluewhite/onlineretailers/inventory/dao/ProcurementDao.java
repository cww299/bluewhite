package com.bluewhite.onlineretailers.inventory.dao;

import java.util.Date;
import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.onlineretailers.inventory.entity.Procurement;
import java.lang.Integer;

public interface ProcurementDao  extends BaseRepository<Procurement, Long>{
	
	/**
	 * 根据类型和时间查询
	 * @param type
	 * @param startTime
	 * @param beginTime
	 * @return
	 */
	List<Procurement> findByTypeAndCreatedAtBetween(int type, Date startTime, Date endTime);
	
	/**
	 * 查询销售出库的出库单
	 * @param type
	 * @param startTime
	 * @param beginTime
	 * @return
	 */
	List<Procurement> findByTypeAndStatusAndCreatedAtBetween(int type,int status, Date startTime, Date endTime);
	
	
	/**
	 * 根据下单合同查找生产单
	 * @return
	 */
	Procurement findByOrderId(Long id);
}
