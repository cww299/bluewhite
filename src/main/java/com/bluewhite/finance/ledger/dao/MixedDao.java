package com.bluewhite.finance.ledger.dao;


import java.util.Date;
import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.finance.ledger.entity.Mixed;

public interface MixedDao extends BaseRepository<Mixed, Long>{

	List<Mixed> findByMixPartyNamesIdAndMixtSubordinateTimeBetween(Long partyNamesId, Date firstDayOfMonth,
			Date lastDayOfMonth);
	

}
