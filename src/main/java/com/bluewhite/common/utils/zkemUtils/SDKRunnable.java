package com.bluewhite.common.utils.zkemUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.bluewhite.common.Log;
import com.bluewhite.common.MyExceptionHandlerExceptionResolver;
import com.jacob.activeX.ActiveXComponent;

public class SDKRunnable implements Runnable {

	private static final Log log = Log.getLog(SDKRunnable.class);
	
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
			System.out.println("Thread开始====3秒等待设备实时事件");
			Thread.sleep(3000);
			regEvent();
		} catch (Exception e) {
			log.error("线程异常，结束");
		}
	}

	/**
	 * 线程任务
	 */
	private void regEvent() {
		ZkemSDKRealTime sdk = new ZkemSDKRealTime();
		ActiveXComponent zkem = sdk.initSTA(address);
		sdk.connect(address, zkem);
		timer(sdk, zkem);
		sdk.regEvent(zkem);
	}

	/**
	 * 定时任务
	 * 
	 * @param sdk
	 * @param zkem
	 */
	private void timer(ZkemSDKRealTime sdk, ActiveXComponent zkem) {
		Calendar c = Calendar.getInstance();
		Date time = c.getTime();
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				// 使用获取机器ip方式，判断是否保持设备连接在线状态
				String ip = sdk.GetDeviceIP(1, zkem);
				if (ip == null) {
					System.out.println(address + "考勤机设备异常，重连中-------");
					regEvent();
					timer.cancel();
				}
			}
		}, time, 60000);// 这里设定将延时每隔一分钟执行一次
	}

}
