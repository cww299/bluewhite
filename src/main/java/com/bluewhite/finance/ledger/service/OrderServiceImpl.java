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
import com.bluewhite.finance.ledger.dao.OrderDao;
import com.bluewhite.finance.ledger.entity.Order;

@Service
public class OrderServiceImpl extends BaseServiceImpl<Order, Long> implements OrderService{
	 
	@Autowired
	private OrderDao dao;
	@Override
	public PageResult<Order> findPages(Order param, PageParameter page) {
		Page<Order> pages = dao.findAll((root,query,cb) -> {
        	List<Predicate> predicate = new ArrayList<>();
        	//按id过滤
        	if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class),param.getId()));
			}
        	
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
        	return null;
        },page);
		PageResult<Order> result = new PageResult<Order>(pages, page);
        return result;
	}

	@Override
	@Transactional
	public void addOrder(Order order) {
		dao.save(order);
	}
}
