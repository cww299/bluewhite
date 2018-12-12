package com.bluewhite.finance.ledger.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.StringUtil;
import com.bluewhite.finance.ledger.dao.CustomerDao;
import com.bluewhite.finance.ledger.entity.Customer;

@Service
public class CustomerServiceImpl extends BaseServiceImpl<Customer, Long> implements CustomerService{
	 
	@Autowired
	private CustomerDao dao;
	@Override
	public PageResult<Customer> findPages(Customer param, PageParameter page) {
		Page<Customer> pages = dao.findAll((root,query,cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			Predicate[] pre = new Predicate[predicate.size()];
			
			query.where(predicate.toArray(pre));
			
        	return null;
		},page);
		
			PageResult<Customer> result = new PageResult<Customer>(pages, page);
        return result;
	}
	@Override
	public List<Customer> findByCusProductNameAndCusPartyNames(String productName, String partyNames) {
		
		return dao.findByCusProductNameLikeAndCusPartyNames("%"+StringUtil.specialStrKeyword(productName)+"%", partyNames);
	}
	@Override
	@Transactional
	public void addCustomer(Customer customer) {
		dao.save(customer);
		
	}

}
