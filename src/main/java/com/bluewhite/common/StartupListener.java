package com.bluewhite.common;

import java.util.Timer;
import java.util.TimerTask;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.bluewhite.common.entity.ConfigManager;
import com.bluewhite.common.utils.IpUtil;
import com.bluewhite.common.utils.zkemUtils.SDKRunnable;

@Service
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() == null) {
		} else {
			if (IpUtil.getLocalIP().equals("192.168.1.74")) {
			    regEvent();
			}
		}
	}

	private void regEvent() {
		Timer timer = new Timer("regEvent", true);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				String key = ConfigManager.getProperty("attendance.ip");
				for (String address : key.split(",")) {
					new Thread(new SDKRunnable(address), "thread:" + address).start();
				}
			}
		}, 300);
	}
	
}
