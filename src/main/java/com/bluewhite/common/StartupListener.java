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
		if (event.getApplicationContext().getParent() == null) {
			// root application context 没有parent，他就是老大.
			// 需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。
			System.out.println("执行我最后");
//			regEvent();
		} else {
			System.out.println("执行我最后2");
			regEvent();

		}
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
