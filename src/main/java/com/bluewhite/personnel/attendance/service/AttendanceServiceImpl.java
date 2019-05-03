package com.bluewhite.personnel.attendance.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.ZkemUtils.ZkemSDKUtils;
import com.bluewhite.personnel.attendance.dao.ApplicationLeaveDao;
import com.bluewhite.personnel.attendance.dao.AttendanceDao;
import com.bluewhite.personnel.attendance.dao.AttendanceInitDao;
import com.bluewhite.personnel.attendance.dao.AttendanceTimeDao;
import com.bluewhite.personnel.attendance.entity.Attendance;
import com.bluewhite.system.user.entity.User;
import com.bluewhite.system.user.service.UserService;

@Service
public class AttendanceServiceImpl extends BaseServiceImpl<Attendance, Long> implements AttendanceService {

	@Autowired
	private UserService userService;

	@Autowired
	private AttendanceDao dao;

	@Autowired
	private ZkemSDKUtils sdk;

	@Autowired
	private ApplicationLeaveDao applicationLeaveDao;
	
	@Autowired
	private AttendanceTimeDao attendanceTimeDao;

	@Autowired
	private AttendanceInitDao attendanceInitDao;

	@PersistenceContext
	protected EntityManager entityManager;

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
			if (userListAll.size() > 0) {
				List<User> user = userListAll.stream()
						.filter(User ->User.getNumber() == null && User.getUserName().trim().equals(map.get("name").toString().trim()))
						.collect(Collectors.toList());
				if (user.size() > 1) {
					throw new ServiceException("系统用户有相同名称的员工" + user.get(0).getUserName() + "，请检查是否重复");
				}
				if (user.size() > 0) {
					if (user.get(0).getNumber() == null || !user.get(0).getNumber().equals(map.get("number").toString())) {	
						user.get(0).setNumber(map.get("number").toString());
						userService.save(user.get(0));
						count++;
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
	public List<Map<String, Object>> findUser(String address, String number) {
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
	public List<Attendance> allAttendance(String address, Date startTime, Date endTime) {
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
		attendanceListAll = attendanceListAll.stream()
				.filter(Attendance -> Attendance.getTime().before(endTime) && Attendance.getTime().after(startTime))
				.collect(Collectors.toList());
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
		page.setSort(new Sort(Direction.DESC,"time"));
		Page<Attendance> pages = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			// 按用户 id过滤
			if (param.getUserId() != null) {
				predicate.add(cb.equal(root.get("userId").as(Long.class), param.getUserId()));
			}
			
			// 按签到类型过滤
			if (param.getInOutMode() != null) {
				predicate.add(cb.equal(root.get("inOutMode").as(Integer.class), param.getInOutMode()));
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
		}, page);
		PageResult<Attendance> result = new PageResult<>(pages, page);
		return result;
	}

	public void regEvent() {
		sdk.initSTA();
		try {
			System.out.println("考勤机实时事件启动");
			sdk.connect("192.168.1.204", 4370);
			sdk.regEvent();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			// attendanceList = sdk.getGeneralLogData(0);
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
		List<Attendance> attendanceList = this.findPageAttendance(attendance, new PageParameter(0, Integer.MAX_VALUE))
				.getRows();
		if (attendanceList.size() > 0) {
			Map<String, List<Attendance>> mapAttendance = attendanceList.stream()
					.filter(Attendance -> Attendance.getUserId() == null)
					.collect(Collectors.groupingBy(Attendance::getNumber, Collectors.toList()));
			for (String ps1 : mapAttendance.keySet()) {
				List<Attendance> psList1 = mapAttendance.get(ps1);
				User user = userService.findByNumber(ps1);
				if (user != null) {
					psList1.stream().forEach(item -> {
						item.setUserId(user.getId());
					});
					count++;
				}
			}
		}
		dao.save(attendanceList);
		return count;
	}

	@Override
	@Transactional
	public int restAttendance(String address, Date startTime, Date endTime) {
		Attendance attendance  =  new Attendance();
		attendance.setOrderTimeBegin(startTime);
		attendance.setOrderTimeEnd(endTime);
		List<Attendance> attendanceList = findPageAttendance(attendance, new PageParameter(0, Integer.MAX_VALUE))
				.getRows().stream().filter(Attendance->Attendance.getInOutMode()!=2).collect(Collectors.toList());
		dao.delete(attendanceList);
		allAttendance("192.168.1.204", startTime, endTime);
		allAttendance("192.168.1.205", startTime, endTime);
		allAttendance("192.168.1.250", startTime, endTime);
		return attendanceList.size();
	}

}
