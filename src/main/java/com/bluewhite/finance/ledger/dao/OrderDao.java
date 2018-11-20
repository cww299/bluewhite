package com.bluewhite.finance.ledger.dao;


import java.util.Date;
import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.finance.ledger.entity.Order;

public interface OrderDao extends BaseRepository<Order, Long>{
	
	
  public List<Order> findBetweenByCreatedAt(Date fristTime ,Date lastTime);
	

}
