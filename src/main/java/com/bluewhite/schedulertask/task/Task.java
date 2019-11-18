package com.bluewhite.schedulertask.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bluewhite.personnel.attendance.service.AttendanceService;
import com.bluewhite.production.finance.service.CollectInformationService;

@Component
public class Task {

	@Autowired
	private CollectInformationService collectInformationService;
	@Autowired
	private AttendanceService attendanceService;


//	@Scheduled(cron = "0 00 06 * * ?") // 清晨6点触发
//	public void attendanceTask() {
//		// 获取昨天的日期
//		Calendar cal = Calendar.getInstance();
//		cal.add(Calendar.DATE, -1);
//		Date time = cal.getTime();
//		Date startTime = DatesUtil.getfristDayOftime(time);
//		Date endTime = DatesUtil.getLastDayOftime(time);
//		attendanceService.allAttendance(Constants.THREE_FLOOR, startTime, endTime,null);
//		attendanceService.allAttendance(Constants.TWO_FLOOR, startTime, endTime,null);
//		attendanceService.allAttendance(Constants.ONE_FLOOR, startTime, endTime,null);
//		attendanceService.allAttendance(Constants.EIGHT_WAREHOUSE, startTime, endTime,null);
//		attendanceService.allAttendance(Constants.NEW_IGHT_WAREHOUSE, startTime, endTime,null);
//		attendanceService.allAttendance(Constants.ELEVEN_WAREHOUSE, startTime, endTime,null);
//	}

//	@Scheduled(cron = "0 0/3 * * * ?") // 3分钟触发一次
//	public void attendanceTask() {
//		
//	}

}
