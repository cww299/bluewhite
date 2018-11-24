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
import com.bluewhite.finance.ledger.dao.ContactDao;
import com.bluewhite.finance.ledger.dao.CustomerDao;
import com.bluewhite.finance.ledger.entity.Contact;
import com.bluewhite.finance.ledger.entity.Customer;

@Service
public class ContactServiceImpl extends BaseServiceImpl<Contact, Long> implements ContactService{
	 
	@Autowired
	private ContactDao dao;
	@Override
	public PageResult<Contact> findPages(Contact param, PageParameter page) {
		Page<Contact> pages = dao.findAll((root,query,cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			
			//按姓名查找
			if (!StringUtils.isEmpty(param.getConPartyNames())) {
				predicate.add(cb.like(root.get("conPartyNames").as(String.class),"%" + param.getConPartyNames() + "%"));
			}
			
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
        	return null;
		},page);
		
			PageResult<Contact> result = new PageResult<Contact>(pages, page);
        return result;
	}
	
	
	
	
	@Override
	@Transactional
	public void addContact(Contact customer) {
		dao.save(customer);
		
	}

}
