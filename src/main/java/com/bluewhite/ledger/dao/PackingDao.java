package com.bluewhite.ledger.dao;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.ledger.entity.Packing;
import java.util.Date;
import java.util.List;

public interface PackingDao extends BaseRepository<Packing, Long>{

	/**
	 * 获取日期内的所有贴报单
	 * @param packingdate
	 * @return
	 */
	List<Packing> findByPackingDate(Date packingdate);
}
