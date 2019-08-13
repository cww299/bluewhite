package com.bluewhite.ledger.dao;


import com.bluewhite.base.BaseRepository;
import com.bluewhite.ledger.entity.Customer;
import java.lang.String;
import java.util.List;

public interface CustomerDao extends BaseRepository<Customer, Long>{
	
	Customer findByPhone(String phone);
	
	Customer findByName(String name);
}
