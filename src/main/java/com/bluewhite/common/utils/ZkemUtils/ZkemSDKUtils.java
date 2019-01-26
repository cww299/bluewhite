package com.bluewhite.common.utils.ZkemUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.bluewhite.common.ServiceException;
import com.bluewhite.personnel.attendance.entity.Attendance;
import com.bluewhite.system.user.entity.User;
import com.bluewhite.system.user.service.UserService;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.DispatchEvents;
import com.jacob.com.STA;
import com.jacob.com.Variant;

@Component
public class ZkemSDKUtils {
	
	@Autowired
	private UserService userService;
	
	private static ZkemSDKUtils zkemSDKUtils;
	
	@PostConstruct
	public void init() {
		zkemSDKUtils = this;
		zkemSDKUtils.userService = this.userService; // 初使化时将已静态化的testService实例化
	}

	private static ActiveXComponent zkem;

	public void initSTA() {
		// 静态加载zkemkeeper.dll, zkemkeeper.ZKEM.1为注册表中的ProgID数值
		// 构建ActiveX组件实例
		zkem = new ActiveXComponent("zkemkeeper.ZKEM.1");
		ComThread.InitSTA();// 调用初始化并放入内存中等待调用new
							// ActiveXComponent("zkemkeeper.ZKEM.1")
	}

	public void release() {
		// 释放占用的内存空间但是这样调用会出现一个严重的问题，当访问量过大时，初始化的内存太多而不能及时释放，这样就会导致内存溢出，这个应用程序就会崩溃，最好还得重新启动服务，重新发布项目。
		// 长连接本来原本就是在Windows平台上运行的，但是经过一些技术加工之后，在Java中也能够调用，此问题就出现了。解决的方法还是有的，Net开发webService调用，然后生成Java客户端代码，
		// 再用java调用，这样问题就解决了，而且效率也很好，使用方便。
		ComThread.Release();
	}

	/**
	 * 连接到考勤机
	 * 
	 * @param address
	 * @param port
	 * @return
	 * @throws Exception
	 */
	public boolean connect(String address, int port) throws Exception {
		// 连接考勤机，返回是否连接成功，成功返回true，失败返回false。
		// 1、Connect_NET：zkem中方法，通过网络连接中控考勤机。
		// 2、address：中控考勤机IP地址。
		// 3、port：端口号
		boolean result = zkem.invoke("Connect_NET", address, port).getBoolean();
		return result;
	}

	/**
	 * 断开考勤机连接
	 */
	public void disConnect() {
		zkem.invoke("Disconnect");
	}

	/**
	 * 启动事件监听
	 */
	public static void regEvent() {
		System.out.println("启动----------");
		zkem.invoke("RegEvent", new Variant(1), new Variant(1));
		zkem.invoke("ReadRTLog", new Variant(1));
		zkem.invoke("GetRTLog", new Variant(1));
		new DispatchEvents(zkem.getObject(), new SensorEvents());
		new STA().doMessagePump();
	}

	/**
	 * 读取考勤所有数据到缓存中。配合getGeneralLogData使用。
	 * 
	 * @return
	 */
	public boolean readGeneralLogData(int machineNum) {
		// 调用zkem中的ReadGeneralLogData方法，传入参数，机器号
		boolean result = zkem.invoke("ReadGeneralLogData", new Variant[] { new Variant(machineNum) }).getBoolean();
		return result;
	}

	/**
	 * 读取该时间之后的最新考勤数据。 配合getGeneralLogData使用。
	 * 
	 * @param lastest
	 * @return
	 */
	public boolean readLastestLogData(int machineNum, Date lastest) {
		Calendar c = Calendar.getInstance();
		c.setTime(lastest);

		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);
		int hours = c.get(Calendar.HOUR_OF_DAY);
		int minutes = c.get(Calendar.MINUTE);
		int seconds = c.get(Calendar.SECOND);

		Variant v0 = new Variant(machineNum);
		Variant vLog = new Variant(1);
		Variant dwYear = new Variant(year, true);
		Variant dwMonth = new Variant(month, true);
		Variant dwDay = new Variant(day, true);
		Variant dwHour = new Variant(hours, true);
		Variant dwMinute = new Variant(minutes, true);
		Variant dwSecond = new Variant(seconds, true);

		boolean result = zkem.invoke("ReadLastestLogData", v0, vLog, dwYear, dwMonth, dwDay, dwHour, dwMinute, dwSecond)
				.getBoolean();
		return result;
	}

	/**
	 * 获取缓存中的考勤数据。配合readGeneralLogData / readLastestLogData使用。
	 * 
	 * @return 返回的map中，包含以下键值： "EnrollNumber" 人员编号 "Time" 考勤时间串，格式: yyyy-MM-dd
	 *         HH:mm:ss "VerifyMode" 验证方式：0 为密码验证，1 为指纹验证，2 为卡验证 ,15为 面部验证
	 *         "InOutMode" 默认
	 *         0—Check-In 1—Check-Out 2—Break-O 3—Break-In 4—OT-In 5—OT-Out
	 */
	public List<Attendance>  getGeneralLogData(int machineNum) {
		Variant v0 = new Variant(machineNum);
		Variant dwEnrollNumber = new Variant("", true);
		Variant dwVerifyMode = new Variant(0, true);
		Variant dwInOutMode = new Variant(0, true);
		Variant dwYear = new Variant(0, true);
		Variant dwMonth = new Variant(0, true);
		Variant dwDay = new Variant(0, true);
		Variant dwHour = new Variant(0, true);
		Variant dwMinute = new Variant(0, true);
		Variant dwSecond = new Variant(0, true);
		Variant dwWorkCode = new Variant(0, true);
		List<Attendance> strList = new ArrayList<>();
		boolean newresult = false;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		do {
			Variant vResult = Dispatch.call(zkem, "SSR_GetGeneralLogData", v0, dwEnrollNumber, dwVerifyMode,
					dwInOutMode, dwYear, dwMonth, dwDay, dwHour, dwMinute, dwSecond, dwWorkCode);
			newresult = vResult.getBoolean();
			if (newresult) {
				String enrollNumber = dwEnrollNumber.getStringRef();
				// 如果没有编号，则跳过。
				if (enrollNumber == null || enrollNumber.trim().length() == 0)
					continue;
				Attendance attendance = new Attendance();
				attendance.setNumber(enrollNumber);
				try {
					attendance.setTime(sdf.parse(dwYear.getIntRef() + "-" + dwMonth.getIntRef() + "-" + dwDay.getIntRef() + " "
							+ dwHour.getIntRef() + ":" + dwMinute.getIntRef() + ":" + dwSecond.getIntRef()));
				} catch (ParseException e) {
					throw new ServiceException("时间转换异常");
				}
				attendance.setVerifyMode(dwVerifyMode.getIntRef());
				User user = null;
				try {
//					user = userService.findByNumber(enrollNumber.trim());
				} catch (Exception e) {
					throw new ServiceException("重复数据"+enrollNumber);
				}
				if (user != null) {
					attendance.setUserId(user.getId());
				}
				strList.add(attendance);
			}
		} 
		while (newresult == true);
		return strList;
	}

	/**
	 * 获取用户信息
	 *
	 * @return 返回的Map中，包含以下键值: "EnrollNumber" 人员编号 "Name" 人员姓名 "Password" 人员密码
	 *         "Privilege" 特权 0位普通 3特权 "Enabled" 是否启用
	 */
	public static List<Map<String, Object>> getUserInfo() {

		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		// 将用户数据读入缓存中。
		boolean result = zkem.invoke("ReadAllUserID", 1).getBoolean();

		Variant v0 = new Variant(1);
		Variant sdwEnrollNumber = new Variant("", true);
		Variant sName = new Variant("", true);
		Variant sPassword = new Variant("", true);
		Variant iPrivilege = new Variant(0, true);
		Variant bEnabled = new Variant(false, true);

		while (result) {
			// 从缓存中读取一条条的用户数据
			result = zkem.invoke("SSR_GetAllUserInfo", v0, sdwEnrollNumber, sName, sPassword, iPrivilege, bEnabled)
					.getBoolean();

			// 如果没有编号，跳过。
			String enrollNumber = sdwEnrollNumber.getStringRef();
			if (enrollNumber == null || enrollNumber.trim().length() == 0)
				continue;

			// 由于名字后面会产生乱码，所以这里采用了截取字符串的办法把后面的乱码去掉了，以后有待考察更好的办法。
			// 只支持2位、3位、4位长度的中文名字。
			String name = sName.getStringRef();
			int index = name.indexOf("\0");
			String newStr = "";
			if (index > -1) {
				name = name.substring(0, index);
			}
			if (sName.getStringRef().length() > 4) {
				name = sName.getStringRef().substring(0, 4);
			}
			// 如果没有名字，跳过。
			if (name.trim().length() == 0)
				continue;
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("number", enrollNumber);
			m.put("name", name.trim());
			m.put("privilege", iPrivilege.getIntRef());
			m.put("enabled", bEnabled.getBooleanRef());
			resultList.add(m);
		}
		return resultList;
	}

	/**
	 * 设置用户信息
	 *
	 * @param number
	 * @param name
	 * @param password
	 * @param isPrivilege
	 *            0為普通用戶,3為管理員;
	 * @param enabled
	 *            是否啟用
	 * @return
	 */
	public static boolean setUserInfo(String number, String name, String password, int isPrivilege, boolean enabled) {
		Variant v0 = new Variant(1);
		Variant sdwEnrollNumber = new Variant(number, true);
		Variant sName = new Variant(name, true);
		Variant sPassword = new Variant(password, true);
		Variant iPrivilege = new Variant(isPrivilege, true);
		Variant bEnabled = new Variant(enabled, true);

		boolean result = zkem.invoke("SSR_SetUserInfo", v0, sdwEnrollNumber, sName, sPassword, iPrivilege, bEnabled)
				.getBoolean();
		return result;
	}

	/**
	 * 根据考勤号码获取用户信息
	 *
	 * @param number
	 *            考勤号码
	 * @return
	 */
	public static Map<String, Object> getUserInfoByNumber(String number) {
		Variant v0 = new Variant(1);
		Variant sdwEnrollNumber = new Variant(number, true);
		Variant sName = new Variant("", true);
		Variant sPassword = new Variant("", true);
		Variant iPrivilege = new Variant(0, true);
		Variant bEnabled = new Variant(false, true);
		boolean result = zkem.invoke("SSR_GetUserInfo", v0, sdwEnrollNumber, sName, sPassword, iPrivilege, bEnabled)
				.getBoolean();
		if (result) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("EnrollNumber", number);
			m.put("Name", sName.getStringRef());
			m.put("Password", sPassword.getStringRef());
			m.put("Privilege", iPrivilege.getIntRef());
			m.put("Enabled", bEnabled.getBooleanRef());
			return m;
		}
		return null;
	}

	/**
	 * 删除用户;
	 */
	public static Boolean delectUserById(String dwEnrollNumber) {
		Variant v0 = new Variant(1);
		Variant sdwEnrollNumber = new Variant(dwEnrollNumber, true);
		/**
		 * sdwBackupNumber： 一般范围为 0-9，同时会查询该用户是否还有其他指纹和密码，如都没有，则删除该用户 当为 10
		 * 是代表删除的是密码，同时会查询该用户是否有指纹数据，如没有，则删除该用户 11 和 13 是代表删除该用户所有指纹数据， 12
		 * 代表删除该用户（包括所有指纹和卡号、密码数据）
		 */
		Variant sdwBackupNumber = new Variant(12);
		/**
		 * 删除登记数据，和 SSR_DeleteEnrollData 不同的是删除所有指纹数据可用参数 13 实现，该函数具有更高效率
		 */
		return zkem.invoke("SSR_DeleteEnrollDataExt", v0, sdwEnrollNumber, sdwBackupNumber).getBoolean();
	}

	/**
	 * 获取考勤机序列码
	 */
	public String getSerialNumber(int machineNum) {
		// Variant：变体类型，能够在运行期间动态的改变类型。
		// 变体类型能支持所有简单的数据类型，如整型、浮点、字符串、布尔型、日期时间、货币及OLE自动化对象等，不能够表达Object
		// Pascal对象。
		Variant v0 = new Variant(machineNum);
		Variant dwSerialNumber = new Variant("", true);
		// zkem.invoke：调用Method类代表的方法，此处表明调用zkem中的GetSerialNumber方法，
		boolean result = zkem.invoke("GetSerialNumber", v0, dwSerialNumber).getBoolean();
		if (result) {
			return dwSerialNumber.getStringRef();
		}
		return null;
	}

}