package com.bluewhite.common.utils.zkemUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.DispatchEvents;
import com.jacob.com.STA;
import com.jacob.com.Variant;

public class ZkemSDKRealTime {

	public ActiveXComponent initSTA(String address) {
		// 静态加载zkemkeeper.dll, zkemkeeper.ZKEM.1为注册表中的ProgID数值
		// 构建ActiveX组件实例
		ActiveXComponent zkem = new ActiveXComponent("zkemkeeper.ZKEM.1");
		ComThread.InitSTA();
		return zkem;
	}

	/**
	 * 连接到考勤机
	 * 
	 * @param address
	 * @param port
	 * @return
	 * @throws Exception
	 */
	public boolean connect(String address, ActiveXComponent zkem) {
		// 连接考勤机，返回是否连接成功，成功返回true，失败返回false。
		boolean result = false;
		try {
			result = zkem.invoke("Connect_NET", address, 4370).getBoolean();
			if (result) {
				System.out.println(address + ":连接成功");
			}
		} catch (Exception e) {
			System.out.println(address + ":连接失败");
		}
		return result;
	}

	/**
	 * 启动事件监听
	 */
	public void regEvent(ActiveXComponent zkem) {
		System.out.println("考勤机实时事件启动");
		DispatchEvents de = new DispatchEvents(zkem.getObject(), new SensorEvents(zkem));
		Dispatch.call(zkem, "RegEvent", new Variant(1l), new Variant(65535l));
		new STA().doMessagePump();
	}

	// 释放占用的内存空间但是这样调用会出现一个严重的问题，当访问量过大时，初始化的内存太多而不能及时释放，这样就会导致内存溢出，这个应用程序就会崩溃，最好还得重新启动服务，重新发布项目。
	// 长连接原本就是在Windows平台上运行的，但是经过一些技术加工之后，在Java中也能够调用，此问题就出现了。解决的方法还是有的，Net开发webService调用，然后生成Java客户端代码，
	// 再用java调用，这样问题就解决了，而且效率也很好，使用方便。
	public void release() {
		ComThread.Release();
	}

	/**
	 * 根据考勤号码获取用户信息
	 *
	 * @param number
	 *            考勤号码
	 * @return
	 */
	public static List<Map<String, Object>> getUserInfoByNumber(String number, ActiveXComponent zkem) {
		Variant v0 = new Variant(1);
		Variant sdwEnrollNumber = new Variant(number, true);
		Variant sName = new Variant("", true);
		Variant sPassword = new Variant("", true);
		Variant iPrivilege = new Variant(0, true);
		Variant bEnabled = new Variant(false, true);
		boolean result = zkem.invoke("SSR_GetUserInfo", v0, sdwEnrollNumber, sName, sPassword, iPrivilege, bEnabled)
				.getBoolean();
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		if (result) {
			// 由于名字后面会产生乱码，所以这里采用了截取字符串的办法把后面的乱码去掉了，以后有待考察更好的办法。
			// 只支持2位、3位、4位长度的中文名字。
			String name = sName.getStringRef();
			int index = name.indexOf("\0");
			String newStr = "";
			if (index > -1) {
				name = name.substring(0, index);
			}
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("number", number);
			m.put("name", name.trim());
			m.put("privilege", iPrivilege.getIntRef());
			m.put("enabled", bEnabled.getBooleanRef());
			resultList.add(m);
		}
		return resultList;
	}
	
	/**
	 * 获取机器IP号
	 * @param machineNumber 机器号
	 * @return IP地址
	 */
	public static String GetDeviceIP(int machineNumber,ActiveXComponent zkem){
		Variant ipAddr=new Variant("",true);
		boolean status= zkem.invoke("GetDeviceIP",new Variant(machineNumber),ipAddr).getBoolean();
		if(!status){
			return null;
		}
		return ipAddr.getStringRef();
	}

	/**
	 * 读取实时事件到pc缓冲区
	 * 
	 * @param machineNumber
	 * @return
	 */
	public boolean ReadRTLog(int machineNumber, ActiveXComponent zkem) {
		boolean result = zkem.invoke("ReadRTLog", new Variant(machineNumber)).getBoolean();
		return result;
	}

}
