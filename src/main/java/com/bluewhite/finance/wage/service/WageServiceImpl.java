package com.bluewhite.finance.wage.service;

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
import com.bluewhite.finance.wage.dao.WageDao;
import com.bluewhite.finance.wage.entity.Wage;

@Service
public class WageServiceImpl extends BaseServiceImpl<Wage, Long> implements WageService {

	@Autowired
	private WageDao dao;

	@Override
	public PageResult<Wage> findPages(Wage param, PageParameter page) {
		Page<Wage> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<Wage> result = new PageResult<>(pages, page);
		return result;
	}
	/*删除*/
	@Override
	public int deleteWage(String[] ids) {
		int count = 0;
		if(!StringUtils.isEmpty(ids)){
			for (int i = 0; i < ids.length; i++) {
				Long id = Long.parseLong(ids[i]);
				dao.delete(id); 
				count++;
			}
		}
		return count;
	}
	/*新增修改*/
	@Override
	public Wage addWage(Wage wage) {
		return dao.save(wage);
	}



	
}
