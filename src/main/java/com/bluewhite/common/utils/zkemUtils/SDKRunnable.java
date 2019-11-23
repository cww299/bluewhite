package com.bluewhite.common.utils.zkemUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.jacob.activeX.ActiveXComponent;

public class SDKRunnable implements Runnable {

	private String address;

	public SDKRunnable(String address) {
		this.address = address;
	}

	/**
	 * 线程内每隔一分钟去读取实时事件，失败则重连当前设备
	 */
	@Override
	public void run() {
		try {
			System.out.println("Thread开始====2秒等待设备实时事件");
			ZkemSDKRealTime sdk = new ZkemSDKRealTime();
			ActiveXComponent zkem = sdk.initSTA(address);
			Thread.sleep(2000);
			sdk.connect(address, zkem);
			timer(sdk,zkem);
			sdk.regEvent(zkem);
		} catch (Exception e) {
			
		}
	}
	
	/**
	 * 定时任务
	 * @param sdk
	 * @param zkem
	 */
	public void timer(ZkemSDKRealTime sdk,ActiveXComponent zkem) {
		Calendar c = Calendar.getInstance();
		Date time = c.getTime();
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				//使用获取机器ip方式，判断是否保持设备连接在线状态
				String ip = sdk.GetDeviceIP(1, zkem);
				if(ip==null){
					System.out.println(address+"考勤机设备掉线，重连中-------");
					sdk.connect(address, zkem);
				}
			}
		},time,60000);// 这里设定将延时每隔一分钟执行一次
	}

}
