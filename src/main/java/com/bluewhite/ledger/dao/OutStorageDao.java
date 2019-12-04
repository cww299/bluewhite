package com.bluewhite.ledger.dao;

import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.ledger.entity.OutStorage;

public interface OutStorageDao extends BaseRepository<OutStorage, Long>{
	
	/**
	 * 根据入库单id查找
	 * @param id
	 * @return
	 */
	List<OutStorage> findByPutStorageId(Long id);

}
