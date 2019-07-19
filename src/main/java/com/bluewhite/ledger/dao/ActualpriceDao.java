package com.bluewhite.ledger.dao;


import java.util.Date;
import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.ledger.entity.Actualprice;

public interface ActualpriceDao extends BaseRepository<Actualprice, Long>{
	
	public List<Actualprice> findByProductNameLikeAndBatchNumber(String productName,String batchNumber);
	
	public List<Actualprice> findByCurrentMonthBetween(Date firstDayOfMonth,Date lastDayOfMonth);
}
