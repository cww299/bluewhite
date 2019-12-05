package com.bluewhite.ledger.dao;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.ledger.entity.PutStorage;
import java.lang.Long;
import java.util.List;

public interface PutStorageDao extends BaseRepository<PutStorage, Long> {
	
	/**
	 * 根据入库仓库和产品id查找入库单
	 * @param inwarehousetypeid
	 * @param productId
	 * @return
	 */
	List<PutStorage> findByWarehouseTypeIdAndProductId(Long warehouseTypeId, Long productId);
}
