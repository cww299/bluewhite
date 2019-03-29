package com.bluewhite.personnel.attendance.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.springframework.util.StringUtils;

import com.alibaba.druid.sql.visitor.functions.Now;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.personnel.attendance.dao.ApplicationLeaveDao;
import com.bluewhite.personnel.attendance.dao.AttendanceCollectDao;
import com.bluewhite.personnel.attendance.dao.AttendanceDao;
import com.bluewhite.personnel.attendance.dao.AttendanceInitDao;
import com.bluewhite.personnel.attendance.dao.AttendanceTimeDao;
import com.bluewhite.personnel.attendance.entity.ApplicationLeave;
import com.bluewhite.personnel.attendance.entity.Attendance;
import com.bluewhite.personnel.attendance.entity.AttendanceCollect;
import com.bluewhite.personnel.attendance.entity.AttendanceInit;
import com.bluewhite.personnel.attendance.entity.AttendanceTime;
import com.bluewhite.system.user.entity.User;
import com.bluewhite.system.user.entity.UserContract;
import com.bluewhite.system.user.service.UserService;
@Service
public class AttendanceTimeServiceImpl extends BaseServiceImpl<AttendanceTime, Long> implements AttendanceTimeService {

	@Autowired
	private UserService userService;
	@Autowired
	private AttendanceDao attendanceDao;
	@Autowired
	private AttendanceTimeDao dao;
	@Autowired
	private AttendanceInitDao attendanceInitDao;
	@Autowired
	private AttendanceCollectDao attendanceCollectDao;
	@Autowired
	private ApplicationLeaveDao applicationLeaveDao;

	@Override
	public List<AttendanceTime> findAttendanceTime(AttendanceTime attendance) {
		// 检查当前月份属于夏令时或冬令时 flag=ture 为夏令时
		boolean flag = false;
		try {
			flag = DatesUtil.belongCalendar(attendance.getOrderTimeBegin());
		} catch (ParseException e) {
			e.printStackTrace();
		}

		long size = DatesUtil.getDaySub(attendance.getOrderTimeBegin(),
				DatesUtil.getLastDayOftime(attendance.getOrderTimeEnd()));
		List<AttendanceTime> attendanceTimeList = new ArrayList<>();
		// 第一天的开始签到时间从6点开始新一天的签到
		Date beginTimes = DatesUtil.dayTime(attendance.getOrderTimeBegin(), "06:00:00");
		for (int i = 0; i < size; i++) {
			// 获取一天的签到结束时间,第二天的6点之前
			Date endTimes = DatesUtil.nextDay(beginTimes);

			// 当部门不为null时，按部门查找出所有的人员
			List<User> userList = new ArrayList<>();
			if (!StringUtils.isEmpty(attendance.getOrgNameId())) {
				userList = userService.findByOrgNameId(attendance.getOrgNameId());
			}
			if (!StringUtils.isEmpty(attendance.getUserId())) {
				User user = userService.findOne(attendance.getUserId());
				userList.add(user);
			}

			// 开始汇总每个人的考勤
			for (User us : userList) {
				List<Attendance> attList = attendanceDao.findByUserIdAndTimeBetween(us.getId(), beginTimes, endTimes);
				// 获取每个人当天的考勤记录
				AttendanceTime attendanceTime = new AttendanceTime();
				// 考勤汇总当天的日期
				attendanceTime.setTime(DatesUtil.getfristDayOftime(beginTimes));
				attendanceTime.setNumber(us.getNumber());
				attendanceTime.setWeek(DatesUtil.dateToWeek(beginTimes));
				attendanceTime.setUserId(us.getId());

				// 获取员工考勤的初始化参数
				AttendanceInit attendanceInit = attendanceInitDao.findByUserId(us.getId());
				// 上班开始时间
				Date workTime = null;
				// 上班结束时间
				Date workTimeEnd = null;
				// 中午休息开始时间
				Date restBeginTime = null;
				// 中午休息结束时间
				Date restEndTime = null;
				// 出勤时长
				Double turnWorkTime = null;
				// 休息时长
				Double restTime = null;
				// flag=ture 为夏令时
				if (flag) {
					String[] workTimeArr = attendanceInit.getWorkTimeSummer().split("-");
					// 将 工作间隔开始结束时间转换成当前日期的时间
					workTime = DatesUtil.dayTime(beginTimes, workTimeArr[0]);
					workTimeEnd = DatesUtil.dayTime(beginTimes, workTimeArr[1]);
					String[] restTimeArr = attendanceInit.getRestTimeSummer().split("-");
					// 将 休息间隔开始结束时间转换成当前日期的时间
					restBeginTime = DatesUtil.dayTime(beginTimes, restTimeArr[0]);
					restEndTime = DatesUtil.dayTime(beginTimes, restTimeArr[1]);
					restTime = attendanceInit.getRestSummer();
					turnWorkTime = attendanceInit.getTurnWorkTimeSummer();
				}
				// 冬令时
				String[] workTimeArr = attendanceInit.getWorkTimeWinter().split("-");
				// 将 工作间隔开始结束时间转换成当前日期的时间
				workTime = DatesUtil.dayTime(beginTimes, workTimeArr[0]);
				workTimeEnd = DatesUtil.dayTime(beginTimes, workTimeArr[1]);
				// 将 休息间隔开始结束时间转换成当前日期的时间
				String[] restTimeArr = attendanceInit.getRestTimeSummer().split("-");
				// 将 休息间隔开始结束时间转换成当前日期的时间
				restBeginTime = DatesUtil.dayTime(beginTimes, restTimeArr[0]);
				restEndTime = DatesUtil.dayTime(beginTimes, restTimeArr[1]);
				restTime = attendanceInit.getRestWinter();
				turnWorkTime = attendanceInit.getTurnWorkTimeWinter();

				// 全年无休，满勤
				if (attendanceInit.getRestType() == 1) {
					attendanceTime.setFlag(0);
					attendanceTime.setTurnWorkTime(turnWorkTime);
					attendanceTimeList.add(attendanceTime);
					continue;
				}

				// 2.周休一天，3.月休两天，其他周日算加班,4.全年无休，5.按到岗小时计算（类似全年无休，有自己的节假日休息）。
				// 获取休息的日期，不计算出勤
				// 当循环日期不等于休息日，进行考勤的记录,休息日无签到记录，无出勤数据
				String[] restDayArr = null;
				if (attendanceInit.getRestDay() != null) {
					restDayArr = attendanceInit.getRestDay().split(",");
					boolean rout = false;
					if (restDayArr.length > 0) {
						for (int j = 0; j < restDayArr.length; j++) {
							if (DatesUtil.getfristDayOftime(beginTimes).equals(restDayArr[j])) {
								rout = true;
								break;
							}
						}
					}
					if (rout) {
						attendanceTime.setFlag(0);
						attendanceTimeList.add(attendanceTime);
						continue;
					}
				}

				boolean sign = false;
				Double minute = 0.0;
				// 当员工的签到时间在上班时间之后，需要进行，对于员工前一天上班时间的延后处理，重新设定上班时间
				// 加班后默认到岗时间(1.按点上班，2.第二天上班时间以超过24:00后的时间往后推,3.超过24:30后默认休息7.5小时)
				// 获取前一天员工的签出时间。是当天的24:00到6:00之间的签出记录
				List<Attendance> afterAttendance = null;
				if (attendanceInit.getComeWork() == 2) {
					afterAttendance = attendanceDao.findByUserIdAndTimeBetween(us.getId(),
							DatesUtil.dayTime(beginTimes, "00:00:00"), DatesUtil.dayTime(beginTimes, "06:00:00"));
					if (afterAttendance.size() > 0) {
						// 得到最后一次签到记录离24：00相差分钟数
						minute = DatesUtil.getTime(DatesUtil.dayTime(beginTimes, "00:00:00"),
								afterAttendance.get(afterAttendance.size() - 1).getTime());
						sign = true;
					}
				}
				// 2.超过24:30后默认休息7.5小时
				if (attendanceInit.getComeWork() == 3) {
					afterAttendance = attendanceDao.findByUserIdAndTimeBetween(us.getId(),
							DatesUtil.dayTime(beginTimes, "00:30:00"), DatesUtil.dayTime(beginTimes, "06:00:00"));
					if (afterAttendance.size() > 0) {
						// 得到最后一次签到记录离24：30相差分钟数
						minute = DatesUtil.getTime(DatesUtil.dayTime(beginTimes, "00:30:00"),
								afterAttendance.get(afterAttendance.size() - 1).getTime());
						sign = true;
					}
				}

				// 考情记录有三种情况。当一天的考勤记录条数等于大于2时,为正常的考勤
				// 大于2时，取集合中的最后一条数据作为考勤记录
				if (attList.size() >= 2) {
					// 获取签到签出时间
					if (attList.get(0).getTime().before(attList.get(attList.size() - 1).getTime())) {
						// 上班
						attendanceTime.setCheckIn(attList.get(0).getTime());
						// 下班
						attendanceTime.setCheckOut(attList.get(attList.size() - 1).getTime());
					} else {
						// 上班
						attendanceTime.setCheckIn(attList.get(attList.size() - 1).getTime());
						// 下班
						attendanceTime.setCheckOut(attList.get(0).getTime());
					}

					// 工作总时长(签到签出时间总和减去休息时间)
					// 多种情况
					// :1.当签到签出时间同时在休息时间之前2.当签到签出时间都在休息时间之后3.当签到签出时间（任一or全部）在休息时间之间（当出现这种情况
					// , 均不用计算休息时间）
					// 在上午同时签到签出
					if (attendanceTime.getCheckIn().before(restBeginTime)
							&& attendanceTime.getCheckOut().before(restBeginTime)) {
						attendanceTime.setWorkTime(
								DatesUtil.getTimeHour(attendanceTime.getCheckIn(), attendanceTime.getCheckOut()));
					} else
					// 在下午同时签到签出
					if (attendanceTime.getCheckIn().after(restEndTime)
							&& attendanceTime.getCheckOut().after(restEndTime)) {
						attendanceTime.setWorkTime(
								DatesUtil.getTimeHour(attendanceTime.getCheckIn(), attendanceTime.getCheckOut()));
					} else
					// 当签出时间在休息时间之间 （从签出时间到休息时间开始）
					if (attendanceTime.getCheckOut().after(restBeginTime)
							&& attendanceTime.getCheckOut().before(restEndTime)) {
						attendanceTime.setWorkTime(DatesUtil.getTimeHour(attendanceTime.getCheckIn(), restBeginTime));
					} else
					// 当签入时间在休息时间之间 （从休息时间结束到签出时间）
					if (attendanceTime.getCheckIn().after(restBeginTime)
							&& attendanceTime.getCheckOut().before(restEndTime)) {
						attendanceTime.setWorkTime(DatesUtil.getTimeHour(restEndTime, attendanceTime.getCheckOut()));
					} else {
						// 实际工作时长
						if (attendanceInit.getRestTimeWork() != 2) {
							attendanceTime.setWorkTime(NumUtils.sub(
									DatesUtil.getTimeHour(attendanceTime.getCheckIn(), attendanceTime.getCheckOut()),
									restTime));
						} else {
							attendanceTime.setWorkTime(
									DatesUtil.getTimeHour(attendanceTime.getCheckIn(), attendanceTime.getCheckOut()));
						}
					}

					// 进行出勤，加班，缺勤，迟到，早退的计算
					AttendanceTool.attendanceIntTool(sign, workTime, workTimeEnd, minute, turnWorkTime, attendanceTime,
							attendanceInit, us);
				}
				// 当一天的考勤记录条数小于2时。为异常的考勤
				if (attList.size() < 2) {
					// 缺勤时间（公司未规定放假日期，所以当员工没有打卡记录时，统一算缺勤)
					attendanceTime.setDutytime(NumUtils.sub(turnWorkTime, 0));
					attendanceTime.setFlag(1);
				}
				attendanceTimeList.add(attendanceTime);

			}
			// 循环一次结束后，获取下一天的签到开始时间,6点开始
			beginTimes = DatesUtil.nextDay(beginTimes);
		}
		return attendanceTimeList;
	}

	@Override
	public List<Map<String, Object>> findAttendanceTimeCollect(AttendanceTime attendanceTime) throws ParseException {
		return attendanceCollect(attendanceTimeByApplication(findAttendanceTime(attendanceTime)));
	}

	/**
	 * 通过传入的工作情况，按人员汇总出考勤数据
	 */
	private List<Map<String, Object>> attendanceCollect(List<AttendanceTime> attendanceTimeList) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		// 最外层循环人员list
		List<Map<String, Object>> allList = new ArrayList<>();
		// 单向数据map
		Map<String, Object> allMap = null;
		// 按人员分组
		Map<String, List<AttendanceTime>> mapAttendance = attendanceTimeList.stream()
				.filter(AttendanceTime -> AttendanceTime.getNumber() != null)
				.collect(Collectors.groupingBy(AttendanceTime::getNumber, Collectors.toList()));
		for (String ps1 : mapAttendance.keySet()) {
			allMap = new HashMap<>();
			// 获取单一员工日期区间所有的考勤数据
			List<AttendanceTime> psList1 = mapAttendance.get(ps1);
			// 按日期自然排序
			List<AttendanceTime> attendanceTimeList1 = psList1.stream()
					.sorted(Comparator.comparing(AttendanceTime::getTime)).collect(Collectors.toList());
			AttendanceCollect attendanceCollect = new AttendanceCollect(psList1);
			attendanceCollectDao.save(attendanceCollect);
			allMap.put("attendanceTimeData", attendanceTimeList1);
			allMap.put("collect", attendanceCollect);
			allList.add(allMap);
		}
		return allList;
	}

	@Override
	public AttendanceTime updateAttendanceTime(AttendanceTime attendanceTime) {
		AttendanceTime oldAttendanceTime = dao.findOne(attendanceTime.getId());
		BeanCopyUtils.copyNotEmpty(attendanceTime, oldAttendanceTime, "");
		return dao.save(oldAttendanceTime);
	}

	@Override
	public String checkAttendanceTime(AttendanceTime attendanceTime) {
		//已统计的提示信息
		String ex = "";
		if(new Date().before(DatesUtil.getLastDayOfMonth(attendanceTime.getOrderTimeBegin()))){
			throw new ServiceException("选择日期的签到记录未完成,无法统计");
		};
		List<User> userList = new ArrayList<>();
		if (!StringUtils.isEmpty(attendanceTime.getOrgNameId())) {
			userList = userService.findByOrgNameId(attendanceTime.getOrgNameId());
		}
		if (!StringUtils.isEmpty(attendanceTime.getUserId())) {
			User user = userService.findOne(attendanceTime.getUserId());
			userList.add(user);
		}
		for (User user : userList) {
			try {
				// 查询该月有没有初始化
				attendanceCollectDao.findByUserIdAndTime(user.getId(),attendanceTime.getOrderTimeBegin());
			} catch (Exception e) {
				ex = ex + "员工" + user.getUserName() + ",";
			}
		}
		return ex;
	}
	

	@Override
	public List<AttendanceTime> findAttendanceTimePage(AttendanceTime param) {
		Page<AttendanceTime> pages = dao.findAll((root, query, cb) -> {
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
			// 按考勤日期
			if (!StringUtils.isEmpty(param.getOrderTimeBegin()) && !StringUtils.isEmpty(param.getOrderTimeEnd())) {
				predicate.add(cb.between(root.get("time").as(Date.class), param.getOrderTimeBegin(),
						param.getOrderTimeEnd()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, new PageParameter());
		return pages.getContent();
	}
	
	@Override
	public List<AttendanceTime> attendanceTimeByApplication(List<AttendanceTime> attendanceTimeList) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 按人员分组
		Map<Long, List<AttendanceTime>> mapAttendanceTime = attendanceTimeList.stream()
				.collect(Collectors.groupingBy(AttendanceTime::getUserId, Collectors.toList()));
		for (Long ps1 : mapAttendanceTime.keySet()) {
			// 获取单一员工日期区间所有的请假事项
			List<AttendanceTime> psList1 = mapAttendanceTime.get(ps1);
			// 按考勤数据日期自然排序
			List<AttendanceTime> attendanceTimeListSort = psList1.stream().sorted(Comparator.comparing(AttendanceTime::getTime)).collect(Collectors.toList());
			for(AttendanceTime at: attendanceTimeListSort){
				// 检查当前月份属于夏令时或冬令时 flag=ture 为夏令时
				boolean flag = false;
				try {
					flag = DatesUtil.belongCalendar(at.getTime());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				// 获取员工考勤的初始化参数
				AttendanceInit attendanceInit = attendanceInitDao.findByUserId(at.getUserId());
				// 上班开始时间
				Date workTime = null;
				// 上班结束时间
				Date workTimeEnd = null;
				// 中午休息开始时间
				Date restBeginTime = null;
				// 中午休息结束时间
				Date restEndTime = null;
				// 出勤时长
				Double turnWorkTime = null;
				// 休息时长
				Double restTime = null;
				//查找出申请时间是当月的有符合该员工请假事项
				List<ApplicationLeave> applicationLeave = applicationLeaveDao.findByUserIdAndWriteTimeBetween(at.getUserId(),
						DatesUtil.getFirstDayOfMonth(at.getTime()),DatesUtil.getLastDayOfMonth(at.getTime()));
				for(ApplicationLeave al :applicationLeave){
						  	 JSONObject jo = JSON.parseObject(al.getTime());
					         String date =  jo.getString("date");
					         Double time = Double.valueOf(jo.getString("time"));
					         //获取时间区间
			        		 String[] dateArr =  date.split("~");
			        		 Date dateLeave =null;
					         if(dateArr.length<2){
					        	 dateLeave = sdf.parse(date);
					         }
					 		// flag=ture 为夏令时
				 				if (flag) {
				 					String[] workTimeArr = attendanceInit.getWorkTimeSummer().split("-");
				 					// 将 工作间隔开始结束时间转换成当前日期的时间
				 					workTime = DatesUtil.dayTime(dateLeave, workTimeArr[0]);
				 					workTimeEnd = DatesUtil.dayTime(dateLeave, workTimeArr[1]);
				 					String[] restTimeArr = attendanceInit.getRestTimeSummer().split("-");
				 					// 将 休息间隔开始结束时间转换成当前日期的时间
				 					restBeginTime = DatesUtil.dayTime(dateLeave, restTimeArr[0]);
				 					restEndTime = DatesUtil.dayTime(dateLeave, restTimeArr[1]);
				 					restTime = attendanceInit.getRestSummer();
				 					turnWorkTime = attendanceInit.getTurnWorkTimeSummer();
				 				}
				 				// 冬令时
				 				String[] workTimeArr = attendanceInit.getWorkTimeWinter().split("-");
				 				// 将 工作间隔开始结束时间转换成当前日期的时间
				 				workTime = DatesUtil.dayTime(dateLeave, workTimeArr[0]);
				 				workTimeEnd = DatesUtil.dayTime(dateLeave, workTimeArr[1]);
				 				// 将 休息间隔开始结束时间转换成当前日期的时间
				 				String[] restTimeArr = attendanceInit.getRestTimeSummer().split("-");
				 				// 将 休息间隔开始结束时间转换成当前日期的时间
				 				restBeginTime = DatesUtil.dayTime(dateLeave, restTimeArr[0]);
				 				restEndTime = DatesUtil.dayTime(dateLeave, restTimeArr[1]);
				 				restTime = attendanceInit.getRestWinter();
				 				turnWorkTime = attendanceInit.getTurnWorkTimeWinter();
					         
					        	 //请假
					        	 if(al.isHoliday() && dateArr.length>=2){
					        			 //获取请假所有日期
					        			 List<String> dateList = DatesUtil.getPerDaysByStartAndEndDate(dateArr[0],dateArr[1],"yyyy-MM-dd");
					        			 for(String dt : dateList){
					        				 if(sdf.parse(dt).compareTo(at.getTime())==0){
					        					 //变更为请假状态
					        					 at.setFlag(2);
					        					 time = time>=turnWorkTime ? turnWorkTime : NumUtils.sub(time,turnWorkTime);
					        					 at.setLeaveTime(at.getLeaveTime()+time);
					        					 at.setHolidayDetail(at.getHolidayDetail()+","+al.getHolidayDetail());
					        				 }
					        			 }
					        	 }
					        	 //补签
					        	 if(al.isAddSignIn() && at.getTime().compareTo(DatesUtil.getfristDayOftime(dateLeave))==0 ){
					        		 if(time==0){
					        			 at.setCheckIn(dateLeave);
					        		 }else{
					        			 at.setCheckOut(dateLeave); 
					        		 }
				        			boolean sign = false;
				    				Double minute = 0.0;
					     			List<Attendance> afterAttendance = null;
					     			Date beginTimes = at.getTime();
									if (attendanceInit.getComeWork() == 2) {
										afterAttendance = attendanceDao.findByUserIdAndTimeBetween(at.getUserId(),
												DatesUtil.dayTime(beginTimes, "00:00:00"), DatesUtil.dayTime(beginTimes, "06:00:00"));
										if (afterAttendance.size() > 0) {
											// 得到最后一次签到记录离24：00相差分钟数
											minute = DatesUtil.getTime(DatesUtil.dayTime(beginTimes, "00:00:00"),
													afterAttendance.get(afterAttendance.size() - 1).getTime());
											sign = true;
										}
									}
									// 2.超过24:30后默认休息7.5小时
									if (attendanceInit.getComeWork() == 3) {
										afterAttendance = attendanceDao.findByUserIdAndTimeBetween(at.getUserId(),
												DatesUtil.dayTime(beginTimes, "00:30:00"), DatesUtil.dayTime(beginTimes, "06:00:00"));
										if (afterAttendance.size() > 0) {
											// 得到最后一次签到记录离24：30相差分钟数
											minute = DatesUtil.getTime(DatesUtil.dayTime(beginTimes, "00:30:00"),
													afterAttendance.get(afterAttendance.size() - 1).getTime());
											sign = true;
										}
									}
					        		// 进行出勤，加班，缺勤，迟到，早退的计算
									AttendanceTime signAtt= AttendanceTool.attendanceIntTool(sign, workTime, workTimeEnd, minute, turnWorkTime, at,
												attendanceInit, at.getUser());
									at = signAtt;
					        	 }
					        	 
					        	 //加班
					        	 if(al.isApplyOvertime() && at.getTime().compareTo(dateLeave)==0){
					        		 at.setOvertime(NumUtils.sum(at.getOvertime(),time));
					        	 }
					        	 //调休且员工出勤时间等于调休到的那一天
								 if(al.isTradeDays()  && at.getTime().compareTo(dateLeave)==0){
									 at.setTurnWorkTime(turnWorkTime);
									 at.setDutytime(0.0);
									 at.setBelate(0);	
									 at.setBelateTime(0.0);
								}
					 	
					}
				}
			}
		return dao.save(attendanceTimeList);
	}
	


}
