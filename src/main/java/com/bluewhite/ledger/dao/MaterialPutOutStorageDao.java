package com.bluewhite.ledger.dao;

import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.ledger.entity.MaterialPutOutStorage;
import java.lang.Long;

public interface MaterialPutOutStorageDao extends BaseRepository<MaterialPutOutStorage, Long>{
	
	/**
	 * 根据入库单查找实际出库数量
	 * @param putstorageid
	 * @return
	 */
	List<MaterialPutOutStorage> findByMaterialPutStorageId(Long putstorageid);
	
	/**
	 * 根据出库单查找实际出库数量
	 * @param putstorageid
	 * @return
	 */
	List<MaterialPutOutStorage> findByMaterialOutStorageId(Long materialoutstorageid);

}
