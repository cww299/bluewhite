package com.bluewhite.common.utils.ZkemUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.bluewhite.common.ServiceException;
import com.bluewhite.personnel.attendance.dao.AttendanceDao;
import com.bluewhite.personnel.attendance.entity.Attendance;
import com.bluewhite.system.user.entity.User;
import com.bluewhite.system.user.service.UserService;
import com.jacob.com.Variant;
@Component
public class SensorEvents {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AttendanceDao dao;
	
	public static SensorEvents sensorEvents;

	@PostConstruct
	public void init() {
		sensorEvents = this;
		sensorEvents.userService = this.userService; //步骤2 初使化时将已静态化的testService实例化
		sensorEvents.dao = this.dao; //步骤2 初使化时将已静态化的testService实例化
	}
	
	
	public void OnConnected(Variant[] arge){
		System.out.println("当成功连接机器时触发该事件，无返回值====");
	}

	public void OnDisConnected(Variant[] arge){
		System.out.println("当断开机器时触发该事件，无返回值====");
	}

	public void OnAlarm(Variant[] arge){
		System.out.println("当机器报警时触发该事件===="+arge);
	}

	public void OnAttTransactionEx(Variant[] arge){
		Attendance attendance = new Attendance(); 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//验证时间
		String time = arge[4]+"-"+arge[5]+"-"+arge[6]+"-"+arge[7]+":"+arge[8]+":"+arge[9]+" "+arge[10];
		User user = userService.findByNumber(arge[0].getStringRef());
		attendance.setUserId(user.getId());
		attendance.setNumber(String.valueOf(arge[0]));
		try {
			attendance.setTime(sdf.parse(time));
		} catch (ParseException e) {
			throw new ServiceException("时间转换异常");
		}
		//考勤状态
		attendance.setInOutMode(Integer.parseInt(String.valueOf(arge[2])));
		//验证方式
		attendance.setVerifyMode(Integer.parseInt(String.valueOf(arge[3])));
		dao.save(attendance);
		System.out.println("当验证通过时触发该事件====签到成功");
	}

	public void OnEnrollFingerEx(Variant[] arge){
		System.out.println("登记指纹时触发该事件===="+arge.clone());
	}

	public void OnFinger(Variant[] arge){
		System.out.println("当机器上指纹头上检测到有指纹时触发该消息，无返回值");
	}

	public void OnFingerFeature(Variant[] arge){
		System.out.println("登记用户指纹时，当有指纹按下时触发该消息===="+arge);
	}

	public void OnHIDNum(Variant[] arge){
		System.out.println("当刷卡时触发该消息===="+arge);
	}

	public void OnNewUser(Variant[] arge){
		System.out.println("当成功登记新用户时触发该消息===="+arge);
	}

	public void OnVerify(Variant[] arge){
		System.out.println("当用户验证时触发该消息===="+arge);
	}

	public void OnWriteCard(Variant[] arge){
		System.out.println("当机器进行写卡操作时触发该事件===="+arge);
	}

	public void OnEmptyCard(Variant[] arge){
		System.out.println("当清空 MIFARE 卡操作时触发该事件===="+arge);
	}

	public void OnEMData(Variant[] arge){
		System.out.println("当机器向 SDK 发送未知事件时，触发该事件===="+arge);
	}



}
