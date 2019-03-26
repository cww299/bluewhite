package com.bluewhite.personnel.attendance.service;

import java.util.List;
import java.util.Map;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.personnel.attendance.entity.AttendanceTime;

public interface AttendanceTimeService extends BaseCRUDService<AttendanceTime,Long>{
	
	
	/**
	 * 检测初始化考勤必要条件
	 */
	public String checkAttendanceTime(AttendanceTime attendanceTime) ;
	
	/**
	 * 按日期初始化考勤详细
	 * @param attendance
	 * @return
	 */
	public List<AttendanceTime> findAttendanceTime(AttendanceTime attendanceTime);
	
	/**
	 * 按日期将出勤中需要核算的请假事项计算到考勤详细中
	 */
	public List<AttendanceTime> attendanceTimeByApplication(AttendanceTime attendanceTime);
	
	/**
	 * 按条件查看考勤工作时长的汇总
	 * @param attendance
	 * @return
	 */
	public List<Map<String, Object>> findAttendanceTimeCollect(AttendanceTime attendanceTime);
	
	/**
	 * 手动修改考勤详细数据
	 * @param attendanceTime
	 */
	public AttendanceTime updateAttendanceTime(AttendanceTime attendanceTime);
	
	/**
	 * 按条件查看考级统计后的记录
	 * @param attendanceTime
	 * @return
	 */
	public List<AttendanceTime> findAttendanceTimePage(AttendanceTime attendanceTime);

}
