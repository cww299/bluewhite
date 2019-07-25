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
import com.bluewhite.ledger.dao.CustomrDao;
import com.bluewhite.ledger.entity.Customr;
@Service
public class CustomrServiceImpl extends BaseServiceImpl<Customr, Long> implements CustomrService{
	
	@Autowired
	private CustomrDao dao;

	@Override
	public PageResult<Customr> findPages(Customr param, PageParameter page) {
		Page<Customr> pages = dao.findAll((root, query, cb) -> {
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
		PageResult<Customr> result = new PageResult<>(pages, page);
		return result;
	}

	@Override
	public int deleteCustomr(String ids) {
		
		
		return 0;
	}

	

}
