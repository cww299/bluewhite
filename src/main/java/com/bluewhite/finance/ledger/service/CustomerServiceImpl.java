package com.bluewhite.finance.ledger.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
			
			//按乙方姓名查找
			if (!StringUtils.isEmpty(param.getCusPartyNames())) {
				predicate.add(cb.like(root.get("cusPartyNames").as(String.class),"%" + param.getCusPartyNames() + "%"));
			}
			//按乙方姓名查找
			if (!StringUtils.isEmpty(param.getCusProductName())) {
				predicate.add(cb.like(root.get("cusProductName").as(String.class),"%" + param.getCusProductName() + "%"));
			}
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
