package com.bluewhite.finance.ledger.dao;


import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.finance.ledger.entity.Customer;

public interface CustomerDao extends BaseRepository<Customer, Long>{
	
	
  public List<Customer> findByCusProductNameLikeAndCusPartyNames(String productName ,String partyNames);
	

}
