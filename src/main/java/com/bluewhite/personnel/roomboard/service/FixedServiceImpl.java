package com.bluewhite.personnel.roomboard.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.personnel.roomboard.dao.FixedDao;
import com.bluewhite.personnel.roomboard.entity.Fixed;

@Service
public class FixedServiceImpl extends BaseServiceImpl<Fixed, Long>
		implements FixedService {
	@Autowired
	private FixedDao dao;
	/*
	 *分页查询
	 */
	@Override
	public PageResult<Fixed> findPage(Fixed fixed, PageParameter page) {
		Page<Fixed> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			//按宿舍查询
			if (fixed.getHostelId() != null) {
				predicate.add(cb.equal(root.get("hostelId").as(Long.class), fixed.getHostelId()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<Fixed> result = new PageResult<>(pages, page);
		return result;
	}

	/*新增*/
	@Override
	public Fixed addFixed(Fixed fixed) {
		fixed.setPrice(NumUtils.div(fixed.getSum(),fixed.getBranch(),2));
		fixed.setSurplusSum(fixed.getSum());
		return dao.save(fixed);
	}



	
	
	
}
