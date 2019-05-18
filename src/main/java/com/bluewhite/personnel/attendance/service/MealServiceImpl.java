package com.bluewhite.personnel.attendance.service;

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
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.personnel.attendance.dao.MealDao;
import com.bluewhite.personnel.attendance.dao.PersonVariableDao;
import com.bluewhite.personnel.attendance.entity.Meal;
import com.bluewhite.personnel.attendance.entity.PersonVariable;
import com.bluewhite.system.user.entity.User;
import com.bluewhite.system.user.service.UserService;

@Service
public class MealServiceImpl extends BaseServiceImpl<Meal, Long>
		implements MealService {
	@Autowired
	private MealDao dao;
	@Autowired
	private PersonVariableDao personVariableDao;
	@Autowired
	private UserService userService;
	@Override
	public PageResult<Meal> findPage(Meal param, PageParameter page) {
		Page<Meal> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按用户 id过滤
			if (param.getUserId() != null) {
				predicate.add(cb.equal(root.get("userId").as(Long.class), param.getUserId()));
			}
			// 按姓名查找
			if (!StringUtils.isEmpty(param.getUserName())) {
				predicate.add(cb.equal(root.get("user").get("userName").as(String.class), param.getUserName()));
			}
			// 按部门查找
			if (!StringUtils.isEmpty(param.getOrgNameId())) {
				predicate.add(cb.equal(root.get("user").get("orgNameId").as(Long.class), param.getOrgNameId()));
			}
			// 按报餐类型
			if (param.getMode() != null) {
				predicate.add(cb.equal(root.get("mode").as(Integer.class), param.getMode()));
			}
			// 按日期
			if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
				predicate.add(cb.between(root.get("tradeDaysTime").as(Date.class), param.getOrderTimeBegin(),
						param.getOrderTimeEnd()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<Meal> result = new PageResult<>(pages, page);
		return result;
	}
	/*
	 * 去除分页查询
	 */
	@Override
	public List<Meal> findMeal(Meal param) {
		List<Meal> list = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按用户 id过滤
			if (param.getUserId() != null) {
				predicate.add(cb.equal(root.get("userId").as(Long.class), param.getUserId()));
			}
			// 按姓名查找
			if (!StringUtils.isEmpty(param.getUserName())) {
				predicate.add(cb.equal(root.get("user").get("userName").as(String.class), param.getUserName()));
			}
			// 按部门查找
			if (!StringUtils.isEmpty(param.getOrgNameId())) {
				predicate.add(cb.equal(root.get("user").get("orgNameId").as(Long.class), param.getOrgNameId()));
			}
			// 按报餐类型
			if (param.getMode() != null) {
				predicate.add(cb.equal(root.get("mode").as(Integer.class), param.getMode()));
			}
			// 按日期
			if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
				predicate.add(cb.between(root.get("tradeDaysTime").as(Date.class), param.getOrderTimeBegin(),
						param.getOrderTimeEnd()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		});
		return list;
	}	
	@Override
	public Meal addMeal(Meal meal) {
		//按报餐类型查找  找出每餐费用
	 User user= userService.findOne(meal.getUserId());
	 meal.setOrgNameId(user.getOrgNameId());
	 PersonVariable variable=personVariableDao.findByType(1);
	 if (meal.getMode()==1) {
		 meal.setPrice(Double.valueOf(variable.getKeyValue()));
	 }
	 if (meal.getMode()==2) {
		 meal.setPrice(Double.valueOf(variable.getKeyValueTwo()));
	 }
	 if (meal.getMode()==3) {
		 meal.setPrice(Double.valueOf(variable.getKeyValueThree()));
	 }
	 if (meal.getId()==null) {
	 String date = meal.getTime();
	 String[] addDate = date.split("~");
	 List<Date> dateList=DatesUtil.getPerDaysByStartAndEndDate(addDate[0],addDate[1],"yyyy-MM-dd");
	 List<Meal> meals=new ArrayList<Meal>();
	 for (Date date2 : dateList) {
		 Meal meal2=new Meal();
		 meal2.setTradeDaysTime(date2);
		 meal2.setPrice(meal.getPrice());
		 meal2.setMode(meal.getMode());
		 meal2.setUserName(meal.getUserName());
		 meal2.setUserId(meal.getUserId());
		 meals.add(meal2);
	}
	 dao.save(meals);
	 }else{
	 dao.save(meal);
	 }
	return meal;
	}
	
	//查询字典表中 报餐价格
	@Override
	public PersonVariable findByType(Integer type) {
		
		return personVariableDao.findByType(type);
	}

	@Override
	public PersonVariable updateperson(PersonVariable personVariable) {
		
		return personVariableDao.save(personVariable);
	}
	
	/*
	 * 汇总查询
	 */
	@Override
	public List<Map<String, Object>> findMealSummary(Meal meal) {
		List<Map<String, Object>> allList = new ArrayList<>();
		// 单向数据map
		Map<String, Object> allMap = null;
		List<Meal> mealsList= findMeal(meal);
		Map<Long, List<Meal>> mealMap = mealsList.stream()
			.filter(Meal -> Meal.getUserId() != null)
			.collect(Collectors.groupingBy(Meal::getUserId, Collectors.toList()));
		for (Long ps1 : mealMap.keySet()) {
			allMap = new HashMap<>();
			// 获取单一员工日期区间所有的报餐数据
			List<Meal> psList1 = mealMap.get(ps1);
			List<Double> listDouble = new ArrayList<>();
			psList1.stream().filter(Meal->Meal.getUserId().equals(Meal.getUserId())).forEach(
					c->{listDouble.add(c.getPrice());
			});
			double budget = NumUtils.sum(listDouble);
			double modeOne=psList1.stream().filter(Meal->Meal.getUserId().equals(Meal.getUserId()) && Meal.getMode()==1).count();
			double modeTwo=psList1.stream().filter(Meal->Meal.getUserId().equals(Meal.getUserId()) && Meal.getMode()==2).count();
			double modeThree=psList1.stream().filter(Meal->Meal.getUserId().equals(Meal.getUserId()) && Meal.getMode()==3).count();
			User user= userService.findOne(ps1);
			String aString= user.getUserName();
			allMap.put("username", aString);
			allMap.put("money", budget);
			allMap.put("modeOne",modeOne);
			allMap.put("modeTwo",modeTwo);
			allMap.put("modeThree",modeThree);
			allList.add(allMap);
			}
		
		return allList;
	}

	
	
	
	


}
