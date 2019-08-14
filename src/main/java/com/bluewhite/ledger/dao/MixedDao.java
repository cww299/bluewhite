package com.bluewhite.ledger.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Transient;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.ledger.entity.Mixed;

public interface MixedDao extends BaseRepository<Mixed, Long> {
	/**
	 * 根據客戶和日期查找
	 * @return
	 */
	List<Mixed> findByCustomerIdAndMixtTimeBetween(Long idLong ,Date orderTimeBegin,Date orderTimeEnd);

}
