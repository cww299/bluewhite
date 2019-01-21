package com.bluewhite.personnel.attendance.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.ServiceException;
import com.bluewhite.common.utils.ZkemUtils.ZkemSDKUtils;
import com.bluewhite.personnel.attendance.entity.Attendance;
import com.bluewhite.system.user.entity.User;
import com.bluewhite.system.user.service.UserService;

@Service
public class AttendanceServiceImpl extends BaseServiceImpl<Attendance, Long> implements AttendanceService {
	
	@Autowired
	private UserService userService;

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
		List<User> userList =  new ArrayList<>();
		for(Map<String, Object> map  : userMapList){
			User user  = userService.findByUserName(map.get("name").toString());
			if(user!=null){
				if(!map.get("number").toString().equals(user.getNumber())){
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
	public boolean updateUser(String address, String number ,String name, int isPrivilege, boolean enabled) {
		ZkemSDKUtils sdk = new ZkemSDKUtils();
		sdk.initSTA();
		boolean flag = false;
		try {
			flag = sdk.connect(address, 4370);
		} catch (Exception e) {
			throw new ServiceException("考勤机连接失败");
		}
		if (flag) {
			flag = sdk.setUserInfo( number,  name, null,  isPrivilege,  enabled);
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
	public List<Map<String, Object>> allAttendance(String address) {
		ZkemSDKUtils sdk = new ZkemSDKUtils();
		sdk.initSTA();
		boolean flag = false;
		try {
			flag = sdk.connect(address, 4370);
		} catch (Exception e) {
			throw new ServiceException("考勤机连接失败");
		}
		List<Map<String, Object>> attendanceList = null;
		if (flag) {
			attendanceList = sdk.getGeneralLogData(0);
		} 
		sdk.disConnect();
		sdk.release();
		return attendanceList;
	}

}
