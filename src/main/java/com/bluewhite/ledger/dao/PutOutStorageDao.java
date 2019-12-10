package com.bluewhite.ledger.dao;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.ledger.entity.PutOutStorage;
import java.lang.Long;
import java.util.List;

public interface PutOutStorageDao extends BaseRepository<PutOutStorage, Long> {

	/**
	 * 根据入库单查找实际出库数量
	 * @param putstorageid
	 * @return
	 */
	List<PutOutStorage> findByPutStorageId(Long putstorageid);
}
