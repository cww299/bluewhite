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
import com.bluewhite.finance.ledger.dao.MixedDao;
import com.bluewhite.finance.ledger.entity.Contact;
import com.bluewhite.finance.ledger.entity.Customer;
import com.bluewhite.finance.ledger.entity.Mixed;

@Service
public class MixedServiceImpl extends BaseServiceImpl<Mixed, Long> implements MixedService{
	 
	@Autowired
	private MixedDao dao;
	@Override
	public PageResult<Mixed> findPages(Mixed param, PageParameter page) {
		Page<Mixed> pages = dao.findAll((root,query,cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
        	return null;
		},page);
		
			PageResult<Mixed> result = new PageResult<Mixed>(pages, page);
        return result;
	}
	
	
	@Override
	@Transactional
	public void addMixed(Mixed mixed) {
		dao.save(mixed);
	}

}
