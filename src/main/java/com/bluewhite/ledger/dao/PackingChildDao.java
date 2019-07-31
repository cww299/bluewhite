package com.bluewhite.ledger.dao;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.ledger.entity.PackingChild;
import java.lang.Long;
import java.util.List;

public interface PackingChildDao  extends BaseRepository<PackingChild, Long>{

	List<PackingChild> findBySendGoodsId(Long sendgoodsid);
	
}
