package com.bluewhite.personnel.attendance.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.Constants;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.zkemUtils.ZkemSDKUtils;
import com.bluewhite.personnel.attendance.dao.AttendanceDao;
import com.bluewhite.personnel.attendance.entity.Attendance;
import com.bluewhite.system.user.entity.User;
import com.bluewhite.system.user.service.UserService;

import cn.hutool.core.util.StrUtil;

@Service
public class AttendanceServiceImpl extends BaseServiceImpl<Attendance, Long> implements AttendanceService {

	@Autowired
	private UserService userService;
	@Autowired
	private AttendanceDao dao;
	@Autowired
	private ZkemSDKUtils sdk;

	@Override
	public List<Map<String, Object>> getAllUser(String address) {
		sdk.initSTA();
		boolean flag = sdk.connect(address, 4370);
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
						.filter(User -> StrUtil.isBlank(User.getNumber()) && User.getUserName().trim().equals(map.get("name").toString().trim()))
						.collect(Collectors.toList());
				if (user.size() > 1) {
					throw new ServiceException("系统用户有相同名称的员工" + user.get(0).getUserName() + "，请检查是否重复");
				}
				if (user.size() > 0) {
					if (user.get(0).getNumber() == null
							|| !user.get(0).getNumber().equals(map.get("number").toString())) {
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
		boolean flag = sdk.connect(address, 4370);
		if (flag) {
			flag = sdk.setUserInfo(number, name, "", isPrivilege, enabled);
		}
		sdk.disConnect();
		sdk.release();
		return flag;
	}

	@Override
	public boolean deleteUser(String address, String number) {
		sdk.initSTA();
		boolean flag = sdk.connect(address, 4370);
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
		boolean flag = sdk.connect(address, 4370);
		List<Map<String, Object>> user = null;
		if (flag) {
			user = sdk.getUserInfoByNumber(number);
		}
		sdk.disConnect();
		sdk.release();
		return user;
	}

	@Override
	@Transactional
	public List<Attendance> allAttendance(String address, Date startTime, Date endTime, Long userId) {
		sdk.initSTA();
		try {
			sdk.connect(address, 4370);
		} catch (Exception e) {
			
		}
		List<Attendance> attendanceListAll = new ArrayList<>();
		boolean flag = sdk.readGeneralLogData(0);
		if (flag) {
			attendanceListAll = sdk.getGeneralLogData(0);
		}
		attendanceListAll = attendanceListAll.stream()
				.filter(Attendance -> Attendance.getTime().before(endTime) && Attendance.getTime().after(startTime))
				.collect(Collectors.toList());
		if (userId != null) {
			attendanceListAll = attendanceListAll.stream()
					.filter(Attendance -> Attendance.getUserId() != null && Attendance.getUserId().equals(userId))
					.collect(Collectors.toList());
		}
		String sourceMachine = null;
		if (Constants.THREE_FLOOR.equals(address)) {
			sourceMachine = "THREE_FLOOR";
		}
//		if (Constants.TWO_FLOOR.equals(address)) {
//			sourceMachine = "TWO_FLOOR";
//		}
		if (Constants.ONE_FLOOR.equals(address)) {
			sourceMachine = "ONE_FLOOR";
		}
		if (Constants.EIGHT_WAREHOUSE.equals(address)) {
			sourceMachine = "EIGHT_WAREHOUSE";
		}
		if (Constants.NEW_IGHT_WAREHOUSE.equals(address)) {
			sourceMachine = "NEW_IGHT_WAREHOUSE";
		}
		if (Constants.ELEVEN_WAREHOUSE.equals(address)) {
			sourceMachine = "ELEVEN_WAREHOUSE";
		}
		String sourceMachineFina = sourceMachine;
		attendanceListAll.stream().forEach(a -> {
			a.setSourceMachine(sourceMachineFina);
		});
		batchSave(attendanceListAll);
		sdk.disConnect();
		sdk.release();
		return attendanceListAll;
	}

	@Override
	public PageResult<Attendance> findPageAttendance(Attendance param, PageParameter page) {
		page.setSort(new Sort(Direction.DESC, "time"));
		String sourceMachine = null;
		if(!StringUtils.isEmpty(param.getSourceMachine())){
			if (Constants.THREE_FLOOR.equals(param.getSourceMachine())) {
				sourceMachine = "THREE_FLOOR";
			}
//			if (Constants.TWO_FLOOR.equals(param.getSourceMachine())) {
//				sourceMachine = "TWO_FLOOR";
//			}
			if (Constants.ONE_FLOOR.equals(param.getSourceMachine())) {
				sourceMachine = "ONE_FLOOR";
			}
			if (Constants.EIGHT_WAREHOUSE.equals(param.getSourceMachine())) {
				sourceMachine = "EIGHT_WAREHOUSE";
			}
			if (Constants.NEW_IGHT_WAREHOUSE.equals(param.getSourceMachine())) {
				sourceMachine = "NEW_IGHT_WAREHOUSE";
			}
			if (Constants.ELEVEN_WAREHOUSE.equals(param.getSourceMachine())) {
				sourceMachine = "ELEVEN_WAREHOUSE";
			}
			param.setSourceMachine(sourceMachine);
		}
		
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

			// 按编号
			if (!StringUtils.isEmpty(param.getNumber())) {
				predicate.add(cb.equal(root.get("number").as(String.class), param.getNumber()));
			}
			
			// 按打卡地点
			if (!StringUtils.isEmpty(param.getSourceMachine())) {
				predicate.add(cb.equal(root.get("sourceMachine").as(String.class), param.getSourceMachine()));
			}

			// 按姓名查找
			if (!StringUtils.isEmpty(param.getUserName())) {
				predicate.add(cb.like(root.get("user").get("userName").as(String.class), "%" + param.getUserName() + "%"));
			}

			// 按部门查找
			if (param.getOrgNameId() != null) {
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

	@Override
	public List<Map<String, Object>> getAllAttendance(String address) {
		sdk.initSTA();
		boolean flag = sdk.connect(address, 4370);
		List<Map<String, Object>> attendanceList = null;
		flag = sdk.readGeneralLogData(0);
		if (flag) {
			// attendanceList = sdk.getGeneralLogData(0);
		}
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
	public int restAttendance(String address, Date startTime, Date endTime, Long userId) {
		Attendance attendance = new Attendance();
		attendance.setOrderTimeBegin(startTime);
		attendance.setOrderTimeEnd(endTime);
		attendance.setUserId(userId);
		attendance.setSourceMachine(address);
		List<Attendance> attendanceList = findPageAttendance(attendance, new PageParameter(0, Integer.MAX_VALUE))
				.getRows().stream()
				.filter(Attendance -> (Attendance.getInOutMode() != null && Attendance.getInOutMode() == 1))
				.collect(Collectors.toList());
		if (attendanceList.size() > 0) {
			dao.delete(attendanceList);
		}
		int count = 0;
		if (StringUtils.isEmpty(address)) {
			List<Attendance> list = allAttendance(Constants.THREE_FLOOR, startTime, endTime, userId);
//			allAttendance(Constants.TWO_FLOOR, startTime, endTime, userId);
			List<Attendance> list1 = allAttendance(Constants.ONE_FLOOR, startTime, endTime, userId);
			List<Attendance> list2 = allAttendance(Constants.EIGHT_WAREHOUSE, startTime, endTime, userId);
			List<Attendance> list3 = allAttendance(Constants.NEW_IGHT_WAREHOUSE, startTime, endTime, userId);
			List<Attendance> list4 = allAttendance(Constants.ELEVEN_WAREHOUSE, startTime, endTime, userId);
			count = list.size()+list1.size()+list2.size()+list3.size()+list4.size();
		} else {
			List<Attendance> list = allAttendance(address, startTime, endTime, userId);
			count = list.size();
		}
		return count;
	}

	@Override
	public Map<String, Object> getUser(String address, String number) {
		sdk.initSTA();
		boolean flag = sdk.connect(address, 4370);
		Map<String, Object> user = null;
		if (flag) {
			user = sdk.getUserInfoTmp(number);
		}
		sdk.disConnect();
		sdk.release();
		return user;
	}

	@Override
	public List<Attendance> findByUserIdInAndTimeBetween(List<Long> userLong, Date beginDate, Date endDate) {
		return dao.findByUserIdInAndTimeBetween(userLong, beginDate, endDate);
	}

	@Override
	public List<Attendance> findByUserIdAndSourceMachineAndTimeBetween(Long userId, String sourceMachine,
			Date startTime, Date endTime) {
		return dao.findByUserIdAndSourceMachineAndTimeBetween(userId,sourceMachine,startTime,endTime);
	}

	@Override
	public List<Attendance> findBySourceMachineAndTimeBetween(String sourceMachine, Date startTime, Date endTime) {
		return dao.findBySourceMachineAndTimeBetween(sourceMachine,startTime,endTime);
	}

}
