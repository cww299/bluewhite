package com.bluewhite.personnel.attendance.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.bluewhite.personnel.attendance.entity.AttendanceInit;
import com.bluewhite.personnel.attendance.entity.AttendanceTime;
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
	@Autowired
	private AttendanceTimeService attendanceTimeService;
	@Autowired
	private AttendanceInitService attendanceInitService;
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
			String org=user.getOrgName().getName();
			allMap.put("username", aString);
			allMap.put("money", budget);
			allMap.put("orgName", org);
			allMap.put("modeOne",modeOne);
			allMap.put("modeTwo",modeTwo);
			allMap.put("modeThree",modeThree);
			allList.add(allMap);
			}
		
		return allList;
	}
	//同步吃饭记录
	@Override
	public int InitMeal(AttendanceTime attendanceTime) throws ParseException {
	List<AttendanceTime> attendanceTimes=attendanceTimeService.findAttendanceTime(attendanceTime);
	List<Meal> list=dao.findByTypeAndTradeDaysTimeBetween(2,DatesUtil.getFirstDayOfMonth(attendanceTime.getOrderTimeBegin()),DatesUtil.getLastDayOfMonth(attendanceTime.getOrderTimeBegin()));
	if (list.size()>0) {
		dao.delete(list);
	} 
	List<Meal> meals=new ArrayList<Meal>();
	PersonVariable variable=personVariableDao.findByType(1);
	for (AttendanceTime attendanceTime2 : attendanceTimes) {
		if (attendanceTime2.getCheckIn()!=null || attendanceTime2.getCheckOut()!=null) {
			int j=attendanceTime2.getCheckIn().getHours();
			if (j>12) {
				attendanceTime.setCheckOut(attendanceTime2.getCheckIn());
				attendanceTime.setCheckIn(null);
			}
			if (j==12) {
				attendanceTime.setCheckIn(attendanceTime2.getCheckIn());
				attendanceTime.setCheckOut(attendanceTime2.getCheckIn());
			}
		}
		
		if (attendanceTime2.getFail()==2) {
			
		}else{ 
		//基础数据 每一餐的价格
		if (attendanceTime2.getCheckOut()!=null || attendanceTime2.getCheckIn()!=null) {
		if (attendanceTime2.getCheckOut()!=null && attendanceTime2.getCheckIn()!=null) {
			DateFormat df = new SimpleDateFormat("HH:mm:ss");
			Date dt1 = df.parse("08:30:00");
			Date dt3 = df.parse("13:30:00");
			Date dt4 = df.parse("18:30:00");
			Date dt5 = df.parse("04:30:00");
			String  aString=df.format(attendanceTime2.getCheckIn());
			Date dt2= df.parse(aString);
			
			String  aString2=df.format(attendanceTime2.getCheckOut());
			Date dt6= df.parse(aString2);
			//签出时间 小时dt5 重新赋值
			if (dt6.compareTo(dt5)==-1) {
				dt6=df.parse("23:59:59");
			}
			int a= dt2.compareTo(dt1);
			int b= dt2.compareTo(dt3);
			int c= dt6.compareTo(dt4);
			int d= dt2.compareTo(dt4);//吃晚饭  第一次打卡要小于18.30
			if (attendanceTime2.getEatType()!=null) {
				//早饭 签入时间小于dt1
				if (attendanceTime2.getEatType()==1 &&  a==-1) {
					Meal meal2=new Meal();
					meal2.setTradeDaysTime(attendanceTime2.getTime());
					meal2.setPrice(Double.valueOf(variable.getKeyValue()));
					meal2.setMode(1);
					meal2.setUserName(attendanceTime2.getUserName());
					meal2.setUserId(attendanceTime2.getUserId());
					meal2.setType(2);
					meals.add(meal2);
				}
			}
			
			if (b==-1) {
				//中餐 签入时间小于dt3
				Meal meal2=new Meal();
				meal2.setTradeDaysTime(attendanceTime2.getTime());
				meal2.setPrice(Double.valueOf(variable.getKeyValueTwo()));
				meal2.setMode(2);
				meal2.setUserName(attendanceTime2.getUserName());
				meal2.setUserId(attendanceTime2.getUserId());
				meal2.setType(2);
				meals.add(meal2);
			}
			
			if (attendanceTime2.getEatType()!=null) {
				//晚饭 签出时间大于dt4
			if (attendanceTime2.getEatType()==2 && d==-1 && c==1) {
				Meal meal2=new Meal();
				meal2.setTradeDaysTime(attendanceTime2.getTime());
				meal2.setPrice(Double.valueOf(variable.getKeyValueThree()));
				meal2.setMode(3);
				meal2.setUserName(attendanceTime2.getUserName());
				meal2.setUserId(attendanceTime2.getUserId());
				meal2.setType(2);
				meals.add(meal2);
			} 
			//早晚饭
			if (attendanceTime2.getEatType()==3 &&  a==-1 && c==1) {
				Meal meal2=new Meal();
				meal2.setTradeDaysTime(attendanceTime2.getTime());
				meal2.setPrice(Double.valueOf(variable.getKeyValue()));
				meal2.setMode(1);
				meal2.setUserName(attendanceTime2.getUserName());
				meal2.setUserId(attendanceTime2.getUserId());
				meal2.setType(2);
				meals.add(meal2);
				Meal meal3=new Meal();
				meal3.setTradeDaysTime(attendanceTime2.getTime());
				meal3.setPrice(Double.valueOf(variable.getKeyValueThree()));
				meal3.setMode(3);
				meal3.setUserName(attendanceTime2.getUserName());
				meal3.setUserId(attendanceTime2.getUserId());
				meal3.setType(2);
				meals.add(meal3);
		}
			}
		}else {
		if (attendanceTime2.getCheckIn()!=null) {
			if (attendanceTime2.getEatType()!=null) {
				//早饭
				if (attendanceTime2.getEatType()==1) {
					Meal meal2=new Meal();
					meal2.setTradeDaysTime(attendanceTime2.getTime());
					meal2.setPrice(Double.valueOf(variable.getKeyValue()));
					meal2.setMode(1);
					meal2.setUserName(attendanceTime2.getUserName());
					meal2.setUserId(attendanceTime2.getUserId());
					meal2.setType(2);
					meals.add(meal2);
				}
			}
		}
		if (attendanceTime2.getCheckOut()!=null) {
			if (attendanceTime2.getEatType()!=null) {
				//晚饭
			if (attendanceTime2.getEatType()==2) {
				Meal meal2=new Meal();
				meal2.setTradeDaysTime(attendanceTime2.getTime());
				meal2.setPrice(Double.valueOf(variable.getKeyValueThree()));
				meal2.setMode(3);
				meal2.setUserName(attendanceTime2.getUserName());
				meal2.setUserId(attendanceTime2.getUserId());
				meal2.setType(2);
				meals.add(meal2);
			} 
			}
		}
		if (attendanceTime2.getEatType()!=null) {
			//早饭晚饭都吃
			if (attendanceTime2.getEatType()==3) {
				Meal meal2=new Meal();
				meal2.setTradeDaysTime(attendanceTime2.getTime());
				meal2.setPrice(Double.valueOf(variable.getKeyValue()));
				meal2.setMode(1);
				meal2.setUserName(attendanceTime2.getUserName());
				meal2.setUserId(attendanceTime2.getUserId());
				meal2.setType(2);
				meals.add(meal2);
				Meal meal3=new Meal();
				meal3.setTradeDaysTime(attendanceTime2.getTime());
				meal3.setPrice(Double.valueOf(variable.getKeyValueThree()));
				meal3.setMode(3);
				meal3.setUserName(attendanceTime2.getUserName());
				meal3.setUserId(attendanceTime2.getUserId());
				meal3.setType(2);
				meals.add(meal3);
		}
		}
		 	Meal meal2=new Meal();
			meal2.setTradeDaysTime(attendanceTime2.getTime());
			meal2.setPrice(Double.valueOf(variable.getKeyValueTwo()));
			meal2.setMode(2);
			meal2.setUserName(attendanceTime2.getUserName());
			meal2.setUserId(attendanceTime2.getUserId());
			meal2.setType(2);
			meals.add(meal2);
		}
		}else{
			AttendanceInit attendanceInit = attendanceInitService.findByUserId(attendanceTime2.getUserId());
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			PersonVariable restType = personVariableDao.findByType(0);
			boolean rout = false;
			// 1.周休一天，
			if (attendanceInit.getWorkType()==1 || attendanceInit.getWorkType()==2) {
			if (attendanceInit.getRestType() == 1) {
				String[] weeklyRestDate = restType.getKeyValue().split(",");
				if (weeklyRestDate.length > 0) {
					for (int j = 0; j < weeklyRestDate.length; j++) {
						if (DatesUtil.getfristDayOftime(attendanceTime2.getTime()).compareTo(sdf.parse(weeklyRestDate[j]))==0) {
							rout=true;
						}
					}
					if (rout==false) {
						Meal meal2=new Meal();
						meal2.setTradeDaysTime(attendanceTime2.getTime());
						meal2.setPrice(Double.valueOf(variable.getKeyValueTwo()));
						meal2.setMode(2);
						meal2.setUserName(attendanceTime2.getUserName());
						meal2.setUserId(attendanceTime2.getUserId());
						meal2.setType(2);
						meals.add(meal2);
					}
				}
			}
			//2.月休两天
			if (attendanceInit.getRestType() == 2) {
				String[] weeklyRestDate = restType.getKeyValue().split(",");
				if (weeklyRestDate.length > 0) {
					for (int j = 0; j < weeklyRestDate.length; j++) {
						if (DatesUtil.getfristDayOftime(attendanceTime2.getTime()).compareTo(sdf.parse(weeklyRestDate[j]))==0) {
							rout=true;
						}
					}
					if (rout==false) {
						Meal meal2=new Meal();
						meal2.setTradeDaysTime(attendanceTime2.getTime());
						meal2.setPrice(Double.valueOf(variable.getKeyValueTwo()));
						meal2.setMode(2);
						meal2.setUserName(attendanceTime2.getUserName());
						meal2.setUserId(attendanceTime2.getUserId());
						meal2.setType(2);
						meals.add(meal2);
					}
				}
			}
			}
		}
	}
	}
		dao.save(meals);
		return 0;
	}

	
	public static void main(String[] args) throws ParseException {
		Date date=new Date();
		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		Date dt1 = df.parse("10:00:00");
		String  aString=df.format(date);
		Date date2= df.parse(aString);
		System.out.println(date2.compareTo(dt1));
	}
	
	


}
