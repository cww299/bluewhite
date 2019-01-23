package com.bluewhite.personnel.attendance.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
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
import com.bluewhite.common.utils.ZkemUtils.ZkemSDKUtils;
import com.bluewhite.finance.ledger.entity.Bill;
import com.bluewhite.personnel.attendance.dao.AttendanceDao;
import com.bluewhite.personnel.attendance.entity.Attendance;
import com.bluewhite.personnel.attendance.entity.AttendanceTime;
import com.bluewhite.product.product.entity.Product;
import com.bluewhite.production.finance.entity.CollectPay;
import com.bluewhite.system.user.entity.User;
import com.bluewhite.system.user.service.UserService;
import com.mysql.fabric.xmlrpc.base.Array;

@Service
public class AttendanceServiceImpl extends BaseServiceImpl<Attendance, Long> implements AttendanceService {

	@Autowired
	private UserService userService;

	@Autowired
	private AttendanceDao dao;

	@PersistenceContext
	protected EntityManager entityManager;

	@Override
	public List<Map<String, Object>> getAllUser(String address) {
		ZkemSDKUtils sdk = new ZkemSDKUtils();
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
		List<User> userList = new ArrayList<>();
		for (Map<String, Object> map : userMapList) {
			User user = userService.findByUserName(map.get("name").toString());
			if (user != null) {
				if (!map.get("number").toString().equals(user.getNumber())) {
					user.setNumber(map.get("number").toString());
					userList.add(user);
					count++;
				}
			}
		}
		userService.save(userList);
		return count;
	}

	@Override
	public boolean updateUser(String address, String number, String name, int isPrivilege, boolean enabled) {
		ZkemSDKUtils sdk = new ZkemSDKUtils();
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
		ZkemSDKUtils sdk = new ZkemSDKUtils();
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
	@Transactional
	public List<Attendance> allAttendance(String address) {
		ZkemSDKUtils sdk = new ZkemSDKUtils();
		sdk.initSTA();
		boolean flag = false;
		try {
			flag = sdk.connect(address, 4370);
		} catch (Exception e) {
			throw new ServiceException("考勤机连接失败");
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<Map<String, Object>> attendanceList = null;
		List<Attendance> attendanceListAll = new ArrayList<>();
		flag = sdk.readGeneralLogData(0);
		if (flag) {
			attendanceList = sdk.getGeneralLogData(0);
		}
		for (Map<String, Object> attendance : attendanceList) {
			Attendance attendance1 = new Attendance();
			attendance1.setNumber(attendance.get("EnrollNumber").toString());
			try {
				attendance1.setTime(sdf.parse(attendance.get("Time").toString()));
			} catch (ParseException e) {
				throw new ServiceException("时间转换异常");
			}
			attendance1.setVerifyMode(Integer.parseInt(String.valueOf(attendance.get("VerifyMode"))));
			User user = userService.findByNumber(attendance.get("EnrollNumber").toString());
			if (user != null) {
				attendance1.setUserId(user.getId());
				attendanceListAll.add(attendance1);
			}
		}
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
						cb.like(root.get("user").get("userName").as(String.class), "%" + param.getUserName() + "%"));
			}
			
			// 按部门查找
			if (!StringUtils.isEmpty(param.getOrgNameId())) {
				predicate.add(cb.equal(root.get("user").get("orgNameId").as(Long.class),  param.getOrgNameId()));
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
		ZkemSDKUtils sdk = new ZkemSDKUtils();
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
	public List<Attendance> findAttendanceTime(Attendance attendance) {
		PageParameter page = new PageParameter();
		page.setSize(Integer.MAX_VALUE);

		long size = DatesUtil.getDaySub(attendance.getOrderTimeBegin(), attendance.getOrderTimeEnd());
		for (int i = 0; i < size; i++) {
			Date beginTimes = null;
			if (i != 0) {
				// 获取下一天的时间
				beginTimes = DatesUtil.nextDay(attendance.getOrderTimeBegin());
			} else {
				// 获取第一天的开始时间
				beginTimes = attendance.getOrderTimeBegin();
			}
			// 获取一天的结束时间
			Date endTimes = DatesUtil.getLastDayOftime(beginTimes);
			List<Attendance> attendanceList = this.findPageAttendance(attendance, page).getRows();
			if (attendanceList.size() > 0) {
				Map<Object, List<Attendance>> mapAttendance = attendanceList.stream().collect(Collectors.groupingBy(Attendance::getUserId,Collectors.toList()));
				
				List<Attendance> list = new ArrayList<Attendance>();
				for(Object ps : mapAttendance.keySet()){
					//获取每个人当天的考勤记录
					List<Attendance> attList= mapAttendance.get(ps);
					//考情记录有三种情况。当一天的考勤记录条数等于2时。为正常的考勤
					if(attList.size()==2){
							AttendanceTime attendanceTime = new AttendanceTime();
							attendanceTime.setUsername(attList.get(0).getUser().getUserName());
							attendanceTime.setNumber(attList.get(0).getNumber());
							attendanceTime.setCheckIn(attList.get(0).getTime());
							attendanceTime.setCheckOut(attList.get(1).getTime());
							
							
							
//							attendanceTime.setTurnWorkTime();
							
						
					}
					//当一天的考勤记录条数小于2时。为异常的考勤
					if(attList.size()<2){
						for(Attendance attendance1 : attList){
							AttendanceTime attendanceTime = new AttendanceTime();
							attendanceTime.setUsername(attList.get(0).getUser().getUserName());
							attendanceTime.setNumber(attList.get(0).getNumber());
							attendanceTime.setCheckIn(attList.get(0).getTime());
							attendanceTime.setCheckOut(attList.get(1).getTime());
							
						}
					}
					//当一天的考勤记录条数大于2时。为异常的考勤
					if(attList.size()>2){
						for(Attendance attendance1 : attList){
							AttendanceTime attendanceTime = new AttendanceTime();
							attendanceTime.setUsername(attList.get(0).getUser().getUserName());
							attendanceTime.setNumber(attList.get(0).getNumber());
							attendanceTime.setCheckIn(attList.get(0).getTime());
							attendanceTime.setCheckOut(attList.get(1).getTime());
							
						}
					}
					
					
				}
		
				
				
				
				

			}
		}

		return null;
	}
	
	
	/**
	 * 确认考勤签到时间在设定上班时间之前，
	 * 
	 * 确认考勤签出时间在设定上班时间之后，
	 * 
	 */
	
	private boolean flag (){
		boolean flag = false;
		
		
		
		
		
		return flag;
		
	}

	@Override
	public List<Map<String, Object>> getAllAttendance(String address) {
		ZkemSDKUtils sdk = new ZkemSDKUtils();
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
			attendanceList = sdk.getGeneralLogData(0);
		}
		sdk.disConnect();
		sdk.release();
		return attendanceList;
	}

}
