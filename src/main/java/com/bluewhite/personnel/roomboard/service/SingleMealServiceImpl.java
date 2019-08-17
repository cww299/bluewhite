package com.bluewhite.personnel.roomboard.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.NumUtils;
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

	/*
	 *汇总物料
	 */
	@Override
	public List<Map<String, Object>> SingleSummary(SingleMeal singleMeal) {
	List<SingleMeal> list=dao.findByTimeBetween(singleMeal.getOrderTimeBegin(), singleMeal.getOrderTimeEnd());
	List<Map<String, Object>> allList = new ArrayList<>();
	Map<String, Object> allMap = null;
	Map<Integer, List<SingleMeal>> map = list.stream()
			.filter(SingleMeal -> SingleMeal.getType() != null)
			.collect(Collectors.groupingBy(SingleMeal::getType, Collectors.toList()));
	for (Integer ps1 : map.keySet()) {
		allMap = new HashMap<>();
		List<SingleMeal> psList1 = map.get(ps1);
		List<Double> listDouble = new ArrayList<>();
		psList1.stream().filter(SingleMeal-> SingleMeal.getType().equals(SingleMeal.getType())).forEach(c -> {
			listDouble.add(c.getPrice());
		});
		String aString = null;
		if (ps1.equals(1)) {
			aString="早餐";
		}else if (ps1.equals(2)) {
			aString="中餐";
		}else if (ps1.equals(3)) {
			aString="晚餐";
		}else if (ps1.equals(4)) {
			aString="夜宵";
		}
		double budget = NumUtils.sum(listDouble);
		allMap.put("money", budget);
		allMap.put("type", aString);
		allList.add(allMap);
		}
		return null;
	}

	@Override
	public List<SingleMeal> findSingleMeal(SingleMeal singleMeal) {
		List<SingleMeal> list = dao.findAll((root, query, cb) -> {
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
		});
		return list;
	}

	
	
}
