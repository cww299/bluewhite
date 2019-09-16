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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.Constants;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.finance.attendance.dao.AttendancePayDao;
import com.bluewhite.finance.attendance.entity.AttendancePay;
import com.bluewhite.personnel.attendance.dao.ApplicationLeaveDao;
import com.bluewhite.personnel.attendance.dao.AttendanceCollectDao;
import com.bluewhite.personnel.attendance.dao.AttendanceDao;
import com.bluewhite.personnel.attendance.dao.AttendanceInitDao;
import com.bluewhite.personnel.attendance.dao.AttendanceTimeDao;
import com.bluewhite.personnel.attendance.dao.PersonVariableDao;
import com.bluewhite.personnel.attendance.entity.ApplicationLeave;
import com.bluewhite.personnel.attendance.entity.Attendance;
import com.bluewhite.personnel.attendance.entity.AttendanceCollect;
import com.bluewhite.personnel.attendance.entity.AttendanceInit;
import com.bluewhite.personnel.attendance.entity.AttendanceTime;
import com.bluewhite.personnel.attendance.entity.PersonVariable;
import com.bluewhite.system.user.entity.User;
import com.bluewhite.system.user.service.UserService;

@Service
public class AttendanceTimeServiceImpl extends BaseServiceImpl<AttendanceTime, Long> implements AttendanceTimeService {

	@Autowired
	private PersonVariableDao personVariableDao;
	@Autowired
	private UserService userService;
	@Autowired
	private AttendanceDao attendanceDao;
	@Autowired
	private AttendanceService attendanceService;
	@Autowired
	private AttendanceTimeDao dao;
	@Autowired
	private AttendanceInitDao attendanceInitDao;
	@Autowired
	private AttendanceInitService attendanceInitService;
	@Autowired
	private AttendanceCollectDao attendanceCollectDao;
	@Autowired
	private ApplicationLeaveDao applicationLeaveDao;
	@Autowired
	private AttendancePayDao attendancePayDao;

	@Override
	public List<AttendanceTime> findAttendanceTime(AttendanceTime attendance) throws ParseException {
		// 获取改时间段所有的打卡记录
		List<Attendance> allAttList = null;
		// 获取当前日期的固定休息日
		PersonVariable restType = personVariableDao.findByTypeAndTime(0,
				DatesUtil.getFirstDayOfMonth(attendance.getOrderTimeBegin()));
		// 报餐系统所需要的人员
		List<User> list = null;
		// 报餐人员满足于输入时间内离职和入职
		List<AttendanceInit> attendanceInitList = null;
		if (attendance.getUserId() == null && attendance.getOrgNameId() == null) {
			User user = new User();
			user.setIsAdmin(false);
			user.setForeigns(0);
			list = userService.findUserList(user).stream()
					.filter(User -> (User.getQuit() != null && User.getQuit() == 0) || (User.getQuitDate() != null
							&& User.getQuitDate().compareTo(attendance.getOrderTimeBegin()) != -1))
					.collect(Collectors.toList()).stream()
					.filter(User -> User.getEntry() != null && User.getEntry()
							.compareTo(DatesUtil.getLastDayOfMonth(attendance.getOrderTimeBegin())) != 1)
					.collect(Collectors.toList());
			attendanceInitList = attendanceInitDao.findAll();
			allAttList = attendanceDao.findByTimeBetween(attendance.getOrderTimeBegin(),
					DatesUtil.getLastDayOfMonth(attendance.getOrderTimeBegin()));
		}
		if (attendance.getUserId() != null && attendance.getOrgNameId() != null) {
			throw new ServiceException("请不要同时选择部门和人员");
		}

		// 检查当前月份属于夏令时或冬令时 flag=ture 为夏令时
		boolean flag = DatesUtil.belongCalendar(attendance.getOrderTimeBegin());
		long size = DatesUtil.getDaySub(attendance.getOrderTimeBegin(),
				DatesUtil.getLastDayOfMonth(attendance.getOrderTimeBegin()));
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
				Attendance ae = new Attendance();
				ae.setOrgNameId(attendance.getOrgNameId());
				ae.setOrderTimeBegin(attendance.getOrderTimeBegin());
				ae.setOrderTimeEnd(DatesUtil.getLastDayOfMonth(attendance.getOrderTimeBegin()));
				allAttList = attendanceService.findPageAttendance(ae, new PageParameter(0, Integer.MAX_VALUE))
						.getRows();
			}
			if (!StringUtils.isEmpty(attendance.getUserId())) {
				User user = userService.findOne(attendance.getUserId());
				allAttList = attendanceDao.findByUserIdAndTimeBetween(attendance.getUserId(),
						attendance.getOrderTimeBegin(), DatesUtil.getLastDayOfMonth(attendance.getOrderTimeBegin()));
				userList.add(user);
			}
			if (attendance.getUserId() == null && attendance.getOrgNameId() == null) {
				userList.addAll(list);
			}

			String exUser = "";
			// 开始汇总每个人的考勤
			for (User us : userList) {
				List<Attendance> attUserList = new ArrayList<>();
				// 获取员工一天内的打卡记录并按照自然排序
				List<Attendance> attUserListSort = allAttList.stream().filter(
						Attendance -> Attendance.getUserId() != null && Attendance.getUserId().equals(us.getId()))
						.collect(Collectors.toList());
				for (Attendance at : attUserListSort) {
					if ((at.getTime().after(beginTimes) || at.getTime().compareTo(beginTimes) == 0)
							&& (at.getTime().before(endTimes) || at.getTime().compareTo(endTimes) == 0)) {
						attUserList.add(at);
					}
				}
				List<Attendance> attList = attUserList.stream().sorted(Comparator.comparing(Attendance::getTime))
						.collect(Collectors.toList());
				// 获取每个人当天的考勤记录
				AttendanceTime attendanceTime = new AttendanceTime();
				// 考勤汇总当天的日期
				attendanceTime.setTime(DatesUtil.getfristDayOftime(beginTimes));
				attendanceTime.setNumber(us.getNumber());
				attendanceTime.setWeek(DatesUtil.dateToWeek(beginTimes));
				attendanceTime.setUserId(us.getId());
				attendanceTime.setUserName(us.getUserName());
				attendanceTime.setBelate(0);
				attendanceTime.setFlag(0);
				attendanceTime.setLeaveEarly(0);
				attendanceTime.setBelateTime(0.0);
				attendanceTime.setLeaveTime(0.0);
				attendanceTime.setTurnWorkTime(0.0);
				attendanceTime.setWorkTime(0.0);
				attendanceTime.setOvertime(0.0);
				attendanceTime.setLeaveEarlyTime(0.0);
				attendanceTime.setDutytime(0.0);

				// 获取员工考勤的初始化参数
				AttendanceInit attendanceInit = null;
				if (attendance.getUserId() == null && attendance.getOrgNameId() == null) {
					List<AttendanceInit> attendanceInitUserList = attendanceInitList.stream()
							.filter(AttendanceInit -> AttendanceInit.getUserId() != null
									&& AttendanceInit.getUserId().equals(us.getId()))
							.collect(Collectors.toList());
					attendanceInit = attendanceInitUserList.size() > 0 ? attendanceInitUserList.get(0) : null;
				} else {
					attendanceInit = attendanceInitService.findByUserId(us.getId());
				}
				if (attendanceInit == null) {
					exUser += exUser.equals("") ? us.getUserName() : "," + us.getUserName();
					continue;
				}

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
				// 报餐吃饭记录
				attendanceTime.setAttendanceInit(attendanceInit);

				// flag=ture 为夏令时
				if (flag) {
					String[] workTimeArr = attendanceInit.getWorkTimeSummer().split(" - ");
					// 将 工作间隔开始结束时间转换成当前日期的时间
					workTime = DatesUtil.dayTime(beginTimes, workTimeArr[0]);
					workTimeEnd = DatesUtil.dayTime(beginTimes, workTimeArr[1]);
					String[] restTimeArr = attendanceInit.getRestTimeSummer().split(" - ");
					// 将 休息间隔开始结束时间转换成当前日期的时间
					restBeginTime = DatesUtil.dayTime(beginTimes, restTimeArr[0]);
					restEndTime = DatesUtil.dayTime(beginTimes, restTimeArr[1]);
					restTime = attendanceInit.getRestSummer();
					turnWorkTime = attendanceInit.getTurnWorkTimeSummer();
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
					restEndTime = DatesUtil.dayTime(beginTimes, restTimeArr[1]);
					restTime = attendanceInit.getRestWinter();
					turnWorkTime = attendanceInit.getTurnWorkTimeWinter();
				}

				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				boolean rout = false;
				// 1.周休一天，
				if (attendanceInit.getRestType() == 1) {
					String[] weeklyRestDate = restType.getKeyValue().split(",");
					if (weeklyRestDate.length > 0) {
						for (int j = 0; j < weeklyRestDate.length; j++) {
							if (DatesUtil.getfristDayOftime(beginTimes).compareTo(sdf.parse(weeklyRestDate[j])) == 0) {
								rout = true;
								break;
							}
						}
					}
				}

				// 2.月休两天
				if (attendanceInit.getRestType() == 2) {
					String[] monthRestDate = restType.getKeyValueTwo().split(",");
					if (monthRestDate.length > 0) {
						for (int j = 0; j < monthRestDate.length; j++) {
							if (DatesUtil.getfristDayOftime(beginTimes).compareTo(sdf.parse(monthRestDate[j])) == 0) {
								rout = true;
								break;
							}
						}
					}
				}

				// 获取休息的日期，不计算出勤
				// 当循环日期不等于休息日，进行考勤的记录,休息日无签到记录，无出勤数据
				String[] restDayArr = null;
				if (!StringUtils.isEmpty(attendanceInit.getRestDay())) {
					restDayArr = attendanceInit.getRestDay().split(",");
					if (restDayArr.length > 0) {
						for (int j = 0; j < restDayArr.length; j++) {
							if (DatesUtil.getfristDayOftime(beginTimes).compareTo(sdf.parse(restDayArr[j])) == 0) {
								rout = true;
								break;
							}
						}
					}
				}

				boolean sign = false;
				Double minute = 0.0;
				// 当员工的签到时间在上班时间之后，需要进行，对于员工前一天上班时间的延后处理，重新设定上班时间
				// 加班后默认到岗时间(1.按点上班，2.第二天上班时间以超过24:00后的时间往后推,3.超过24:30后默认休息7.5小时)
				// 获取前一天员工的签出时间。是当天的24:00到6:00之间的签出记录
				List<Attendance> afterAttendance = null;
				if (attendance.getUserId() != null || attendance.getOrgNameId() != null) {
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
				}
				// 考情记录有三种情况。当一天的考勤记录条数等于大于2时,为正常的考勤
				// 大于2时，取集合中的最后一条数据作为考勤记录
				if (attList.size() >= 2) {
					// 获取签入签出时间
					if (attList.get(0).getTime().before(attList.get(attList.size() - 1).getTime())) {
						// 上班
						attendanceTime.setCheckIn(new Date(attList.get(0).getTime().getTime()));
						// 下班
						attendanceTime.setCheckOut(new Date(attList.get(attList.size() - 1).getTime().getTime()));
					} else {
						// 上班
						attendanceTime.setCheckIn(new Date(attList.get(attList.size() - 1).getTime().getTime()));
						// 下班
						attendanceTime.setCheckOut(new Date(attList.get(0).getTime().getTime()));
					}

					// 工作总时长(签到签出时间总和减去休息时间)
					// 多种情况
					// :1.当签到签出时间同时在休息时间之前2.当签到签出时间都在休息时间之后3.当签到签出时间（任一or全部）在休息时间之间（当出现这种情况
					// , 均不用计算休息时间）
					// 在上午同时签到签出
					if (attendanceTime.getCheckIn().compareTo(restBeginTime) != 1
							&& attendanceTime.getCheckOut().compareTo(restBeginTime) != 1) {
						attendanceTime.setWorkTime(
								DatesUtil.getTimeHour(attendanceTime.getCheckIn(), attendanceTime.getCheckOut()));
					} else
					// 在下午同时签到签出
					if (attendanceTime.getCheckIn().compareTo(restEndTime) != -1
							&& attendanceTime.getCheckOut().compareTo(restEndTime) != -1) {
						attendanceTime.setWorkTime(DatesUtil.getTimeHour(attendanceTime.getCheckIn(),
								attendanceTime.getCheckOut().after(workTimeEnd) ? workTimeEnd
										: attendanceTime.getCheckOut()));
					} else
					// 当签出时间在休息时间之间 （从签出时间到休息时间开始）
					if (attendanceTime.getCheckOut().compareTo(restBeginTime) != -1
							&& attendanceTime.getCheckOut().compareTo(restEndTime) != 1) {
						attendanceTime.setWorkTime(DatesUtil.getTimeHour(attendanceTime.getCheckIn(), restBeginTime));
					} else
					// 当签入时间在休息时间之间 （从休息时间结束到签出时间）
					if (attendanceTime.getCheckIn().compareTo(restBeginTime) != -1
							&& attendanceTime.getCheckIn().compareTo(restEndTime) != 1) {
						attendanceTime.setWorkTime(
								DatesUtil.getTimeHour(restEndTime, attendanceTime.getCheckOut().after(workTimeEnd)
										? workTimeEnd : attendanceTime.getCheckOut()));
					} else {
						// 实际工作时长
						attendanceTime
								.setWorkTime(
										NumUtils.sub(
												DatesUtil.getTimeHour(
														// 签入小于等于工作开始时间时，取工作开始时间计算，否则取签入时间
														attendanceTime.getCheckIn().compareTo(workTime) != 1 ? workTime
																: attendanceTime.getCheckIn(),
														// 签出大于等于工作结束时间时，取工作开始时间计算，否则取签出时间
														attendanceTime.getCheckOut().compareTo(workTimeEnd) != -1
																? workTimeEnd : attendanceTime.getCheckOut()),
												restTime));
					}

					// 当休息日有打卡记录时，不需要申请加班的人自动算加班时长
					if (rout) {
						attendanceTime.setFlag(3);
						if (attendanceInit.getOverTimeType() == 2 && attendanceTime.getCheckIn() != null
								&& attendanceTime.getCheckOut() != null) {
							if (attendanceInit.getRestTimeWork() == 3) {
								attendanceTime.setOvertime(
										DatesUtil.getTimeHour(attendanceTime.getCheckIn().before(workTime) ? workTime
												: attendanceTime.getCheckIn(), attendanceTime.getCheckOut()));
							} else {
								attendanceTime.setOvertime(NumUtils.sub(
										DatesUtil.getTimeHour(attendanceTime.getCheckIn().before(workTime) ? workTime
												: attendanceTime.getCheckIn(), attendanceTime.getCheckOut()),
										attendanceTime.getCheckOut().after(restEndTime) ? restTime : 0));
							}
							// 设定加班后可以晚到岗加班时间
							if (sign) {
								if (attendanceTime.getCheckIn().compareTo(DatesUtil.getDaySum(workTime, minute)) != 1) {
									attendanceTime.setOvertime(attendanceInit.getRestTimeWork() == 3
											? DatesUtil.getTimeHour(workTime, attendanceTime.getCheckOut())
											: NumUtils.sub(
													DatesUtil.getTimeHour(workTime, attendanceTime.getCheckOut()),
													attendanceTime.getCheckOut().after(restEndTime) ? restTime : 0));
								}
							}
							attendanceTime.setOrdinaryOvertime(attendanceTime.getOvertime());
						}
						attendanceTimeList.add(attendanceTime);
						continue;
					}

					// 当外协部或者物流部有打卡记录时，按打开记录核算考勤
					if (us.getOrgNameId() != null && us.getOrgNameId() != 45 && us.getOrgNameId() != 23) {
						// 无到岗要求和无打卡要求
						if (attendanceInit.getWorkType() == 1 || attendanceInit.getWorkType() == 2) {
							attendanceTime.setFlag(0);
							attendanceTime.setTurnWorkTime(turnWorkTime);
							attendanceTimeList.add(attendanceTime);
							continue;
						}
					}

					// 进行出勤，加班，缺勤，迟到，早退的计算
					AttendanceTool.attendanceIntTool(sign, workTime, workTimeEnd, restBeginTime, restEndTime, minute,
							turnWorkTime, attendanceTime, attendanceInit, us, restTime);
				}
				// 当一天的考勤记录条数小于2时。为异常的考勤
				if (attList.size() < 2) {
					if (attList.size() == 1) {
						attendanceTime.setCheckIn(new Date(attList.get(0).getTime().getTime()));
					}
					if (rout) {
						attendanceTime.setFlag(3);
						attendanceTimeList.add(attendanceTime);
						continue;
					}
					// 无到岗要求和无打卡要求
					if (attendanceInit.getWorkType() == 1 || attendanceInit.getWorkType() == 2) {
						attendanceTime.setFlag(0);
						attendanceTime.setTurnWorkTime(turnWorkTime);
						attendanceTimeList.add(attendanceTime);
						continue;
					}
					// 缺勤时间（公司未规定放假日期，所以当员工没有打卡记录时，统一算缺勤)
					attendanceTime.setDutytime(NumUtils.sub(turnWorkTime, 0));
					attendanceTime.setFlag(1);
				}
				attendanceTimeList.add(attendanceTime);

			}
			if (!exUser.equals("")) {
				throw new ServiceException(exUser + "没有考勤设定数据，请填写后操作");
			}
			// 循环一次结束后，获取下一天的签到开始时间,6点开始
			beginTimes = DatesUtil.nextDay(beginTimes);
		}
		return attendanceTimeList;
	}

	@Override
	public List<Map<String, Object>> findAttendanceTimeCollectAdd(AttendanceTime attendanceTime) throws ParseException {
		return attendanceCollect(
				saveAttendanceTimeList(attendanceTimeByApplication(findAttendanceTime(attendanceTime))), true);
	}

	@Override
	public List<Map<String, Object>> findAttendanceTimeCollect(AttendanceTime attendanceTime) throws ParseException {
		return attendanceCollect(attendanceTimeByApplication(findAttendanceTime(attendanceTime)), false);
	}

	@Override
	public List<Map<String, Object>> findAttendanceTimeCollectList(AttendanceTime attendanceTime)
			throws ParseException {
		attendanceTime.setOrderTimeEnd(DatesUtil.getLastDayOfMonth(attendanceTime.getOrderTimeBegin()));
		// 最外层循环人员list
		List<Map<String, Object>> allList = new ArrayList<>();
		// 单向数据map
		Map<String, Object> allMap = null;
		List<AttendanceTime> attendanceTimeList = findAttendanceTimePage(attendanceTime);
		// 按人员分组
		Map<Long, List<AttendanceTime>> mapAttendance = attendanceTimeList.stream()
				.filter(AttendanceTime -> AttendanceTime.getUserId() != null)
				.collect(Collectors.groupingBy(AttendanceTime::getUserId, Collectors.toList()));
		for (Long ps1 : mapAttendance.keySet()) {
			allMap = new HashMap<>();
			// 获取单一员工日期区间所有的考勤数据
			List<AttendanceTime> psList1 = mapAttendance.get(ps1);
			// 按日期自然排序
			List<AttendanceTime> attendanceTimeList1 = psList1.stream()
					.sorted(Comparator.comparing(AttendanceTime::getTime)).collect(Collectors.toList());
			AttendanceCollect attendanceCollect = attendanceCollectDao.findByUserIdAndTime(ps1,
					attendanceTime.getOrderTimeBegin());
			for (AttendanceTime at : attendanceTimeList1) {
				at.setUserName(at.getUser().getUserName());
				at.setUser(null);
			}
			attendanceCollect.setUser(null);
			allMap.put("attendanceTimeData", attendanceTimeList1);
			allMap.put("collect", attendanceCollect);
			allList.add(allMap);
		}
		return allList;
	}

	@Override
	@Transactional
	public boolean deleteAttendanceTimeCollect(AttendanceTime attendanceTime) {
		List<AttendanceTime> attendanceTimeList = findAttendanceTimePage(attendanceTime);
		// 按人员分组
		Map<Long, List<AttendanceTime>> mapAttendance = attendanceTimeList.stream()
				.filter(AttendanceTime -> AttendanceTime.getUserId() != null)
				.collect(Collectors.groupingBy(AttendanceTime::getUserId, Collectors.toList()));
		for (Long ps1 : mapAttendance.keySet()) {
			// 获取单一员工日期区间所有的考勤数据
			List<AttendanceTime> psList1 = mapAttendance.get(ps1);
			// 按日期自然排序
			List<AttendanceTime> attendanceTimeList1 = psList1.stream()
					.sorted(Comparator.comparing(AttendanceTime::getTime)).collect(Collectors.toList());
			AttendanceCollect attendanceCollect = attendanceCollectDao.findByUserIdAndTime(ps1,
					attendanceTime.getOrderTimeBegin());
			if (attendanceCollect != null) {
				attendanceCollectDao.delete(attendanceCollect);
			}
		}
		dao.delete(attendanceTimeList);
		return true;
	}

	/**
	 * 通过传入的工作情况，按人员汇总出考勤数据
	 */
	@Transactional
	private List<Map<String, Object>> attendanceCollect(List<AttendanceTime> attendanceTimeList, boolean sign) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		// 最外层循环人员list
		List<Map<String, Object>> allList = new ArrayList<>();
		// 单向数据map
		Map<String, Object> allMap = null;
		// 按人员分组
		Map<Long, List<AttendanceTime>> mapAttendance = attendanceTimeList.stream()
				.filter(AttendanceTime -> AttendanceTime.getUserId() != null)
				.collect(Collectors.groupingBy(AttendanceTime::getUserId, Collectors.toList()));
		for (Long ps1 : mapAttendance.keySet()) {
			allMap = new HashMap<>();
			// 获取单一员工日期区间所有的考勤数据
			List<AttendanceTime> psList1 = mapAttendance.get(ps1);
			// 按日期自然排序
			List<AttendanceTime> attendanceTimeList1 = psList1.stream()
					.sorted(Comparator.comparing(AttendanceTime::getTime)).collect(Collectors.toList());
			AttendanceCollect attendanceCollect = new AttendanceCollect(psList1);
			if (sign) {
				attendanceCollectDao.save(attendanceCollect);
			}
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
		dao.save(oldAttendanceTime);
		attendanceTime.setOrderTimeBegin(DatesUtil.getFirstDayOfMonth(oldAttendanceTime.getTime()));
		attendanceTime.setOrderTimeEnd(DatesUtil.getLastDayOfMonth(oldAttendanceTime.getTime()));
		attendanceTime.setUserId(oldAttendanceTime.getUserId());
		List<AttendanceTime> attendanceTimeList = findAttendanceTimePage(attendanceTime);
		AttendanceCollect attendanceCollect = attendanceCollectDao.findByUserIdAndTime(attendanceTime.getUserId(),
				DatesUtil.getFirstDayOfMonth(oldAttendanceTime.getTime()));
		if (attendanceCollect != null) {
			attendanceCollectDao.delete(attendanceCollect);
		}
		attendanceCollectDao.save(new AttendanceCollect(attendanceTimeList));
		return attendanceTime;
	}

	@Override
	public void checkAttendanceTime(AttendanceTime attendanceTime) {
		String exTwo = "";
		List<User> userList = new ArrayList<>();
		if (new Date().before(DatesUtil.getLastDayOfMonth(attendanceTime.getOrderTimeBegin()))) {
			throw new ServiceException("选择日期的签到记录未完成,无法统计");
		}
		;
		if (!StringUtils.isEmpty(attendanceTime.getOrgNameId())) {
			userList = userService.findByOrgNameId(attendanceTime.getOrgNameId());
		}
		if (!StringUtils.isEmpty(attendanceTime.getUserId())) {
			User user = userService.findOne(attendanceTime.getUserId());
			userList.add(user);
		}
		for (User user : userList) {
			// 查询该月有没有初始化
			AttendanceCollect attendanceCollect = attendanceCollectDao.findByUserIdAndTime(user.getId(),
					attendanceTime.getOrderTimeBegin());
			// 已封存
			if (attendanceCollect != null) {
				exTwo += attendanceCollect.getSeal() == 1 ? user.getUserName() + "," : "";
			}
		}
		if (!exTwo.equals("")) {
			throw new ServiceException(
					"员工" + exTwo + "在" + (new SimpleDateFormat("yyyy-MM")).format(attendanceTime.getOrderTimeBegin())
							+ "月份的考勤已经存档，不能重新计算");
		}
	}

	@Override
	public List<AttendanceTime> findAttendanceTimePage(AttendanceTime param) {
		List<AttendanceTime> list = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按用户 id过滤
			if (param.getUserId() != null) {
				predicate.add(cb.equal(root.get("userId").as(Long.class), param.getUserId()));
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
		});
		return list;
	}

	@Override
	@Transactional
	public List<AttendanceTime> attendanceTimeByApplication(List<AttendanceTime> attendanceTimeList)
			throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		// 按人员分组
		Map<Long, List<AttendanceTime>> mapAttendanceTime = attendanceTimeList.stream()
				.collect(Collectors.groupingBy(AttendanceTime::getUserId, Collectors.toList()));
		for (Long ps1 : mapAttendanceTime.keySet()) {
			// 获取单一员工日期区间所有的请假事项
			List<AttendanceTime> psList1 = mapAttendanceTime.get(ps1);
			// 按考勤数据日期自然排序
			List<AttendanceTime> attendanceTimeListSort = psList1.stream()
					.sorted(Comparator.comparing(AttendanceTime::getTime)).collect(Collectors.toList());
			for (AttendanceTime at : attendanceTimeListSort) {
				// 检查当前月份属于夏令时或冬令时 flag=ture 为夏令时
				boolean flag = DatesUtil.belongCalendar(at.getTime());

				// 获取员工考勤的初始化参数
				AttendanceInit attendanceInit = attendanceInitService.findByUserId(at.getUserId());
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
				// 查找出申请时间是当月的有符合该员工请假事项
				List<ApplicationLeave> applicationLeave = applicationLeaveDao.findByUserIdAndWriteTimeBetween(
						at.getUserId(), DatesUtil.getFirstDayOfMonth(at.getTime()),
						DatesUtil.getLastDayOfMonth(at.getTime()));
				for (ApplicationLeave al : applicationLeave) {
					JSONArray jsonArray = JSON.parseArray(al.getTime());
					for (int i = 0; i < jsonArray.size(); i++) {
						JSONObject jsonObject = jsonArray.getJSONObject(i);
						String date = jsonObject.getString("date");
						Double time = Double.valueOf(jsonObject.getString("time"));

						// 获取时间区间
						String[] dateArr = date.split("~");
						Date dateLeave = null;
						if (dateArr.length < 2) {
							dateLeave = sdf.parse(date);
							// flag=ture 为夏令时
							if (flag) {
								String[] workTimeArr = attendanceInit.getWorkTimeSummer().split(" - ");
								// 将 工作间隔开始结束时间转换成当前日期的时间
								workTime = DatesUtil.dayTime(dateLeave, workTimeArr[0]);
								workTimeEnd = DatesUtil.dayTime(dateLeave, workTimeArr[1]);
								String[] restTimeArr = attendanceInit.getRestTimeSummer().split(" - ");
								// 将 休息间隔开始结束时间转换成当前日期的时间
								restBeginTime = DatesUtil.dayTime(dateLeave, restTimeArr[0]);
								restEndTime = DatesUtil.dayTime(dateLeave, restTimeArr[1]);
								restTime = attendanceInit.getRestSummer();
								turnWorkTime = attendanceInit.getTurnWorkTimeSummer();
							} else {
								// 冬令时
								String[] workTimeArr = attendanceInit.getWorkTimeWinter().split(" - ");
								// 将 工作间隔开始结束时间转换成当前日期的时间
								workTime = DatesUtil.dayTime(dateLeave, workTimeArr[0]);
								workTimeEnd = DatesUtil.dayTime(dateLeave, workTimeArr[1]);
								// 将 休息间隔开始结束时间转换成当前日期的时间
								String[] restTimeArr = attendanceInit.getRestTimeWinter().split(" - ");
								// 将 休息间隔开始结束时间转换成当前日期的时间
								restBeginTime = DatesUtil.dayTime(dateLeave, restTimeArr[0]);
								restEndTime = DatesUtil.dayTime(dateLeave, restTimeArr[1]);
								restTime = attendanceInit.getRestWinter();
								turnWorkTime = attendanceInit.getTurnWorkTimeWinter();
							}
						}

						// 请假
						if (al.isHoliday() && dateArr.length >= 2) {
							// 获取请假所有日期
							List<Date> dateList = DatesUtil.getPerDaysByStartAndEndDate(dateArr[0], dateArr[1],
									"yyyy-MM-dd");
							for (Date inTime : dateList) {
								// flag=ture 为夏令时
								if (flag) {
									String[] workTimeArr = attendanceInit.getWorkTimeSummer().split(" - ");
									// 将 工作间隔开始结束时间转换成当前日期的时间
									workTime = DatesUtil.dayTime(inTime, workTimeArr[0]);
									workTimeEnd = DatesUtil.dayTime(inTime, workTimeArr[1]);
									String[] restTimeArr = attendanceInit.getRestTimeSummer().split(" - ");
									// 将 休息间隔开始结束时间转换成当前日期的时间
									restBeginTime = DatesUtil.dayTime(inTime, restTimeArr[0]);
									restEndTime = DatesUtil.dayTime(inTime, restTimeArr[1]);
									restTime = attendanceInit.getRestSummer();
									turnWorkTime = attendanceInit.getTurnWorkTimeSummer();
								} else {
									// 冬令时
									String[] workTimeArr = attendanceInit.getWorkTimeWinter().split(" - ");
									// 将 工作间隔开始结束时间转换成当前日期的时间
									workTime = DatesUtil.dayTime(inTime, workTimeArr[0]);
									workTimeEnd = DatesUtil.dayTime(inTime, workTimeArr[1]);
									// 将 休息间隔开始结束时间转换成当前日期的时间
									String[] restTimeArr = attendanceInit.getRestTimeWinter().split(" - ");
									// 将 休息间隔开始结束时间转换成当前日期的时间
									restBeginTime = DatesUtil.dayTime(inTime, restTimeArr[0]);
									restEndTime = DatesUtil.dayTime(inTime, restTimeArr[1]);
									restTime = attendanceInit.getRestWinter();
									turnWorkTime = attendanceInit.getTurnWorkTimeWinter();
								}
								List<AttendanceTime> oneAtList = attendanceTimeListSort.stream()
										.filter(AttendanceTime -> (AttendanceTime.getTime().compareTo(inTime)) == 0)
										.collect(Collectors.toList());
								if (oneAtList.size() > 0) {
									if (al.getHolidayType() == 6) {
										// 当请假时间大于或等于迟到时间
										if (NumUtils.mul(time, 60) >= oneAtList.get(0).getBelateTime()
												|| NumUtils.mul(time, 60) >= oneAtList.get(0).getLeaveEarlyTime()) {
											oneAtList.get(0).setBelate(0);
											oneAtList.get(0).setBelateTime(0.0);
											oneAtList.get(0).setLeaveEarly(0);
											oneAtList.get(0).setLeaveEarlyTime(0.0);
										}
									}
									// 变更为请假状态
									oneAtList.get(0).setFlag(2);
									oneAtList.get(0).setHolidayType(al.getHolidayType());
									oneAtList.get(0).setDutytime(time >= turnWorkTime ? turnWorkTime : time);
									oneAtList.get(0).setTurnWorkTime(
											NumUtils.sub(turnWorkTime, oneAtList.get(0).getDutytime()));
									if (time >= turnWorkTime) {
										time = NumUtils.sub(time, turnWorkTime);
										oneAtList.get(0).setLeaveTime(turnWorkTime);
									} else {
										oneAtList.get(0).setLeaveTime(time);
									}
									oneAtList.get(0).setHolidayDetail(al.getHolidayDetail());
								}
							}
						}

						// 加班
						if (al.isApplyOvertime() && at.getTime().compareTo(dateLeave) == 0) {
							if (al.getOvertimeType() == 2) {
								at.setOvertime(NumUtils.sub(NumUtils.setzro(at.getOvertime()), time));
							} else {
								at.setOvertime(NumUtils.sum(NumUtils.setzro(at.getOvertime()), time));
							}

							if (al.getOvertimeType() == 1) {
								at.setOrdinaryOvertime(NumUtils.sum(NumUtils.setzro(at.getOrdinaryOvertime()), time));
							}
							if (al.getOvertimeType() == 3) {
								at.setProductionOvertime(
										NumUtils.sum(NumUtils.setzro(at.getProductionOvertime()), time));
							}
						}
						// 调休且员工出勤时间等于调休到的那一天
						if (al.isTradeDays() && at.getTime().compareTo(dateLeave) == 0) {
							at.setTakeWork(NumUtils.sum(NumUtils.setzro(at.getTakeWork()), time));
							if (at.getDutytime() != 0) {
								at.setTurnWorkTime(NumUtils.sum(at.getTurnWorkTime(), time));
								at.setDutytime(NumUtils.sub(at.getDutytime(), time));
							}
							// 当调休时间大于或等于迟到时间，
							if (NumUtils.mul(time, 60) >= at.getBelateTime()
									|| NumUtils.mul(time, 60) >= at.getLeaveEarlyTime()) {
								at.setBelate(0);
								at.setBelateTime(0.0);
								at.setLeaveEarly(0);
								at.setLeaveEarlyTime(0.0);
							}
						}
					}
				}
			}
		}
		return attendanceTimeList;
	}

	private List<AttendanceTime> saveAttendanceTimeList(List<AttendanceTime> attendanceTimeList) {
		return dao.save(attendanceTimeList);
	}

	@Override
	public List<Map<String, Object>> syncAttendanceTimeCollect(AttendanceTime attendanceTime) throws ParseException {
		checkAttendanceTime(attendanceTime);
		deleteAttendanceTimeCollect(attendanceTime);
		return findAttendanceTimeCollectAdd(attendanceTime);
	}

	@Override
	public List<Map<String, Object>> workshopAttendanceContrast(AttendanceTime attendanceTime) throws ParseException {
		List<Map<String, Object>> mapList = new ArrayList<>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		attendanceTime.setOrderTimeEnd(DatesUtil.getLastDayOfMonth(attendanceTime.getOrderTimeBegin()));
		Integer type = null;
		switch (String.valueOf(attendanceTime.getOrgNameId())) {
		case Constants.QUALITY_ORGNAME:
			type = 1;
			break;
		case Constants.PACK_ORGNAME:
			type = 2;
			break;
		case Constants.DEEDLE_ORGNAME:
			type = 3;
			break;
		case Constants.MACHINIST_ORGNAME:
			type = 4;
			break;
		case Constants.TAILOR_ORGNAME:
			type = 5;
			break;
		default:
			break;
		}

		// 按打卡记录查出考勤
		List<AttendanceTime> attendanceTimeList = findAttendanceTimePage(attendanceTime);
		if (attendanceTimeList.size() == 0) {
			attendanceTimeList = attendanceTimeByApplication(findAttendanceTime(attendanceTime));
		}
		// 按类型获取车间填写的人工考勤
		List<AttendancePay> attendancePayList = attendancePayDao.findByTypeAndAllotTimeBetween(type,
				attendanceTime.getOrderTimeBegin(), attendanceTime.getOrderTimeEnd());

		Map<Long, List<AttendancePay>> mapAttendance = attendancePayList.stream()
				.filter(AttendancePay -> AttendancePay.getUserId() != null)
				.collect(Collectors.groupingBy(AttendancePay::getUserId, Collectors.toList()));

		Map<Long, List<AttendanceTime>> mapAttendanceTime = attendanceTimeList.stream()
				.filter(AttendanceTime -> AttendanceTime.getUserId() != null)
				.collect(Collectors.groupingBy(AttendanceTime::getUserId, Collectors.toList()));

		for (Long ps1 : mapAttendanceTime.keySet()) {
			// 获取单一员工车间填写的考勤数据 自然排序
			List<AttendancePay> attendancePays = mapAttendance.get(ps1);
			if(attendancePays!=null){
				attendancePays = attendancePays.stream().sorted(Comparator.comparing(AttendancePay::getAllotTime)).collect(Collectors.toList());
			}else{
				continue;
			}
			// 获取单一员工打卡记录的考勤数据 自然排序
			List<AttendanceTime> attendanceTimes = mapAttendanceTime.get(ps1).stream()
					.sorted(Comparator.comparing(AttendanceTime::getTime)).collect(Collectors.toList());
			// 將打卡记录当作循环体，通过日期对比
			for (AttendanceTime aTime : attendanceTimes) {
				List<AttendancePay> asList = attendancePays.stream()
						.filter(AttendancePay -> AttendancePay.getAllotTime().compareTo(aTime.getTime()) == 0)
						.collect(Collectors.toList());
				AttendancePay aPay = null;
				Map<String, Object> map = new HashMap<>();
				if (asList.size() > 0) {
					aPay = asList.get(0);
					map.put("id", aPay.getId());
					map.put("warning", aPay.getWarning());
				} else {
					aPay = new AttendancePay();
					NumUtils.setzro(aPay);
				}
				map.put("date", sdf.format(aTime.getTime()));
				User user = userService.findOne(aTime.getUserId());
				map.put("name",user.getUserName());
				map.put("userId", aTime.getUserId());
				// 针工 （检验 管理 开棉）
				if (user.getGroup()!=null 
						&& user.getGroup().getKindWorkId() != null
						&& user.getGroup().getKindWorkId().equals(113)
						&& user.getGroup().getKindWorkId().equals(116)
						&& user.getGroup().getKindWorkId().equals(120)) {
					if (!aPay.getWorkTime().equals(aTime.getWorkTime())) {
						// 记录工作时长
						map.put("recordTurnWorkTime", aPay.getWorkTime());
						// 打卡工作时长
						map.put("clockInTurnWorkTime", aTime.getWorkTime());
						mapList.add(map);
					}
				} else {
					// 出勤时间比对
					if (!aPay.getTurnWorkTime().equals(aTime.getTurnWorkTime())
							|| !aPay.getOverTime().equals(aTime.getOvertime())) {
						// 记录出勤
						map.put("recordTurnWorkTime", aPay.getTurnWorkTime());
						// 打卡出勤
						map.put("clockInTurnWorkTime", aTime.getTurnWorkTime());
						// 加班时间比对
						// 记录加班
						map.put("recordOverTime", aPay.getOverTime());
						// 打卡加班
						map.put("clockInOvertime", aTime.getOvertime());
						mapList.add(map);
					}
				}
			}
		}
		return mapList;
	}
}
