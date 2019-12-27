package com.bluewhite.ledger.dao;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.ledger.entity.PutStorage;
import java.lang.Long;
import java.util.List;
import java.lang.Integer;

public interface PutStorageDao extends BaseRepository<PutStorage, Long> {
	
	/**
	 * 根据入库仓库和产品id查找入库单
	 * @param inwarehousetypeid
	 * @param productId
	 * @return
	 */
	List<PutStorage> findByWarehouseTypeIdAndProductId(Long warehouseTypeId, Long productId);
	
	/**
	 * 根据产品id查找入库单
	 * @param inwarehousetypeid
	 * @param productId
	 * @return
	 */
	List<PutStorage> findByProductId(Long productId);
	
	/**
	 * 根据产品id和是否为查找入库单
	 * @param productId
	 * @param publicstock
	 * @return
	 */
	List<PutStorage> findByProductIdAndPublicStock(Long productId,Integer publicstock);
	
	/**
	 * 根据入库仓库和加工单id查找入库单
	 * @param inwarehousetypeid
	 * @param productId
	 * @return
	 */
	List<PutStorage> findByWarehouseTypeIdAndOrderOutSourceId(Long warehouseTypeId, Long orderOutSourceId);
	
}
