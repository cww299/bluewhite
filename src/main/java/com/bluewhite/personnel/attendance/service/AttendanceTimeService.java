package com.bluewhite.personnel.attendance.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.personnel.attendance.entity.AttendanceTime;

public interface AttendanceTimeService extends BaseCRUDService<AttendanceTime,Long>{
	
	
	/**
	 * 检测初始化考勤必要条件
	 */
	public void checkAttendanceTime(AttendanceTime attendanceTime) ;
	
	/**
	 * 按日期初始化考勤详细
	 * @param attendance
	 * @return
	 */
	public List<AttendanceTime> findAttendanceTime(AttendanceTime attendanceTime) throws ParseException;
	
	/**
	 * 按日期将出勤中需要核算的请假事项计算到考勤详细中
	 * @throws ParseException 
	 */
	public List<AttendanceTime> attendanceTimeByApplication(List<AttendanceTime> attendanceTimeList) throws ParseException;
	
	/**
	 * 加入申请事项考勤工作时长的汇总
	 * @param attendance
	 * @return
	 */
	public List<Map<String, Object>> findAttendanceTimeCollectAdd(AttendanceTime attendanceTime) throws ParseException;
	
	/**
	 * 默认下考勤工作时长的汇总
	 * @param attendance
	 * @return
	 */
	public List<Map<String, Object>> findAttendanceTimeCollect(AttendanceTime attendanceTime) throws ParseException;
	
	/**
	 * 删除考勤工作汇总
	 * @param attendance
	 * @return
	 */
	public boolean deleteAttendanceTimeCollect(AttendanceTime attendanceTime);
	
	/**
	 * 查找申请事项考勤工作
	 * @param attendance
	 * @return
	 */
	public List<Map<String, Object>> findAttendanceTimeCollectList(AttendanceTime attendanceTime) throws ParseException;
	
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

	/**
	 * 同步更新考勤汇总
	 * @param attendanceTime
	 */
	public List<Map<String, Object>> syncAttendanceTimeCollect(AttendanceTime attendanceTime)  throws ParseException;
	
	/**
	 * 车间人员根据填写考勤情况和打卡考勤汇总进行对比
	 */
	public List<Map<String, Object>> workshopAttendanceContrast(AttendanceTime attendanceTime);

}
