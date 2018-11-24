package com.bluewhite.finance.ledger.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.finance.ledger.entity.Customer;

@Service
public interface CustomerService extends BaseCRUDService<Customer,Long>{
	public PageResult<Customer>  findPages(Customer customer, PageParameter page);
	
	public List<Customer> findByCusProductNameAndCusPartyNames(String productName ,String partyNames);
	
	public void addCustomer(Customer customer);
}
