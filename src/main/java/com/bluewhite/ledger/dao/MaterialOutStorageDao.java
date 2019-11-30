package com.bluewhite.ledger.dao;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.ledger.entity.MaterialOutStorage;
import java.lang.Long;
import java.util.List;

public interface MaterialOutStorageDao extends BaseRepository<MaterialOutStorage, Long>{
	
	/**
	 * 根据入库单查找
	 * @param materialputstorageid
	 * @return
	 */
	List<MaterialOutStorage> findByMaterialPutStorageId(Long materialputstorageid);


}
