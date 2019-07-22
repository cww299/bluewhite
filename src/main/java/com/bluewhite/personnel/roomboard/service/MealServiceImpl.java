package com.bluewhite.personnel.roomboard.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.personnel.attendance.dao.PersonVariableDao;
import com.bluewhite.personnel.attendance.entity.AttendanceInit;
import com.bluewhite.personnel.attendance.entity.AttendanceTime;
import com.bluewhite.personnel.attendance.entity.PersonVariable;
import com.bluewhite.personnel.attendance.service.AttendanceInitService;
import com.bluewhite.personnel.attendance.service.AttendanceTimeService;
import com.bluewhite.personnel.roomboard.dao.MealDao;
import com.bluewhite.personnel.roomboard.entity.Meal;
import com.bluewhite.system.user.entity.User;
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
	private AttendanceInitService attendanceInitService;
	@PersistenceContext
	protected EntityManager entityManager;

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
		if (meal.getId() == null) {
			String date = meal.getTime();
			String[] addDate = date.split("~");
			List<Date> dateList = DatesUtil.getPerDaysByStartAndEndDate(addDate[0], addDate[1], "yyyy-MM-dd");
			List<Meal> meals = new ArrayList<Meal>();
			for (Date date2 : dateList) {
				Meal meal2 = new Meal();
				meal2.setTradeDaysTime(date2);
				meal2.setPrice(meal.getPrice());
				meal2.setMode(meal.getMode());
				meal2.setUserName(meal.getUserName());
				meal2.setUserId(meal.getUserId());
				meals.add(meal2);
			}
			dao.save(meals);
		} else {
			dao.save(meal);
		}
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
		List<Map<String, Object>> allList = new ArrayList<>();
		// 单向数据map
		Map<String, Object> allMap = null;
		List<Meal> mealsList = findMeal(meal);
		Map<Long, List<Meal>> mealMap = mealsList.stream().filter(Meal -> Meal.getUserId() != null)
				.collect(Collectors.groupingBy(Meal::getUserId, Collectors.toList()));
		for (Long ps1 : mealMap.keySet()) {
			allMap = new HashMap<>();
			// 获取单一员工日期区间所有的报餐数据
			List<Meal> psList1 = mealMap.get(ps1);
			List<Double> listDouble = new ArrayList<>();
			psList1.stream().filter(Meal -> Meal.getUserId().equals(Meal.getUserId())).forEach(c -> {
				listDouble.add(c.getPrice());
			});
			double budget = NumUtils.sum(listDouble);
			double modeOne = psList1.stream()
					.filter(Meal -> Meal.getUserId().equals(Meal.getUserId()) && Meal.getMode() == 1).count();
			double modeTwo = psList1.stream()
					.filter(Meal -> Meal.getUserId().equals(Meal.getUserId()) && Meal.getMode() == 2).count();
			double modeThree = psList1.stream()
					.filter(Meal -> Meal.getUserId().equals(Meal.getUserId()) && Meal.getMode() == 3).count();
			double modeFour = psList1.stream()
					.filter(Meal -> Meal.getUserId().equals(Meal.getUserId()) && Meal.getMode() == 4).count();
			User user = userService.findOne(ps1);
			String aString = user.getUserName();
			String org = user.getOrgName().getName();
			allMap.put("username", aString);
			allMap.put("money", budget);
			allMap.put("orgName", org);
			allMap.put("modeOne", modeOne);
			allMap.put("modeTwo", modeTwo);
			allMap.put("modeThree", modeThree);
			allMap.put("modeFour", modeFour);
			allList.add(allMap);
		}

		return allList;
	}

	// 同步吃饭记录
	// 根据打卡记录进行是否有早中晚餐记录
	@Override
	@Transactional
	public int InitMeal(AttendanceTime attendanceTime) throws ParseException {
		// 检查当前月份属于夏令时或冬令时 flag=ture 为夏令时
		boolean flag = DatesUtil.belongCalendar(attendanceTime.getOrderTimeBegin());

		List<AttendanceTime> attendanceTimes = attendanceTimeService.findAttendanceTime(attendanceTime);
		List<Meal> list = dao.findByTypeAndTradeDaysTimeBetween(2,
				DatesUtil.getFirstDayOfMonth(attendanceTime.getOrderTimeBegin()),
				DatesUtil.getLastDayOfMonth(attendanceTime.getOrderTimeBegin()));
		if (list.size() > 0) {
			List<Long> idLong = list.stream().map(Meal::getId).collect(Collectors.toList());
			// 同步删除自动添加的报餐数据
			dao.deleteList(idLong);
		}
		List<Meal> meals = new ArrayList<Meal>();
		// 0=休息日期,
		PersonVariable restType = personVariableDao.findByType(0);
		// 1=早中晚三餐价值
		PersonVariable variable = personVariableDao.findByType(1);
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
			if (at.getCheckIn() != null && at.getCheckOut() != null && DatesUtil.getTimeSec(at.getCheckIn(), at.getCheckOut()) > 300) {
				// 签入时间小于早餐延迟时间
				if ((attendanceInit.getEatType() != null && (attendanceInit.getEatType() == 1
						|| attendanceInit.getEatType() == 3 || attendanceInit.getEatType() == 5))
						&& at.getCheckIn().compareTo(breakfastLagTime) != 1) {
					meals.add(addMeal(at, 1,Double.valueOf(variable.getKeyValue())));
				}
				// 1.签出时间大于午餐延迟时间
				if ( at.getCheckOut().compareTo(lunchLagTime) != -1 ) {
					meals.add(addMeal(at, 2, Double.valueOf(variable.getKeyValueTwo())));
				}
				// 1.签入时间小于晚餐延迟时间，2签出时间大于晚餐延迟时间
				if ((attendanceInit.getEatType() != null && (attendanceInit.getEatType() == 2
						|| attendanceInit.getEatType() == 3 || attendanceInit.getEatType() == 4 || attendanceInit.getEatType() == 5))
						&& (at.getCheckIn().compareTo(dinnerLagTime) != 1
								&& at.getCheckOut().compareTo(dinnerLagTime) != -1)) {
					meals.add(addMeal(at, 3, Double.valueOf(variable.getKeyValueThree())));
				}
				// 1签出时间大于夜宵时间
				if ((attendanceInit.getEatType() != null && (attendanceInit.getEatType() == 4 || attendanceInit.getEatType() == 5))
						&& at.getCheckOut().compareTo(midnight) != -1) {
					meals.add(addMeal(at, 4, Double.valueOf(variable.getKeyValue())));
				}
			}
			
			// 考勤异常，只有签入 
			if ((at.getCheckIn() != null && at.getCheckOut() == null) || (at.getCheckIn() != null && at.getCheckOut() != null && DatesUtil.getTimeSec(at.getCheckIn(), at.getCheckOut()) <= 300)) {
				if (attendanceInit.getEatType() != null && (attendanceInit.getEatType() == 1 
						|| attendanceInit.getEatType() == 3  || attendanceInit.getEatType() == 5)) {
					meals.add(addMeal(at, 1, Double.valueOf(variable.getKeyValue())));
				}
					meals.add(addMeal(at, 2, Double.valueOf(variable.getKeyValueTwo())));
				if (attendanceInit.getEatType() != null && (attendanceInit.getEatType() == 2
						|| attendanceInit.getEatType() == 3 || attendanceInit.getEatType() == 4 || attendanceInit.getEatType() == 5)) {
					meals.add(addMeal(at, 3, Double.valueOf(variable.getKeyValueThree())));
				}
				// 1.签入时间大于夜宵时间
				if ((attendanceInit.getEatType() != null && (attendanceInit.getEatType() == 4 || attendanceInit.getEatType() == 5))
						&& at.getCheckIn().compareTo(midnight) != -1) {
					meals.add(addMeal(at, 4, Double.valueOf(variable.getKeyValue())));
				}
			}
			
			// 无签到记录
			if (at.getCheckIn() == null && at.getCheckOut() == null) {
				// 不需要打卡
				if (attendanceInit.getWorkType() == 1 || attendanceInit.getWorkType() == 2) {
					if (at.getFlag() != 3) {
						meals.add(addMeal(at, 2, Double.valueOf(variable.getKeyValueTwo())));
					}
				}
			}
		}
		saveAllMeals(meals);
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
	private Meal addMeal(AttendanceTime attendanceTime, Integer mode, Double price) {
		Meal meal = new Meal();
		meal.setTradeDaysTime(attendanceTime.getTime());
		meal.setUserId(attendanceTime.getUserId());
		meal.setUserName(attendanceTime.getUserName());
		meal.setType(2);
		meal.setPrice(price);
		meal.setMode(mode);
		return meal;
	}

	/**
	 * 批量新增报餐记录
	 * 
	 * @param productList
	 */
	private void saveAllMeals(List<Meal> mealList) {
		entityManager.setFlushMode(FlushModeType.COMMIT);
		for (int i = 0; i < mealList.size(); i++) {
			Meal meal = mealList.get(i);
			entityManager.merge(meal);
			if (i % 1000 == 0 && i > 0) {
				entityManager.flush();
				entityManager.clear();
			}
		}
		entityManager.close();
	}

}
