package com.bluewhite.common;

import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.bluewhite.personnel.attendance.service.AttendanceServiceImpl;

@Service
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {
	@Autowired
	private AttendanceServiceImpl attendanceServiceImpl;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
//		if (event.getApplicationContext().getParent() == null) {
//		} else {
//			System.out.println("执行我最后2");
//			regEvent();
//		}
	}

	private void regEvent() {
		Timer timer = new Timer("regEvent", true);
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				attendanceServiceImpl.regEvent();
			}
		}, 30);
	}

}
