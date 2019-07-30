package com.bluewhite.ledger.service;

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
import com.bluewhite.ledger.dao.CustomerDao;
import com.bluewhite.ledger.entity.Customer;
@Service
public class CustomerServiceImpl extends BaseServiceImpl<Customer, Long> implements CustomerService{
	
	@Autowired
	private CustomerDao dao;

	@Override
	public PageResult<Customer> findPages(Customer param, PageParameter page) {
		Page<Customer> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按类型过滤
			if (param.getType() != null) {
				predicate.add(cb.equal(root.get("type").as(Long.class), param.getType()));
			}
			// 按名称查找
			if (!StringUtils.isEmpty(param.getName())) {
				predicate.add(cb.like(root.get("name").as(String.class), "%" + param.getName() + "%"));
			}

			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<Customer> result = new PageResult<>(pages, page);
		return result;
	}

	@Override
	public int deleteCustomr(String ids) {
		int count = 0;
		if (!StringUtils.isEmpty(ids)) { 
			String [] idStrings = ids.split(",");
			for(String id : idStrings){
				Long idLong = Long.valueOf(id);
				Customer customer = dao.findOne(idLong);
				customer.setUserId(null);
				dao.delete(customer);
				count++;
			}
		}
		return count;
	}

	

}
