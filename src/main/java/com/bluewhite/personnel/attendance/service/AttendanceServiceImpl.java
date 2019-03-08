package com.bluewhite.personnel.attendance.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
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
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.common.utils.ZkemUtils.ZkemSDKUtils;
import com.bluewhite.personnel.attendance.dao.AttendanceDao;
import com.bluewhite.personnel.attendance.entity.Attendance;
import com.bluewhite.personnel.attendance.entity.AttendanceCollect;
import com.bluewhite.personnel.attendance.entity.AttendanceTime;
import com.bluewhite.system.user.entity.User;
import com.bluewhite.system.user.service.UserService;

@Service
public class AttendanceServiceImpl extends BaseServiceImpl<Attendance, Long> implements AttendanceService {

	@Autowired
	private UserService userService;

	@Autowired
	private AttendanceDao dao;

	@PersistenceContext
	protected EntityManager entityManager;
	
	@Autowired
	private ZkemSDKUtils sdk;

	@Override
	public List<Map<String, Object>> getAllUser(String address) {
		sdk.initSTA();
		boolean flag = false;
		try {
			flag = sdk.connect(address, 4370);
		} catch (Exception e) {
			throw new ServiceException("考勤机连接失败");
		}
		List<Map<String, Object>> userList = null;
		if (flag) {
			userList = sdk.getUserInfo();
		}
		sdk.disConnect();
		sdk.release();
		return userList;
	}

	@Override
	public int syncAttendanceUser(String address) {
		int count = 0;
		List<Map<String, Object>> userMapList = this.getAllUser(address);
		List<User> userListAll = userService.findByForeigns();
		for (Map<String, Object> map : userMapList) {
			if(userListAll.size()>0){
				List<User> user = userListAll.stream().filter(User->User.getUserName().equals(map.get("name").toString().trim())).collect(Collectors.toList());
				if(user.size()>1){
					throw new ServiceException("系统用户有相同名称的员工"+user.get(0).getUserName() +"，请检查是否重复");
				}
				if(user.size()>0){
					if(user.get(0).getNumber() == null || !user.get(0).getNumber().equals(map.get("number").toString())){
						user.get(0).setNumber(map.get("number").toString());
						userService.save(user.get(0));
						count++;
						System.out.println(user.get(0).getNumber());
					}
				}
				
			}
		}
		return count;
	}

	@Override
	public boolean updateUser(String address, String number, String name, int isPrivilege, boolean enabled) {
		sdk.initSTA();
		boolean flag = false;
		try {
			flag = sdk.connect(address, 4370);
		} catch (Exception e) {
			throw new ServiceException("考勤机连接失败");
		}
		if (flag) {
			flag = sdk.setUserInfo(number, name, null, isPrivilege, enabled);
		}
		sdk.disConnect();
		sdk.release();
		return flag;
	}

	@Override
	public boolean deleteUser(String address, String number) {
		sdk.initSTA();
		boolean flag = false;
		try {
			flag = sdk.connect(address, 4370);
		} catch (Exception e) {
			throw new ServiceException("考勤机连接失败");
		}
		if (flag) {
			flag = sdk.delectUserById(number);
		}
		sdk.disConnect();
		sdk.release();
		return flag;
	}
	
	@Override
	public List<Map<String, Object>>  findUser(String address, String number) {
		sdk.initSTA();
		boolean flag = false;
		List<Map<String, Object>> user = null;
		try {
			flag = sdk.connect(address, 4370);
		} catch (Exception e) {
			throw new ServiceException("考勤机连接失败");
		}
		if (flag) {
			 user = sdk.getUserInfoByNumber(number);
		}
		sdk.disConnect();
		sdk.release();
		return user;
	}
	

	@Override
	@Transactional
	public List<Attendance> allAttendance(String address,Date startTime , Date endTime) {
		sdk.initSTA();
		
		boolean flag = false;
		try {
			flag = sdk.connect(address, 4370);
		} catch (Exception e) {
			throw new ServiceException("考勤机连接失败");
		}
		List<Attendance> attendanceListAll = new ArrayList<>();
		flag = sdk.readGeneralLogData(0);
		if (flag) {
			attendanceListAll = sdk.getGeneralLogData(0);
		}
		attendanceListAll = attendanceListAll.stream().filter(Attendance -> Attendance.getTime().before(endTime) && Attendance.getTime().after(startTime)).collect(Collectors.toList());
		dao.save(attendanceListAll);
		sdk.disConnect();
		sdk.release();
		return attendanceListAll;
	}

	/**
	 * 考勤导入批处理
	 * 
	 * @param productList
	 */
	private void saveAllProduct(List<Attendance> attendanceListAll) {
		entityManager.setFlushMode(FlushModeType.COMMIT);
		for (int i = 0; i < attendanceListAll.size(); i++) {
			Attendance attendance = attendanceListAll.get(i);
			entityManager.merge(attendance);
			if (i % 1000 == 0 && i > 0) {
				entityManager.flush();
				entityManager.clear();
			}
		}
		entityManager.close();
	}

	@Override
	public PageResult<Attendance> findPageAttendance(Attendance param, PageParameter page) {
		Page<Attendance> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按用户 id过滤
			if (param.getUserId() != null) {
				predicate.add(cb.equal(root.get("userId").as(Long.class), param.getUserId()));
			}

			// 按姓名查找
			if (!StringUtils.isEmpty(param.getUserName())) {
				predicate.add(
						cb.equal(root.get("user").get("userName").as(String.class),param.getUserName()));
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
		}, page);
		PageResult<Attendance> result = new PageResult<>(pages, page);
		return result;
	}

	public void regEvent() {
		sdk.initSTA();
		try {
			System.out.println("考勤机实时事件启动");
			sdk.connect("192.168.1.204", 4370);
			// sdk.connect("192.168.1.205", 4370);
			// sdk.connect("192.168.1.250", 4370);
			sdk.regEvent();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<AttendanceTime> findAttendanceTime(Attendance attendance) {
		if(attendance.getRestTime()==null){
			throw new ServiceException("休息时间不能为空");
		}
		PageParameter page = new PageParameter();
		page.setSize(Integer.MAX_VALUE);
		long size = DatesUtil.getDaySub(attendance.getOrderTimeBegin(),DatesUtil.getLastDayOftime(attendance.getOrderTimeEnd()));
		List<AttendanceTime> attendanceTimeList = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			Date beginTimes = null;
			if (i != 0) {
				// 获取一天的签到开始时间
				beginTimes = DatesUtil.nextDay(attendance.getOrderTimeBegin());
			} else {
				// 获取第一天的签到开始时间
				beginTimes = attendance.getOrderTimeBegin();
			}
			//获取一天的签到结束时间,延后6个小时
			Date endTimes = DatesUtil.dayTime(DatesUtil.getLastDayOftime(DatesUtil.nextDay(beginTimes)), "06:00:00");
			//从5点开始新一天的签到
			Date beginTimes1 = DatesUtil.dayTime(beginTimes,"06:00:00");
			attendance.setOrderTimeBegin(beginTimes1);
			attendance.setOrderTimeEnd(endTimes);
			//当部门不为null时，按部门查找出所有的人员
			List<User> userList = new ArrayList<>();
			if(!StringUtils.isEmpty(attendance.getOrgNameId())){
				 userList = userService.findByOrgNameId(attendance.getOrgNameId());
			}
			if(!StringUtils.isEmpty(attendance.getUserName())){
				User user = userService.findByUserName(attendance.getUserName());
				userList.add(user);
			}
			for(User us : userList){
				attendance.setUserId(us.getId());
				List<Attendance> attList = this.findPageAttendance(attendance, page).getRows();
				if (attList.size() > 0) {
					List<Attendance> list = new ArrayList<Attendance>();
						// 获取每个人当天的考勤记录
						AttendanceTime attendanceTime = new AttendanceTime();
						attendanceTime.setTime(beginTimes);
						attendanceTime.setUsername(attList.get(0).getUser().getUserName());
						attendanceTime.setNumber(attList.get(0).getNumber());
						attendanceTime.setWeek(DatesUtil.dateToWeek(beginTimes));
						// 考情记录有三种情况。当一天的考勤记录条数等于大于2时,为正常的考勤
						// 大于2时，取集合中的最后一条数据作为考勤记录 
						if (attList.size() >= 2) {
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
							
							//将 工作间隔结束时间转换成当前日期的时间
							Date workTime =DatesUtil.dayTime(beginTimes, attendance.getWorkTimeBegin()) ;
							Date workTimeEnd =DatesUtil.dayTime(beginTimes, attendance.getWorkTimeEnd()) ;
							
							//工作总时长
							attendanceTime.setWorkTime(
									NumUtils.sub(
											DatesUtil.getTimeHour( attendanceTime.getCheckIn(),attendanceTime.getCheckOut()),
											attendance.getRestTime()
											));
							// 出勤时长
							Double turnWorkTime = NumUtils.sub(
									DatesUtil.getTimeHour( workTime,workTimeEnd),
									attendance.getRestTime()
									);
							attendanceTime.setTurnWorkTime(attendanceTime.getWorkTime()>=turnWorkTime ? turnWorkTime : attendanceTime.getWorkTime());
							if(attendanceTime.getWorkTime()<turnWorkTime){
								attendanceTime.setDutytime(NumUtils.sub(turnWorkTime,attendanceTime.getWorkTime()));
							}
							// 加班时间
							if (workTime.before(attendanceTime.getCheckOut())) {
								attendanceTime.setOvertime( DatesUtil.getTimeHour(workTimeEnd,attendanceTime.getCheckOut()));
							}
							
						}
						
						// 当一天的考勤记录条数小于2时。为异常的考勤
						if (attList.size() < 2) {
							Date workTimeEnd =DatesUtil.dayTime(beginTimes, attendance.getWorkTimeEnd()) ;
							if (attList.get(0).getTime().before(workTimeEnd)) {
								// 上班
								attendanceTime.setCheckIn(attList.get(0).getTime());
							}else{
								// 下班
								attendanceTime.setCheckOut(attList.get(0).getTime());
							}
						}
						attendanceTimeList.add(attendanceTime);
					
				}else{
					//当按人名查找没有签到记录时，将这一天的考情状态修改
					AttendanceTime attendanceTime = new AttendanceTime();
					SimpleDateFormat sdf =   new SimpleDateFormat("yyyy-MM-dd"); 
					attendanceTime.setTime(beginTimes);
					attendanceTime.setUsername(us.getUserName());
					attendanceTime.setNumber(us.getNumber());
					attendanceTime.setWeek(DatesUtil.dateToWeek(beginTimes));
					attendanceTimeList.add(attendanceTime);
				}
			}
		}
			//根据员工编号自然顺序
	      List<AttendanceTime> attendanceTimeList1 =
	    		  attendanceTimeList.stream().filter(AttendanceTime->AttendanceTime.getNumber()!=null).sorted(Comparator.comparing(AttendanceTime::getNumber)).collect(Collectors.toList());
		return attendanceTimeList1;
	}
	
	
	
	/**
	 * 通过传入的工作情况，按人员汇总出考勤数据
	 */
	private List<Map<String, Object>> AttendanceCollect( List<AttendanceTime> attendanceTimeList){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");  
		//最外层循环人员list
		List<Map<String, Object>> allList = new ArrayList<>();
		//单向数据map
		Map<String, Object> allMap = null;
		//按人员分组
		Map<String, List<AttendanceTime>> mapAttendance =
	    		  attendanceTimeList.stream().filter(AttendanceTime->AttendanceTime.getNumber()!=null).collect(Collectors.groupingBy(AttendanceTime::getNumber,Collectors.toList()));
		for(String ps1 : mapAttendance.keySet()){
			allMap = new HashMap<>();
			//获取单一员工日期区间所有的考勤数据
			List<AttendanceTime> psList1= mapAttendance.get(ps1);
			//按日期自然排序
			 List<AttendanceTime> attendanceTimeList1 =	psList1.stream().sorted(Comparator.comparing(AttendanceTime::getTime)).collect(Collectors.toList());
			AttendanceCollect attendanceCollect = new AttendanceCollect(psList1);
			allMap.put("attendanceTimeData", attendanceTimeList1);
			allMap.put("collect", attendanceCollect);
			allList.add(allMap);
		}
		return allList;
	}
	
	
	@Override
	public List<Map<String, Object>> findAttendanceTimeCollect(Attendance attendance) {
		return this.AttendanceCollect(this.findAttendanceTime(attendance));
	}
	
	


	@Override
	public List<Map<String, Object>> getAllAttendance(String address) {
		sdk.initSTA();
		boolean flag = false;
		try {
			flag = sdk.connect(address, 4370);
		} catch (Exception e) {
			throw new ServiceException("考勤机连接失败");
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Map<String, Object>> attendanceList = null;
		flag = sdk.readGeneralLogData(0);
		if (flag) {
//			attendanceList = sdk.getGeneralLogData(0);
		}
		sdk.disConnect();
		sdk.release();
		return attendanceList;
	}
	

	@Override
	@Transactional
	public int fixAttendance(Date startTime, Date endTime) {
		int count = 0;
		Attendance attendance = new Attendance();
		attendance.setOrderTimeBegin(startTime);
		attendance.setOrderTimeEnd(endTime);
		List<Attendance> attendanceList = this.findPageAttendance(attendance, new PageParameter(0, Integer.MAX_VALUE)).getRows();
		if(attendanceList.size()>0){
			Map<String, List<Attendance>> mapAttendance = attendanceList.stream().filter(Attendance->Attendance.getUserId()==null).collect(Collectors.groupingBy(Attendance::getNumber,Collectors.toList()));
			for(String ps1 : mapAttendance.keySet()){
				List<Attendance> psList1= mapAttendance.get(ps1);
				User user = userService.findByNumber(ps1);
				if(user!=null){
					psList1.stream().forEach( item-> {
						item.setUserId(user.getId());
					});
					count++;
				}
			}
		}
		dao.save(attendanceList);
		return count;
	}



}
