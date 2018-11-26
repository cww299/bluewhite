package com.bluewhite.finance.ledger.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.finance.ledger.dao.BillDao;
import com.bluewhite.finance.ledger.entity.Bill;
@Service
public class BillServiceImpl extends BaseServiceImpl<Bill, Long> implements BillService{

	@Autowired
	private BillDao dao;
	
	@Override
	public PageResult<Bill> findPages(Bill param, PageParameter page) {
		
		Page<Bill> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按id过滤
			if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class), param.getId()));
			}
			
			// 按id过滤
			if (param.getPartyNamesId() != null) {
				predicate.add(cb.equal(root.get("partyNamesId").as(Long.class), param.getPartyNamesId()));
			}
			
			//按姓名查找
			if (!StringUtils.isEmpty(param.getPartyNames())) {
				predicate.add(cb.like(root.get("partyNames").as(String.class),"%" + param.getPartyNames() + "%"));
			}

			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<Bill> result = new PageResult<>(pages, page);
		return result;
	}

}
