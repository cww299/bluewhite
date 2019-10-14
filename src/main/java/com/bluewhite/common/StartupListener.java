package com.bluewhite.common;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.bluewhite.common.utils.IpUtil;
import com.bluewhite.common.utils.ZkemUtils.SDKRunnable;
import com.bluewhite.personnel.attendance.service.AttendanceServiceImpl;

@Service
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {
	@Autowired
	private AttendanceServiceImpl attendanceServiceImpl;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() == null) {
		} else {
			if(IpUtil.getLocalIP().equals("192.168.1.74")){
				regEvent();
			}
		}
	}

	private void regEvent() {
		Timer timer = new Timer("regEvent", true);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				ResourceBundle resource = ResourceBundle.getBundle("resources");
				String key = resource.getString("attendance.ip");
				for (String address : key.split(",")) {
					new Thread(new SDKRunnable(address), "thread:" + address).start();
				}
			}
		}, 300);
	}

}
