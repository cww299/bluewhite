package com.bluewhite.finance.ledger.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.StringUtil;
import com.bluewhite.finance.ledger.dao.ActualpriceDao;
import com.bluewhite.finance.ledger.entity.Actualprice;

@Service
public class ActualpriceServiceImpl extends BaseServiceImpl<Actualprice, Long> implements ActualpriceService{
	 
	@Autowired
	private ActualpriceDao dao;
	@Override
	public List<Actualprice> findByProductNameLikeAndBatchNumber(String productName, String batchNumber) {
		return dao.findByProductNameLikeAndBatchNumber("%"+StringUtil.specialStrKeyword(productName)+"%", batchNumber);
	}
	@Override
	public List<Actualprice> findPages(Actualprice actualprice) {
		
		List<Actualprice> pages = dao.findAll((root,query,cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			if(actualprice.getBatchNumber()!=null){
			//按乙方姓名查找
			if (!StringUtils.isEmpty(actualprice.getBatchNumber())) {
				predicate.add(cb.like(root.get("batchNumber").as(String.class),"%" + actualprice.getBatchNumber() + "%"));
			}
			}
			//按产品姓名查找
			if (!StringUtils.isEmpty(actualprice.getProductName())) {
				predicate.add(cb.like(root.get("productName").as(String.class),"%" + actualprice.getProductName() + "%"));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			
			query.where(predicate.toArray(pre));
        	return null;
		});
		
			List<Actualprice> result = pages;
        return result;
	}
	@Override
	public PageResult<Actualprice> findPages(Actualprice param, PageParameter page) {
		
		if (!StringUtils.isEmpty(param.getId())) {
			Sort sort = new Sort(Direction.DESC, "createdAt");
			page.setSort(sort);
		}
		Page<Actualprice> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			Predicate[] pre = new Predicate[predicate.size()];

			query.where(predicate.toArray(pre));

			return null;
		}, page);
		PageResult<Actualprice> result = new PageResult<Actualprice>(pages, page);
		return result;
	}
	@Override
	public void addActualprice(Actualprice actualprice) {
		dao.save(actualprice);
	}

}
