package com.bluewhite.common.utils.ZkemUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.bluewhite.common.Constants;
import com.bluewhite.common.ServiceException;
import com.bluewhite.personnel.attendance.dao.AttendanceDao;
import com.bluewhite.personnel.attendance.entity.Attendance;
import com.bluewhite.system.user.entity.User;
import com.bluewhite.system.user.service.UserService;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Variant;

@Component
public class SensorEvents {
	
	
	private ActiveXComponent zkem;

	public  SensorEvents(){};
	
	public SensorEvents(ActiveXComponent zkem) {
		this.zkem = zkem;
	}

	@Autowired
	private UserService userService;

	@Autowired
	private AttendanceDao dao;

	public static SensorEvents sensorEvents;

	@PostConstruct
	public void init() {
		sensorEvents = this;
	}

	public void OnConnected(Variant[] arge) {
		System.out.println("当成功连接机器时触发该事件，无返回值====");
	}

	public void OnDisConnected(Variant[] arge) {
		System.out.println("当断开机器时触发该事件，无返回值====");
	}

	public void OnAlarm(Variant[] arge) {
		System.out.println("当机器报警时触发该事件====" + arge);
	}

	/**
	 * 当验证通过时触发该事件 以下参数全为返回值 函数原型:OnAttTransactionEx(BSTR ErollNumber,LONG
	 * IsInValid,LONG AttState,LONG VerifyMethod, LONG Year,LONG Month,LONG
	 * Day,LONG Hour,LONG Minute,LONG Second,LONG WorkCode)
	 *
	 * EnrollNumber:该用户的UserID IsInValid:该记录是否为有效记录，1为无效记录，0为有效记录 AttSate:考勤状态
	 * 默认0 Check-In, 1 Check-Out, 2 Break-Out, 3 Break-In, 4 OT-In, 5 OT-Out
	 * VerifyMethod:验证方式 0为密码验证，1为指纹验证，2为卡验证
	 * Year/Month/Day/Hour/Minute/Second:为验证通过的时间
	 * WorkCode:返回验证时WorkCode值，当机器无Workcode功能时，该值返回0
	 *
	 */
	public void OnAttTransactionEx(Variant[] arge) {
		Attendance attendance = new Attendance();
		System.out.println(arge[0]);
		attendance.setNumber(String.valueOf(arge[0]));
		String year = arge[4].toString();
		String month = arge[5].toString();
		String day = arge[6].toString();
		String hour = arge[7].toString();
		String minute = arge[8].toString();
		String second = arge[9].toString();
		String time = String.format("%s-%s-%s %s:%s:%s", year, month, day, hour, minute, second);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date date = sdf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		User user = SensorEvents.sensorEvents.userService.findByNumber(attendance.getNumber());
		if (user != null) {
			attendance.setUserId(user.getId());
		}
		attendance.setTime(new Date());
		// 考勤状态
		attendance.setInOutMode(Integer.valueOf(String.valueOf(arge[2])));
		// 验证方式
		attendance.setVerifyMode(Integer.valueOf(String.valueOf(arge[3])));
		System.out.println(Thread.currentThread().getName());
		String address = ZkemSDKUtils.GetDeviceIP(0,zkem);
		String sourceMachine = null;
		if (Constants.THREE_FLOOR.equals(address)) {
			sourceMachine = "THREE_FLOOR";
		}
		if (Constants.TWO_FLOOR.equals(address)) {
			sourceMachine = "TWO_FLOOR";
		}
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
		attendance.setSourceMachine(sourceMachine);
		SensorEvents.sensorEvents.dao.save(attendance);
		System.out.println("当验证通过时触发该事件====签到成功");
	}

	public void OnEnrollFingerEx(Variant[] arge) {
		System.out.println("登记指纹时触发该事件====" + arge);
	}

	public void OnFinger(Variant[] arge) {
		System.out.println("当机器上指纹头上检测到有指纹时触发该消息，无返回值");
	}

	public void OnFingerFeature(Variant[] arge) {
		System.out.println("登记用户指纹时，当有指纹按下时触发该消息====" + arge);
	}

	public void OnHIDNum(Variant[] arge) {
		System.out.println("当刷卡时触发该消息====" + arge);
	}

	public void OnNewUser(Variant[] arge) {
		System.out.println("当成功登记新用户时触发该消息====" + arge);
	}

	public void OnVerify(Variant[] arge) {
		System.out.println("当用户验证时触发该消息====" + arge);
	}

	public void OnWriteCard(Variant[] arge) {
		System.out.println("当机器进行写卡操作时触发该事件====" + arge);
	}

	public void OnEmptyCard(Variant[] arge) {
		System.out.println("当清空 MIFARE 卡操作时触发该事件====" + arge);
	}

	public void OnEMData(Variant[] arge) {
		System.out.println("当机器向 SDK 发送未知事件时，触发该事件====" + arge);
	}

}
