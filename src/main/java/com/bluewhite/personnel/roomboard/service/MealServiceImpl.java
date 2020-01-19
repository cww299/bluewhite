package com.bluewhite.personnel.roomboard.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.basedata.dao.BaseDataDao;
import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.finance.wage.dao.WageDao;
import com.bluewhite.finance.wage.entity.Wage;
import com.bluewhite.personnel.attendance.dao.AttendanceInitDao;
import com.bluewhite.personnel.attendance.dao.PersonVariableDao;
import com.bluewhite.personnel.attendance.entity.AttendanceInit;
import com.bluewhite.personnel.attendance.entity.AttendanceTime;
import com.bluewhite.personnel.attendance.entity.PersonVariable;
import com.bluewhite.personnel.attendance.service.AttendanceTimeService;
import com.bluewhite.personnel.roomboard.dao.CostLivingDao;
import com.bluewhite.personnel.roomboard.dao.MealDao;
import com.bluewhite.personnel.roomboard.dao.SingleMealDao;
import com.bluewhite.personnel.roomboard.entity.CostLiving;
import com.bluewhite.personnel.roomboard.entity.Meal;
import com.bluewhite.personnel.roomboard.entity.SingleMeal;
import com.bluewhite.production.group.dao.TemporarilyDao;
import com.bluewhite.production.group.entity.Temporarily;
import com.bluewhite.system.user.entity.TemporaryUser;
import com.bluewhite.system.user.entity.User;
import com.bluewhite.system.user.service.TemporaryUserService;
import com.bluewhite.system.user.service.UserService;

@Service
public class MealServiceImpl extends BaseServiceImpl<Meal, Long> implements MealService {
	@Autowired
	private MealDao dao;
	@Autowired
	private PersonVariableDao personVariableDao;
	@Autowired
	private UserService userService;
	@Autowired
	private AttendanceTimeService attendanceTimeService;
	@Autowired
	private SingleMealDao singleMealDao;
	@Autowired
	private WageDao wageDao;
	@Autowired
	private AttendanceInitDao attendanceInitDao;
	@Autowired
	private CostLivingDao costLivingDao;
	@Autowired
	private TemporarilyDao temporarilyDao;
	@Autowired
	private TemporaryUserService temporaryUserService;
	@Autowired
	private BaseDataDao baseDataDao;

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
				predicate.add(cb.equal(root.get("userName").as(String.class), param.getUserName()));
			}
			// 按部门查找
			if (!StringUtils.isEmpty(param.getOrgNameId())) {
				predicate.add(cb.equal(root.get("orgNameId").as(Long.class), param.getOrgNameId()));
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
				predicate.add(cb.equal(root.get("userName").as(String.class), param.getUserName()));
			}
			// 按部门查找
			if (!StringUtils.isEmpty(param.getOrgNameId())) {
				predicate.add(cb.equal(root.get("orgNameId").as(Long.class), param.getOrgNameId()));
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
		// 按报餐类型查找 找出每餐费用
		PersonVariable variable = personVariableDao.findByType(1);
		if (meal.getMode() == 1) {
			meal.setPrice(Double.valueOf(variable.getKeyValue()));
		}
		if (meal.getMode() == 2) {
			meal.setPrice(Double.valueOf(variable.getKeyValueTwo()));
		}
		if (meal.getMode() == 3) {
			meal.setPrice(Double.valueOf(variable.getKeyValueThree()));
		}
		if (meal.getMode() == 4) {
			meal.setPrice(Double.valueOf(variable.getKeyValue()));
		}
		String date = meal.getTime();
		String[] addDate = date.split("~");
		List<Date> dateList = DatesUtil.getPerDaysByStartAndEndDate(addDate[0], addDate[1], "yyyy-MM-dd");
		List<Meal> meals = new ArrayList<Meal>();
		for (Date date2 : dateList) {
			Meal meal2 = new Meal();
			meal2.setTradeDaysTime(date2);
			meal2.setPrice(meal.getPrice());
			meal2.setMode(meal.getMode());
			meal2.setType(1);
			if(meal.getUserId()!=null){
				User user = userService.findOne(meal.getUserId());
				meal2.setUserId(meal.getUserId());
				meal2.setUserName(user.getUserName());
				meal2.setOrgNameId(user.getOrgNameId());
			}
			if(meal.getTemporaryUserId()!=null){
				TemporaryUser temporaryUser = temporaryUserService.findOne(meal.getTemporaryUserId());
				meal2.setTemporaryUserId(meal.getTemporaryUserId());
				meal2.setUserName(temporaryUser.getUserName());
				meal2.setOrgNameId(meal.getOrgNameId());
			}
			meals.add(meal2);
		}
		dao.save(meals);

		return meal;
	}

	// 查询字典表中 报餐价格
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
		Calendar cal = Calendar.getInstance();
		cal.setTime(meal.getOrderTimeBegin());
		int month = cal.get(Calendar.MONTH);
		cal.setTime(meal.getOrderTimeEnd());
		int month2 = cal.get(Calendar.MONTH);
		if (month != month2) {
			throw new ServiceException("请选择同一月份时间");
		}
		List<Map<String, Object>> allList = new ArrayList<>();
		// 单向数据map
		Map<String, Object> allMap = null;
		double sum1 = 0;// 水费汇总
		double sum2 = 0;// 电费汇总
		double sum3 = 0;// 房租汇总
		double sum4 = 0;// 煤气汇总
		double sum5 = 0;// 上个月煤气汇总
		double sum6 = 0;// 上个月面料主食等汇总
		double sum9 = 0;// 人工工资总和
		double sum10 = 0;// 当月所有的报销物料
		Long day = DatesUtil.getDaySub(DatesUtil.getFirstDayOfMonth(meal.getOrderTimeBegin()),
				DatesUtil.getLastDayOfMonth(meal.getOrderTimeBegin()));// 当月天数
		// 蓝白总部
		Long siteTypeId = (long) 288;
		// 当月开始日期
		Date timeBegin = DatesUtil.getFirstDayOfMonth(meal.getOrderTimeBegin());
		// 当月结束日期
		Date timeEnd = DatesUtil.getLastDayOfMonth(meal.getOrderTimeBegin());
		// 水费
		CostLiving costLivingWater = costLivingDao.findByCostTypeIdAndSiteTypeIdAndBeginTimeAndEndTime((long) 290,
				siteTypeId, timeBegin, timeEnd);
		if (costLivingWater != null) {
			sum1 = NumUtils.mul(costLivingWater.getAverageCost(), day);
		} else {
			throw new ServiceException("未查询到当月水费，请先添加");
		}
		// 电费
		CostLiving costLivingElectricity = costLivingDao.findByCostTypeIdAndSiteTypeIdAndBeginTimeAndEndTime((long) 291,
				siteTypeId, timeBegin, timeEnd);
		if (costLivingElectricity != null) {
			sum2 = NumUtils.mul(costLivingElectricity.getAverageCost(), day);
		} else {
			throw new ServiceException("未查询到当月电费，请先添加");
		}
		// 房租
		CostLiving costLiving = costLivingDao.findByCostTypeIdAndSiteTypeIdAndBeginTimeAndEndTime((long) 289, siteTypeId,timeBegin, timeEnd);
		if (costLiving !=null) {
			// 总费用
			sum3 = NumUtils.mul(costLiving.getAverageCost(), day);
		} else {
			throw new ServiceException("未查询到当月房租，请先添加");
		}
		// 当月煤气
		CostLiving costLivingGas = costLivingDao.findByCostTypeIdAndSiteTypeIdAndBeginTimeAndEndTime((long) 292,
				siteTypeId, timeBegin, timeEnd);
		if (costLivingGas != null) {
			sum4 = NumUtils.mul(costLivingGas.getAverageCost(), day);
		} else {
			throw new ServiceException("未查询到当月煤气费，请先添加");
		}
		// 上月煤气
		CostLiving costLivingGasLast = costLivingDao.findByCostTypeIdAndSiteTypeIdAndBeginTimeAndEndTime((long) 292,
				siteTypeId, DatesUtil.getFristDayOfLastMonth(timeBegin), DatesUtil.getLastDayOLastMonth(timeEnd));
		if (costLivingGasLast != null) {
			sum5 = NumUtils.mul(costLivingGasLast.getAverageCost(), day);
		} else {
			throw new ServiceException("未查询到上月煤气费，请先添加");
		}

		List<SingleMeal> listSingleMeal = singleMealDao.findByTimeBetween(
				DatesUtil.getFristDayOfLastMonth(meal.getOrderTimeBegin()),
				DatesUtil.getLastDayOLastMonth(meal.getOrderTimeBegin()));// 上个月面料主食等汇总
		if (listSingleMeal.size() > 0) {
			for (SingleMeal singleMeal : listSingleMeal) {
				sum6 = sum6 + singleMeal.getPrice();
			}
		}
		double sum7 = NumUtils.sum(sum6, sum5);//// 上个月煤气汇总 +上个月面料主食等汇总
		PersonVariable personVariable1 = personVariableDao.findByType(6);
		double valA = NumUtils.mul(sum7, Double.parseDouble(personVariable1.getKeyValue()));// 采购员收入A
		List<SingleMeal> singleMeals = singleMealDao.findByTimeBetween(
				DatesUtil.getFirstDayOfMonth(meal.getOrderTimeBegin()),
				DatesUtil.getLastDayOfMonth(meal.getOrderTimeBegin()));// 当月所有报销物料
		if (singleMeals.size() > 0) {
			for (SingleMeal singleMeal : singleMeals) {
				sum10 = sum10 + singleMeal.getPrice();
			}
		}
		double valB = NumUtils.mul(sum10, Double.parseDouble(personVariable1.getKeyValue()));// 采购员收入B
		double valday = NumUtils.div((valA > valB ? valA : valB), day, 2);// 采购员当天收入
		PersonVariable personVariable2 = personVariableDao.findByType(5);
		double valPrice = NumUtils.mul(valday, Double.parseDouble(personVariable2.getKeyValue()));// 第一个含管理采购收入
		List<Wage> wage = wageDao.findByTypeAndTimeBetween((long) 281, timeBegin, timeEnd);
		if (wage.size() == 0) {
			throw new ServiceException("当月数据员工资未查询到");
		}
		boolean d1 = DatesUtil.belongCalendar(meal.getOrderTimeBegin());// 判断时冬令时
																		// 还是夏令时
		AttendanceInit init = attendanceInitDao.findByUserId(wage.get(0).getUserId());
		if (init == null) {
			throw new ServiceException("该员工未设定考勤初始化数据");
		}
		double val = 0;
		if (d1 == true) {
			val = init.getTurnWorkTimeSummer();
		} else {
			val = init.getTurnWorkTimeWinter();
		}
		double valwage = 0;// 物料跟进人员的工资
		if (wage.size() > 0) {
			valwage = wage.get(0).getWage();
		}
		double valPrice2 = NumUtils.mul(NumUtils.div(valwage, 25 * val, 2),
				Double.parseDouble(personVariable1.getKeyValueThree()));// 第二个含管理收入
		double valPrice3 = NumUtils.mul(NumUtils.sum(valPrice, valPrice2), day);// 每月

		List<Wage> wages = wageDao.findByTypeAndTimeBetween((long) 282,
				DatesUtil.getFristDayOfLastMonth(meal.getOrderTimeBegin()),
				DatesUtil.getLastDayOLastMonth(meal.getOrderTimeBegin()));
		if (wages.size() == 0) {
			throw new ServiceException("食堂上月工资未查询到");
		}
		if (wages.size() > 0) {
			for (Wage wage2 : wages) {
				sum9 = sum9 + wage2.getWage();// 人工工资总和
			}
		}
		List<Meal> list6 = dao.findByTradeDaysTimeBetween(DatesUtil.getFirstDayOfMonth(meal.getOrderTimeBegin()),
				DatesUtil.getLastDayOfMonth(meal.getOrderTimeBegin()));// 查询所有用餐次数
		if (list6.size() == 0) {
			throw new ServiceException("当月用餐次数未统计");
		}
		int size = list6.size();// 总用餐人次数
		double than = NumUtils.div(sum9, size, 2);// 人工比
		// 选择时间的内的餐数
		List<Meal> meals = dao.findByTradeDaysTimeBetween(meal.getOrderTimeBegin(), meal.getOrderTimeEnd());
		if (meals.size() == 0) {
			throw new ServiceException("选择时间内 没有用餐次数");
		}
		double merits = NumUtils.mul((double) meals.size(), than, (double) 0.5);// 人工绩效
		PersonVariable restType = personVariableDao.findByType(5);
		double water = NumUtils.mul(sum1, Double.parseDouble(restType.getKeyValue()));// 每月水费
		double electric = NumUtils.mul(sum2, Double.parseDouble(restType.getKeyValueTwo()));// 每月电费
		double rent = NumUtils.mul(sum3, Double.parseDouble(restType.getKeyValueThree()));// 每月房租费
		double coal = NumUtils.mul(sum4, Double.parseDouble(restType.getKeyValueThree()));// 每月煤气费
		double sumd = NumUtils.sum(water, electric, rent, coal, valPrice3, valwage, merits);// 所有费用汇总
		List<Double> listDouble2 = new ArrayList<>();
		List<Double> listDouble3 = new ArrayList<>();
		List<Double> listDouble4 = new ArrayList<>();
		List<Double> listDouble5 = new ArrayList<>();
		List<Double> listDouble6 = new ArrayList<>();
		List<Double> listDouble7 = new ArrayList<>();
		List<Double> listDouble8 = new ArrayList<>();
		double budget = 0;
		double budget2 = 0;
		double budget3 = 0;
		double budget4 = 0;
		double budget7 = 0;
		double budget8 = 0;
		double budget9 = 0;
		double budget10 = 0;
		double budget11 = 0;
		double budget12 = 0;
		double budget13 = 0;
		long l = meals.stream().filter(Meal -> Meal.getMode() != null && Meal.getMode().equals(1) && !Meal.getOrgNameId().equals((long) 1)).count();// 早餐数
		long q = meals.stream().filter(Meal -> Meal.getMode() != null && Meal.getMode().equals(2) && !Meal.getOrgNameId().equals((long) 1)).count();// 中餐数
		long w = meals.stream().filter(Meal -> Meal.getMode() != null && Meal.getMode().equals(3) && !Meal.getOrgNameId().equals((long) 1)).count();// 晚餐数
		long r = meals.stream().filter(Meal -> Meal.getMode() != null && Meal.getMode().equals(4) && !Meal.getOrgNameId().equals((long) 1)).count();// 夜宵数
		long t = meals.stream().filter(Meal -> Meal.getMode() != null && Meal.getMode().equals(1)
				&& Meal.getUserId() != null && Meal.getOrgNameId().equals((long) 1)).count();// 总经办早餐数
		long y = meals.stream().filter(Meal -> Meal.getMode() != null && Meal.getMode().equals(2)
				&& Meal.getUserId() != null && Meal.getOrgNameId().equals((long) 1)).count();// 总经办中数
		long u = meals.stream().filter(Meal -> Meal.getMode() != null && Meal.getMode().equals(3)
				&& Meal.getUserId() != null && Meal.getOrgNameId().equals((long) 1)).count();// 总经办晚餐数
		// 食材费用
		if ((meal.getOrgNameId() != null) && (meal.getOrgNameId().equals((long) 1))) {
			// 总经办
			List<SingleMeal> list = singleMealDao.findByTimeBetweenAndOrgNameId(meal.getOrderTimeBegin(),
					meal.getOrderTimeEnd(), (long) 1);
			if (list.size() == 0) {
				throw new ServiceException("选择时间内，没有添加食材记录，请先添加");
			}

			if (list.size() > 0) {
				list.stream().filter(SingleMeal -> SingleMeal.getType() != null && SingleMeal.getType().equals(1))
						.forEach(c -> {
							listDouble2.add(c.getPrice());
						});
				if (listDouble2.size() > 0) {
					budget = NumUtils.sum(listDouble2);// 早餐
				}
				list.stream().filter(SingleMeal -> SingleMeal.getType() != null && SingleMeal.getType().equals(2))
						.forEach(c -> {
							listDouble3.add(c.getPrice());
						});
				if (listDouble3.size() > 0) {
					budget2 = NumUtils.sum(listDouble3);// 午餐
				}
				list.stream().filter(SingleMeal -> SingleMeal.getType() != null && SingleMeal.getType().equals(3))
						.forEach(c -> {
							listDouble4.add(c.getPrice());
						});
				if (listDouble4.size() > 0) {
					budget3 = NumUtils.sum(listDouble4);// 晚餐
				}
				list.stream().filter(SingleMeal -> SingleMeal.getType() != null && SingleMeal.getType().equals(4))
						.forEach(c -> {
							listDouble5.add(c.getPrice());
						});
				if (listDouble5.size() > 0) {
					budget4 = NumUtils.sum(listDouble5);// 夜宵
				}

				list.stream().filter(SingleMeal -> SingleMeal.getType() != null && SingleMeal.getType().equals(5))
						.forEach(c -> {
							listDouble6.add(c.getPrice());
						});
				if (listDouble6.size() > 0) {
					double budget5 = NumUtils.sum(listDouble6);// 早中晚
					long m = t + y + u;
					budget7 = NumUtils.mul(budget5, NumUtils.div(t, m, 2));// 早餐钱
					budget8 = NumUtils.mul(budget5, NumUtils.div(y, m, 2));// 中餐钱
					budget9 = NumUtils.mul(budget5, NumUtils.div(u, m, 2));// 晚餐钱
				}

				list.stream().filter(SingleMeal -> SingleMeal.getType() != null && SingleMeal.getType().equals(6))
						.forEach(c -> {
							listDouble7.add(c.getPrice());
						});
				if (listDouble7.size() > 0) {
					double budget6 = NumUtils.sum(listDouble7);// 中晚
					long m = y + u;
					budget10 = NumUtils.mul(budget6, NumUtils.div(y, m, 2));// 中餐钱
					budget11 = NumUtils.mul(budget6, NumUtils.div(u, m, 2));// 晚餐钱
				}

				list.stream().filter(SingleMeal -> SingleMeal.getType() != null && SingleMeal.getType().equals(7))
						.forEach(c -> {
							listDouble8.add(c.getPrice());
						});
				if (listDouble8.size() > 0) {
					double budget81 = NumUtils.sum(listDouble8);// 早晚
					long m = t + u;
					budget12 = NumUtils.mul(budget81, NumUtils.div(t, m, 2));// 早餐钱
					budget13 = NumUtils.mul(budget81, NumUtils.div(u, m, 2));// 晚餐钱
				}

			}
		} else {
			List<SingleMeal> list2 = singleMealDao.findByTimeBetween(meal.getOrderTimeBegin(), meal.getOrderTimeEnd());
			List<SingleMeal> list1 = list2.stream().filter(SingleMeal -> SingleMeal.getOrgNameId() == null)
					.collect(Collectors.toList());
			if (list1.size() == 0) {
				throw new ServiceException("选择时间内，没有添加食材记录，请先添加");
			}
			if (list1.size() > 0) {
				list1.stream().filter(SingleMeal -> SingleMeal.getType() != null && SingleMeal.getType().equals(1))
						.forEach(c -> {
							listDouble2.add(c.getPrice());
						});
				if (listDouble2.size() > 0) {
					budget = NumUtils.sum(listDouble2);// 早餐
				}
				list1.stream().filter(SingleMeal -> SingleMeal.getType() != null && SingleMeal.getType().equals(2))
						.forEach(c -> {
							listDouble3.add(c.getPrice());
						});
				if (listDouble3.size() > 0) {
					budget2 = NumUtils.sum(listDouble3);// 午餐
				}
				list1.stream().filter(SingleMeal -> SingleMeal.getType() != null && SingleMeal.getType().equals(3))
						.forEach(c -> {
							listDouble4.add(c.getPrice());
						});
				if (listDouble4.size() > 0) {
					budget3 = NumUtils.sum(listDouble4);// 晚餐
				}
				list1.stream().filter(SingleMeal -> SingleMeal.getType() != null && SingleMeal.getType().equals(4))
						.forEach(c -> {
							listDouble5.add(c.getPrice());
						});
				if (listDouble5.size() > 0) {
					budget4 = NumUtils.sum(listDouble5);// 夜宵
				}

				list1.stream().filter(SingleMeal -> SingleMeal.getType() != null && SingleMeal.getType().equals(5))
						.forEach(c -> {
							listDouble6.add(c.getPrice());
						});
				if (listDouble6.size() > 0) {
					double budget5 = NumUtils.sum(listDouble6);// 早中晚
					long m = l + q + w;
					budget7 = NumUtils.mul(budget5, NumUtils.div(l, m, 2));// 早餐钱
					budget8 = NumUtils.mul(budget5, NumUtils.div(q, m, 2));// 中餐钱
					budget9 = NumUtils.mul(budget5, NumUtils.div(w, m, 2));// 晚餐钱
				}

				list1.stream().filter(SingleMeal -> SingleMeal.getType() != null && SingleMeal.getType().equals(6))
						.forEach(c -> {
							listDouble7.add(c.getPrice());
						});
				if (listDouble7.size() > 0) {
					double budget6 = NumUtils.sum(listDouble7);// 中晚
					long m = q + w;
					budget10 = NumUtils.mul(budget6, NumUtils.div(q, m, 2));// 中餐钱
					budget11 = NumUtils.mul(budget6, NumUtils.div(w, m, 2));// 晚餐钱
				}

				list1.stream().filter(SingleMeal -> SingleMeal.getType() != null && SingleMeal.getType().equals(7))
						.forEach(c -> {
							listDouble8.add(c.getPrice());
						});
				if (listDouble8.size() > 0) {
					double budget71 = NumUtils.sum(listDouble8);// 早晚
					long m = l + w;
					budget12 = NumUtils.mul(budget71, NumUtils.div(l, m, 2));// 早餐钱
					budget13 = NumUtils.mul(budget71, NumUtils.div(w, m, 2));// 晚餐钱
				}

			}
		}
		double f = NumUtils.sum(budget, budget7, budget12);// 早餐 食材
		double z = NumUtils.sum(budget2, budget8, budget10);// 中餐 食材
		double x = NumUtils.sum(budget3, budget9, budget11, budget13);// 晚餐 食材
		double c = NumUtils.sum(budget4);// 夜宵 食材

		double q1 = NumUtils.div(sumd, meals.size(), 2);// 水电早餐的平均价格
		long a1 = l;
		long a2 = q;
		long a3 = w;
		long a4 = r;
		if ((meal.getOrgNameId() != null) && (meal.getOrgNameId().equals((long) 1))) {
			a1 = t;
			a2 = y;
			a3 = u;
			a4 = 1;
		}
		double g = NumUtils.sum(NumUtils.division(NumUtils.div(f, a1 == 0 ? 1 : a1, 2)), q1); // 早餐平均
		double i = NumUtils.sum(NumUtils.division(NumUtils.div(z, a2 == 0 ? 1 : a2, 2)), q1);// 中餐平均
		double n = NumUtils.sum(NumUtils.division(NumUtils.div(x, a3 == 0 ? 1 : a3, 2)), q1);// 晚餐平均
		double h = NumUtils.sum(NumUtils.division(NumUtils.div(c, a4 == 0 ? 1 : a4, 2)), q1);// 夜宵平均

		// 获取用餐记录
		List<Meal> mealsList = findMeal(meal);
		Map<Long, List<Meal>> mealMap = mealsList.stream().filter(Meal -> Meal.getUserId() != null)
				.collect(Collectors.groupingBy(Meal::getUserId, Collectors.toList()));
		for (Long ps1 : mealMap.keySet()) {
			allMap = new HashMap<>();
			// 获取单一员工日期区间所有的报餐数据
			List<Meal> psList1 = mealMap.get(ps1);
			if(meal.getOrgNameId()==null && psList1.get(0).getOrgNameId().equals((long) 1)){
				
			}else{
			double modeOne = psList1.stream().filter(Meal -> Meal.getMode() == 1).count();
			double modeTwo = psList1.stream().filter(Meal -> Meal.getMode() == 2).count();
			double modeThree = psList1.stream().filter(Meal -> Meal.getMode() == 3).count();
			double modeFour = psList1.stream().filter(Meal -> Meal.getMode() == 4).count();
			double modeOnePrice = NumUtils.mul(modeOne, g);
			double modeTwoPrice = NumUtils.mul(modeTwo, i);
			double modeThreePrice = NumUtils.mul(modeThree, n);
			double modeFourPrice = NumUtils.mul(modeFour, h);
			double sumPrice = NumUtils.sum(modeOnePrice, modeTwoPrice, modeThreePrice, modeFourPrice);
			BaseData org = baseDataDao.findOne(psList1.get(0).getOrgNameId());
			allMap.put("username", psList1.get(0).getUserName());
			allMap.put("orgName", org.getName());
			allMap.put("modeOne", modeOne);
			allMap.put("modeTwo", modeTwo);
			allMap.put("modeThree", modeThree);
			allMap.put("modeFour", modeFour);
			allMap.put("modeOnePrice", modeOnePrice);
			allMap.put("modeTwoPrice", modeTwoPrice);
			allMap.put("modeThreePrice", modeThreePrice);
			allMap.put("modeFourPrice", modeFourPrice);
			allMap.put("modeOneVal", g);
			allMap.put("modeTwoVal", i);
			allMap.put("modeThreeVal", n);
			allMap.put("modeFourVal", h);
			allMap.put("sumPrice", sumPrice);
			allList.add(allMap);
			}
		}

		Map<Long, List<Meal>> mealMapUser = mealsList.stream().filter(Meal -> Meal.getTemporaryUserId() != null)
				.collect(Collectors.groupingBy(Meal::getTemporaryUserId, Collectors.toList()));
		for (Long ps1 : mealMapUser.keySet()) {
			allMap = new HashMap<>();
			// 获取单一员工日期区间所有的报餐数据
			List<Meal> psList1 = mealMapUser.get(ps1);
			double modeOne = psList1.stream().filter(Meal -> Meal.getMode() == 1).count();
			double modeTwo = psList1.stream().filter(Meal -> Meal.getMode() == 2).count();
			double modeThree = psList1.stream().filter(Meal -> Meal.getMode() == 3).count();
			double modeFour = psList1.stream().filter(Meal -> Meal.getMode() == 4).count();
			double modeOnePrice = NumUtils.mul(modeOne, g);
			double modeTwoPrice = NumUtils.mul(modeTwo, i);
			double modeThreePrice = NumUtils.mul(modeThree, n);
			double modeFourPrice = NumUtils.mul(modeFour, h);
			double sumPrice = NumUtils.sum(modeOnePrice, modeTwoPrice, modeThreePrice, modeFourPrice);
			BaseData org = baseDataDao.findOne(psList1.get(0).getOrgNameId());
			allMap.put("username", psList1.get(0).getUserName());
			allMap.put("orgName", org.getName());
			allMap.put("modeOne", modeOne);
			allMap.put("modeTwo", modeTwo);
			allMap.put("modeThree", modeThree);
			allMap.put("modeFour", modeFour);
			allMap.put("modeOnePrice", modeOnePrice);
			allMap.put("modeTwoPrice", modeTwoPrice);
			allMap.put("modeThreePrice", modeThreePrice);
			allMap.put("modeFourPrice", modeFourPrice);
			allMap.put("modeOneVal", g);
			allMap.put("modeTwoVal", i);
			allMap.put("modeThreeVal", n);
			allMap.put("modeFourVal", h);
			allMap.put("sumPrice", sumPrice);
			allList.add(allMap);
		}

		return allList;
	}

	// 同步吃饭记录
	// 根据打卡记录进行是否有早中晚餐记录
	@Override
	@Transactional
	public int initMeal(AttendanceTime attendanceTime) throws ParseException {
		// 检查当前月份属于夏令时或冬令时 flag=ture 为夏令时
		boolean flag = DatesUtil.belongCalendar(attendanceTime.getOrderTimeBegin());
		Date startDate = DatesUtil.getFirstDayOfMonth(attendanceTime.getOrderTimeBegin());
		Date endDate = DatesUtil.getLastDayOfMonth(attendanceTime.getOrderTimeBegin());
		List<AttendanceTime> attendanceTimes = attendanceTimeService.findAttendanceTime(attendanceTime);
		List<Meal> list = dao.findByTypeAndTradeDaysTimeBetween(2, startDate, endDate);
		if (list.size() > 0) {
			List<Long> idLong = list.stream().map(Meal::getId).collect(Collectors.toList());
			// 同步删除自动添加的报餐数据
			dao.deleteList(idLong);
		}
		List<Meal> meals = new ArrayList<Meal>();
		// 4=设定早中晚三餐对于吃饭统计而延迟的分钟数
		PersonVariable lagMin = personVariableDao.findByType(4);

		for (AttendanceTime at : attendanceTimes) {
			AttendanceInit attendanceInit = at.getAttendanceInit();
			// 第一天的开始签到时间从6点开始新一天的签到
			Date beginTimes = at.getTime();
			// 上班开始时间
			Date workTime = null;
			// 上班结束时间
			Date workTimeEnd = null;
			// 中午休息开始时间
			Date restBeginTime = null;
			// flag=ture 为夏令时
			if (flag) {
				String[] workTimeArr = attendanceInit.getWorkTimeSummer().split(" - ");
				// 将 工作间隔开始结束时间转换成当前日期的时间
				workTime = DatesUtil.dayTime(beginTimes, workTimeArr[0]);
				workTimeEnd = DatesUtil.dayTime(beginTimes, workTimeArr[1]);
				String[] restTimeArr = attendanceInit.getRestTimeSummer().split(" - ");
				// 将 休息间隔开始结束时间转换成当前日期的时间
				restBeginTime = DatesUtil.dayTime(beginTimes, restTimeArr[0]);
			} else {
				// 冬令时
				String[] workTimeArr = attendanceInit.getWorkTimeWinter().split(" - ");
				// 将 工作间隔开始结束时间转换成当前日期的时间
				workTime = DatesUtil.dayTime(beginTimes, workTimeArr[0]);
				workTimeEnd = DatesUtil.dayTime(beginTimes, workTimeArr[1]);
				// 将 休息间隔开始结束时间转换成当前日期的时间
				String[] restTimeArr = attendanceInit.getRestTimeWinter().split(" - ");
				// 将 休息间隔开始结束时间转换成当前日期的时间
				restBeginTime = DatesUtil.dayTime(beginTimes, restTimeArr[0]);
			}
			// 早餐延迟后时间
			Date breakfastLagTime = DatesUtil.getDaySum(workTime, Double.parseDouble(lagMin.getKeyValue()));
			// 午餐延迟后时间
			Date lunchLagTime = DatesUtil.getDaySum(restBeginTime, Double.parseDouble(lagMin.getKeyValueTwo()));
			// 晚餐延迟时间
			Date dinnerLagTime = DatesUtil.getDaySum(workTimeEnd, Double.parseDouble(lagMin.getKeyValueThree()));
			// 夜宵时间
			Date midnight = DatesUtil.dayTime(at.getTime(), "23:00:00");

			// 考勤正常，有签入签出
			if (at.getCheckIn() != null && at.getCheckOut() != null
					&& DatesUtil.getTimeSec(at.getCheckIn(), at.getCheckOut()) > 300) {
				// 签入时间小于早餐延迟时间
				if ((attendanceInit.getEatType() != null && (attendanceInit.getEatType() == 1
						|| attendanceInit.getEatType() == 3 || attendanceInit.getEatType() == 5))
						&& at.getCheckIn().compareTo(breakfastLagTime) != 1) {
					meals.add(addMeal(at, 1));
				}
				// 1.签出时间大于午餐延迟时间
				if (at.getCheckOut().compareTo(lunchLagTime) != -1) {
					meals.add(addMeal(at, 2));
				}
				// 1.签入时间小于晚餐延迟时间，2签出时间大于晚餐延迟时间
				if ((attendanceInit.getEatType() != null
						&& (attendanceInit.getEatType() == 2 || attendanceInit.getEatType() == 3
								|| attendanceInit.getEatType() == 4 || attendanceInit.getEatType() == 5))
						&& (at.getCheckIn().compareTo(dinnerLagTime) != 1
								&& at.getCheckOut().compareTo(dinnerLagTime) != -1)) {
					meals.add(addMeal(at, 3));
				}
				// 1签出时间大于夜宵时间
				if ((attendanceInit.getEatType() != null
						&& (attendanceInit.getEatType() == 4 || attendanceInit.getEatType() == 5))
						&& at.getCheckOut().compareTo(midnight) != -1) {
					meals.add(addMeal(at, 4));
				}
			}

			// 考勤异常，只有签入
			if ((at.getCheckIn() != null && at.getCheckOut() == null) || (at.getCheckIn() != null
					&& at.getCheckOut() != null && DatesUtil.getTimeSec(at.getCheckIn(), at.getCheckOut()) <= 300)) {
				if (attendanceInit.getEatType() != null && (attendanceInit.getEatType() == 1
						|| attendanceInit.getEatType() == 3 || attendanceInit.getEatType() == 5)) {
					meals.add(addMeal(at, 1));
				}
				meals.add(addMeal(at, 2));
				if (attendanceInit.getEatType() != null
						&& (attendanceInit.getEatType() == 2 || attendanceInit.getEatType() == 3
								|| attendanceInit.getEatType() == 4 || attendanceInit.getEatType() == 5)) {
					meals.add(addMeal(at, 3));
				}
				// 1.签入时间大于夜宵时间
				if ((attendanceInit.getEatType() != null
						&& (attendanceInit.getEatType() == 4 || attendanceInit.getEatType() == 5))
						&& at.getCheckIn().compareTo(midnight) != -1) {
					meals.add(addMeal(at, 4));
				}
			}

			// 无签到记录
			if (at.getCheckIn() == null && at.getCheckOut() == null) {
				// 不需要打卡
				if (attendanceInit.getWorkType() == 1 || attendanceInit.getWorkType() == 2) {
					if (at.getFlag() != 3) {
						meals.add(addMeal(at, 2));
					}
				}
			}
		}
		// 手动处理临时人员的打卡吃饭次数，获取车间手动填写的特急人员考勤
		List<Temporarily> temporarilyList = temporarilyDao.findByTemporarilyDateBetween(startDate, endDate);
		// 按日期排序
		temporarilyList = temporarilyList.stream().sorted(Comparator.comparing(Temporarily::getTemporarilyDate))
				.filter(Temporarily -> Temporarily.getTemporaryUserId() != null).collect(Collectors.toList());
		for (Temporarily t : temporarilyList) {
			// 中餐
			if (t.getWorkTime() > 4.5) {
				// 中晚餐
				if (t.getWorkTime() > 9) {
					// 中晚夜宵
					if (t.getWorkTime() > 11) {
						meals.add(addMealTemporary(t.getTemporarilyDate(), t, 2));
						meals.add(addMealTemporary(t.getTemporarilyDate(), t, 3));
						meals.add(addMealTemporary(t.getTemporarilyDate(), t, 4));
						continue;
					}
					meals.add(addMealTemporary(t.getTemporarilyDate(), t, 2));
					meals.add(addMealTemporary(t.getTemporarilyDate(), t, 3));
					continue;
				}
				meals.add(addMealTemporary(t.getTemporarilyDate(), t, 2));
				continue;
			}
		}
		batchSave(meals);
		return meals.size();
	}

	/**
	 * 添加报餐记录
	 * 
	 * @param attendanceTime
	 * @param mode
	 * @param variable
	 * @return
	 */
	private Meal addMeal(AttendanceTime attendanceTime, Integer mode) {
		Meal meal = new Meal();
		meal.setTradeDaysTime(attendanceTime.getTime());
		meal.setUserId(attendanceTime.getUserId());
		meal.setUserName(attendanceTime.getUserName());
		User user = userService.findOne(attendanceTime.getUserId());
		meal.setOrgNameId(user.getOrgNameId());
		meal.setType(2);
		meal.setMode(mode);
		return meal;
	}

	/**
	 * 添加报餐记录
	 * 
	 * @param attendanceTime
	 * @param mode
	 * @param variable
	 * @return
	 */
	private Meal addMealTemporary(Date time, Temporarily temporarily, Integer mode) {
		Meal meal = new Meal();
		meal.setTradeDaysTime(time);
		meal.setTemporaryUserId(temporarily.getTemporaryUserId());
		meal.setUserName(temporarily.getTemporaryUser().getUserName());
		Long temporaryUserOrgId = null;
		switch (temporarily.getType()) {
		case 1:
			temporaryUserOrgId = (long) 48;
			break;
		case 2:
			temporaryUserOrgId = (long) 79;
			break;
		case 3:
			temporaryUserOrgId = (long) 84;
			break;
		}
		meal.setOrgNameId(temporaryUserOrgId);
		meal.setType(2);
		meal.setMode(mode);
		return meal;
	}

	@Override
	public List<Map<String, Object>> findWage(Meal meal) {
		List<Map<String, Object>> allList = new ArrayList<>();
		// 单向数据map
		Map<String, Object> allMap = new HashMap<>();
		double sum1 = 0;// 水费汇总
		double sum2 = 0;// 电费汇总
		double sum3 = 0;// 房租汇总
		double sum4 = 0;// 煤气汇总
		double sum5 = 0;// 上个月煤气汇总
		double sum6 = 0;// 上个月面料主食等汇总
		double sum9 = 0;// 人工工资总和
		double sum10 = 0;// 当月所有的报销物料
		Long day = DatesUtil.getDaySub(DatesUtil.getFirstDayOfMonth(meal.getOrderTimeBegin()),
				DatesUtil.getLastDayOfMonth(meal.getOrderTimeBegin()));// 当月天数
		// 蓝白总部
		Long siteTypeId = (long) 288;
		// 当月开始日期
		Date timeBegin = DatesUtil.getFirstDayOfMonth(meal.getOrderTimeBegin());
		// 当月结束日期
		Date timeEnd = DatesUtil.getLastDayOfMonth(meal.getOrderTimeBegin());
		// 水费
		CostLiving costLivingWater = costLivingDao.findByCostTypeIdAndSiteTypeIdAndBeginTimeAndEndTime((long) 290,
				siteTypeId, timeBegin, timeEnd);
		if (costLivingWater != null) {
			sum1 = NumUtils.mul(costLivingWater.getAverageCost(), day);
		} else {
			throw new ServiceException("未查询到当月水费，请先添加");
		}
		// 电费
		CostLiving costLivingElectricity = costLivingDao.findByCostTypeIdAndSiteTypeIdAndBeginTimeAndEndTime((long) 291,
				siteTypeId, timeBegin, timeEnd);
		if (costLivingElectricity != null) {
			sum2 = NumUtils.mul(costLivingElectricity.getAverageCost(), day);
		} else {
			throw new ServiceException("未查询到当月电费，请先添加");
		}
		// 房租
		CostLiving costLiving = costLivingDao.findByCostTypeIdAndSiteTypeIdAndBeginTimeAndEndTime((long) 289, siteTypeId,timeBegin, timeEnd);
		if (costLiving!=null) {
			// 总费用
			sum3 = NumUtils.mul(costLiving.getAverageCost(), day);
		} else {
			throw new ServiceException("未查询到当月房租，请先添加");
		}
		// 当月煤气
		CostLiving costLivingGas = costLivingDao.findByCostTypeIdAndSiteTypeIdAndBeginTimeAndEndTime((long) 292,
				siteTypeId, timeBegin, timeEnd);
		if (costLivingGas != null) {
			sum4 = NumUtils.mul(costLivingGas.getAverageCost(), day);
		} else {
			throw new ServiceException("未查询到当月煤气费，请先添加");
		}
		// 上月煤气
		CostLiving costLivingGasLast = costLivingDao.findByCostTypeIdAndSiteTypeIdAndBeginTimeAndEndTime((long) 292,
				siteTypeId, DatesUtil.getFristDayOfLastMonth(timeBegin), DatesUtil.getLastDayOLastMonth(timeEnd));
		if (costLivingGasLast != null) {
			sum5 = NumUtils.mul(costLivingGasLast.getAverageCost(), day);
		} else {
			throw new ServiceException("未查询到上月煤气费，请先添加");
		}

		List<SingleMeal> listSingleMeal = singleMealDao.findByTimeBetween(
				DatesUtil.getFristDayOfLastMonth(meal.getOrderTimeBegin()),
				DatesUtil.getLastDayOLastMonth(meal.getOrderTimeBegin()));// 上个月面料主食等汇总
		if (listSingleMeal.size() > 0) {
			for (SingleMeal singleMeal : listSingleMeal) {
				sum6 = sum6 + singleMeal.getPrice();
			}
		}
		double sum7 = NumUtils.sum(sum6, sum5);//// 上个月煤气汇总 +上个月面料主食等汇总
		PersonVariable personVariable1 = personVariableDao.findByType(6);
		double valA = NumUtils.mul(sum7, Double.parseDouble(personVariable1.getKeyValue()));// 采购员收入A
		List<SingleMeal> singleMeals = singleMealDao.findByTimeBetween(
				DatesUtil.getFirstDayOfMonth(meal.getOrderTimeBegin()),
				DatesUtil.getLastDayOfMonth(meal.getOrderTimeBegin()));// 当月所有报销物料
		if (singleMeals.size() > 0) {
			for (SingleMeal singleMeal : singleMeals) {
				sum10 = sum10 + singleMeal.getPrice();
			}
		}
		double valB = NumUtils.mul(sum10, Double.parseDouble(personVariable1.getKeyValue()));// 采购员收入B
		double valday = NumUtils.div((valA > valB ? valA : valB), day, 2);// 采购员当天收入
		PersonVariable personVariable2 = personVariableDao.findByType(5);
		double valPrice = NumUtils.mul(valday, Double.parseDouble(personVariable2.getKeyValue()));// 第一个含管理采购收入
		List<Wage> wage = wageDao.findByTypeAndTimeBetween((long) 281,
				DatesUtil.getFirstDayOfMonth(meal.getOrderTimeBegin()),
				DatesUtil.getLastDayOfMonth(meal.getOrderTimeBegin()));
		if (wage.size() == 0) {
			throw new ServiceException("当月数据员工资未查询到");
		}
		boolean d1 = DatesUtil.belongCalendar(meal.getOrderTimeBegin());// 判断时冬令时
																		// 还是夏令时
		AttendanceInit init = attendanceInitDao.findByUserId(wage.get(0).getUserId());
		if (init == null) {
			throw new ServiceException("该员工未设定考勤初始化数据");
		}
		double val = 0;
		if (d1 == true) {
			val = init.getTurnWorkTimeSummer();
		} else {
			val = init.getTurnWorkTimeWinter();
		}

		double valwage = 0;// 物料跟进人员的工资
		if (wage.size() > 0) {
			valwage = wage.get(0).getWage();
		}
		double valPrice2 = NumUtils.mul(NumUtils.div(valwage, 25 * val, 2),
				Double.parseDouble(personVariable1.getKeyValueThree()));// 第二个含管理收入
		double valPrice3 = NumUtils.sum(valPrice, valPrice2);// 物料采购和数据跟进费
		double valPrice4 = NumUtils.div(valwage, day, 2);// 人工工资
		List<Wage> wages = wageDao.findByTypeAndTimeBetween((long) 282,
				DatesUtil.getFristDayOfLastMonth(meal.getOrderTimeBegin()),
				DatesUtil.getLastDayOLastMonth(meal.getOrderTimeBegin()));
		if (wages.size() == 0) {
			throw new ServiceException("食堂上月工资未查询到");
		}
		if (wages.size() > 0) {
			for (Wage wage2 : wages) {
				sum9 = sum9 + wage2.getWage();// 人工工资总和
			}
		}
		List<Meal> list6 = dao.findByTradeDaysTimeBetween(DatesUtil.getFirstDayOfMonth(meal.getOrderTimeBegin()),
				DatesUtil.getLastDayOfMonth(meal.getOrderTimeBegin()));// 查询所有用餐次数
		if (list6.size() == 0) {
			throw new ServiceException("当月用餐次数未统计");
		}
		int size = list6.size();// 总用餐人次数
		double than = NumUtils.div(sum9, size, 2);// 人工比
		List<Meal> meals = dao.findByTradeDaysTimeBetween(meal.getOrderTimeBegin(), meal.getOrderTimeEnd());// 选择时间的内的
																											// 餐数
		if (meals.size() == 0) {
			throw new ServiceException("选择时间内 没有用餐次数");
		}
		double merits = NumUtils.mul((double) meals.size(), than, 0.5);// 人工绩效
		PersonVariable restType = personVariableDao.findByType(5);
		double water = NumUtils.div(NumUtils.mul(sum1, Double.parseDouble(restType.getKeyValue())), day, 2);// 每天水费
		double electric = NumUtils.div(NumUtils.mul(sum2, Double.parseDouble(restType.getKeyValueThree())), day, 2);// 每天电费
		double rent = NumUtils.div(NumUtils.mul(sum3, Double.parseDouble(restType.getKeyValueTwo())), day, 2);// 每天房租费
		double coal = NumUtils.div(NumUtils.mul(sum4, Double.parseDouble(restType.getKeyValueTwo())), day, 2);// 每天煤气费
		double budget = 0;
		List<SingleMeal> list1 = singleMealDao.findByTimeBetween(meal.getOrderTimeBegin(), meal.getOrderTimeEnd());
		if (list1.size() == 0) {
			throw new ServiceException("选择时间内，没有添加食材记录，请先添加");
		}
		if (list1.size() > 0) {
			for (SingleMeal singleMeal : list1) {
				budget = budget + singleMeal.getPrice();
			}
		}
		double sum = NumUtils.sum(water, electric, rent, coal, valPrice3, valPrice4, merits, budget);// 总数
		double sumPrice = NumUtils.div(sum, meals.size(), 2);
		allMap.put("rent", rent);
		allMap.put("water", water);
		allMap.put("electric", electric);
		allMap.put("coal", coal);
		allMap.put("valPrice3", valPrice3);
		allMap.put("valPrice4", valPrice4);
		allMap.put("merits", merits);
		allMap.put("size", meals.size());
		allMap.put("sumPrice", sumPrice);
		allList.add(allMap);
		return allList;
	}

	@Override
	public List<Map<String, Object>> findElectric(Meal meal) {
		List<Map<String, Object>> allList = new ArrayList<>();
		// 蓝白总部
		Long siteTypeId = (long) 288;
		PersonVariable restType = personVariableDao.findByType(5);
		// 当月开始日期
		Date timeBegin = DatesUtil.getFirstDayOfMonth(meal.getOrderTimeBegin());
		// 当月结束日期
		Date timeEnd = DatesUtil.getLastDayOfMonth(meal.getOrderTimeBegin());
		// 水费
		CostLiving costLivingWater = costLivingDao.findByCostTypeIdAndSiteTypeIdAndBeginTimeAndEndTime((long) 290,
				siteTypeId, timeBegin, timeEnd);
		// 电费
		CostLiving costLivingElectricity = costLivingDao.findByCostTypeIdAndSiteTypeIdAndBeginTimeAndEndTime((long) 291,
				siteTypeId, timeBegin, timeEnd);
		// 房租
		CostLiving costLiving = costLivingDao.findByCostTypeIdAndSiteTypeIdAndBeginTimeAndEndTime((long) 289, siteTypeId,timeBegin, timeEnd);
		if (costLiving != null) {
			Map<String, Object> allMap = new HashMap<>();
			// 总费用
			allMap.put("name", "房租");
			allMap.put("modify", "keyValueThree");
			allMap.put("sum1", costLiving.getTotalCost());
			allMap.put("valPrice1", restType.getKeyValueThree());
			allMap.put("val", NumUtils.mul(costLiving.getTotalCost(), Double.parseDouble(restType.getKeyValueThree())));
			allMap.put("sumday1", costLiving.getAverageCost());
			allList.add(allMap);
		}
		if (costLivingWater != null) {
			Map<String, Object> allMap = new HashMap<>();
			allMap.put("name", "水费");
			allMap.put("modify", "keyValue");
			allMap.put("sum1", costLivingWater.getTotalCost());
			allMap.put("valPrice1", restType.getKeyValue());
			allMap.put("val", NumUtils.mul(costLivingWater.getTotalCost(), Double.parseDouble(restType.getKeyValue())));
			allMap.put("sumday1", costLivingWater.getAverageCost());
			allList.add(allMap);
		}
		if (costLivingElectricity != null) {
			Map<String, Object> allMap = new HashMap<>();
			allMap.put("name", "电费");
			allMap.put("modify", "keyValueTwo");
			allMap.put("sum1", costLivingElectricity.getTotalCost());
			allMap.put("valPrice1", restType.getKeyValueTwo());
			allMap.put("val",
					NumUtils.mul(costLivingElectricity.getTotalCost(), Double.parseDouble(restType.getKeyValueTwo())));
			allMap.put("sumday1", costLivingElectricity.getAverageCost());
			allList.add(allMap);
		}
		return allList;
	}
}
