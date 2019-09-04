package com.bluewhite.personnel.roomboard.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.personnel.roomboard.dao.PlanDao;
import com.bluewhite.personnel.roomboard.entity.Plan;

@Service
public class PlanServiceImpl extends BaseServiceImpl<Plan, Long>
		implements PlanService {
	@Autowired
	private PlanDao dao;
	/*
	 *分页查询
	 */
	@Override
	public PageResult<Plan> findPage(Plan plan, PageParameter page) {
		Page<Plan> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按日期
			if (!StringUtils.isEmpty(plan.getOrderTimeBegin()) && !StringUtils.isEmpty(plan.getOrderTimeEnd())) {
				predicate.add(cb.between(root.get("time").as(Date.class), plan.getOrderTimeBegin(),
						plan.getOrderTimeEnd()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<Plan> result = new PageResult<>(pages, page);
		return result;
	}
	
	/*
	 *新增修改
	 */
	@Override
	public Plan addPlan(Plan plan) {
		
		return dao.save(plan);
	}
	/*
	 *删除
	 */
	@Override
	public int deletes(String[] ids) {
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
	
}
