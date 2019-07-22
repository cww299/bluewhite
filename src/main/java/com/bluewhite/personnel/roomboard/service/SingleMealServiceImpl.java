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
import com.bluewhite.personnel.roomboard.dao.SingleMealDao;
import com.bluewhite.personnel.roomboard.entity.SingleMeal;

@Service
public class SingleMealServiceImpl extends BaseServiceImpl<SingleMeal, Long>
		implements SingleMealService {
	@Autowired
	private SingleMealDao dao;
	/*
	 *分页查询
	 */
	@Override
	public PageResult<SingleMeal> findPage(SingleMeal singleMeal, PageParameter page) {
		Page<SingleMeal> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按物料查找
			if (!StringUtils.isEmpty(singleMeal.getSingleMealConsumptionId())) {
				predicate.add(cb.equal(root.get("singleMealConsumption").get("id").as(Long.class), singleMeal.getSingleMealConsumptionId()));
			}
			// 按 类型过滤
			if (singleMeal.getType() != null) {
				predicate.add(cb.equal(root.get("type").as(Long.class), singleMeal.getType()));
			}
			// 按日期
			if (!StringUtils.isEmpty(singleMeal.getOrderTimeBegin()) && !StringUtils.isEmpty(singleMeal.getOrderTimeEnd())) {
				predicate.add(cb.between(root.get("time").as(Date.class), singleMeal.getOrderTimeBegin(),
						singleMeal.getOrderTimeEnd()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<SingleMeal> result = new PageResult<>(pages, page);
		return result;
	}
	
	/*
	 *新增修改
	 */
	@Override
	public SingleMeal addSingleMeal(SingleMeal singleMeal) {
		
			return dao.save(singleMeal);
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
