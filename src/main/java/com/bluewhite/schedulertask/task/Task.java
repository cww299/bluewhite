package com.bluewhite.schedulertask.task;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bluewhite.common.annotation.SysLogAspectAnnotation;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.personnel.attendance.entity.Attendance;
import com.bluewhite.personnel.attendance.service.AttendanceService;
import com.bluewhite.production.finance.entity.CollectInformation;
import com.bluewhite.production.finance.service.CollectInformationService;
import com.bluewhite.system.sys.entity.SysLog;

@Component
public class Task {

	@Autowired
	private CollectInformationService collectInformationService;
	@Autowired
	private AttendanceService attendanceService;

	@Scheduled(cron = "0 30 23 * * ?") // 每晚23点30触发
	@SysLogAspectAnnotation(description = "定时任务数据汇总", module = "数据汇总", operateType = "更新", logType = SysLog.ADMIN_LOG_TYPE)
	public void aTask() {
		CollectInformation collectInformation = new CollectInformation();
		collectInformation.setType(1);
		collectInformationService.collectInformation(collectInformation);
		CollectInformation collectInformation1 = new CollectInformation();
		collectInformation1.setType(2);
		collectInformationService.collectInformation(collectInformation1);
		CollectInformation collectInformation2 = new CollectInformation();
		collectInformation2.setType(3);
		collectInformationService.collectInformation(collectInformation2);
		CollectInformation collectInformation4 = new CollectInformation();
		collectInformation4.setType(4);
		collectInformationService.collectInformation(collectInformation4);
		CollectInformation collectInformation5 = new CollectInformation();
		collectInformation5.setType(5);
		collectInformationService.collectInformation(collectInformation5);

	}

	@Scheduled(cron = "0 00 06 * * ?") // 清晨6点触发
	public void attendanceTask() {
		// 获取昨天的日期
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		Date time = cal.getTime();
		Date startTime = DatesUtil.getfristDayOftime(time);
		Date endTime = DatesUtil.getLastDayOftime(time);
		attendanceService.allAttendance("192.168.1.204", startTime, endTime);
		attendanceService.allAttendance("192.168.1.205", startTime, endTime);
		attendanceService.allAttendance("192.168.1.250", startTime, endTime);
		attendanceService.allAttendance("192.168.3.113", startTime, endTime);
	}



}
