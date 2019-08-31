package com.bluewhite.personnel.attendance.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.personnel.attendance.entity.Attendance;
import com.bluewhite.personnel.attendance.entity.AttendanceTime;


public interface AttendanceService  extends BaseCRUDService<Attendance,Long>{
	/**
	 * 查询考勤机中的人员信息
	 * @param address
	 * @return
	 */
	public List<Map<String, Object>> getAllUser(String address);
	/**
	 * 同步打卡机人员编号到系统
	 * @param address
	 * @return
	 */
	public int syncAttendanceUser(String address);
	
	/**
	 * 修改考勤机中的人员信息
	 * @param address
	 * @return
	 */
	public boolean updateUser(String address,String number,String name, int isPrivilege, boolean enabled);
	
	/**
	 * 删除考勤机中的人员信息
	 * @param address
	 * @param number
	 * @return
	 */
	public boolean deleteUser(String address, String number);
	
	/**
	 * 根据日期同步考勤机中全部考勤记录
	 * @param address
	 */
	public List<Attendance> allAttendance(String address,Date startTime , Date endTime);
	
	/**
	 * 分页查看考勤记录
	 * @param attendance
	 * @param page
	 * @return
	 */
	public PageResult<Attendance> findPageAttendance(Attendance attendance, PageParameter page);
	
	/**
	 * 查看考勤机中全部考勤记录
	 * @param address
	 * @return
	 */
	public List<Map<String, Object>> getAllAttendance(String address);
	
	/**
	 * 按编号查询人员
	 * @param address
	 * @param number
	 * @return
	 */
	public List<Map<String, Object>>  findUser(String address, String number);
	
	/**
	 *  手动修正未同步人员编号时，同步考勤记录而导致的人员姓名为null问题
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public int fixAttendance(Date startTime, Date endTime);
	
	
	/**
	 * 按日期重置考勤机中全部考勤记录
	 * @param address
	 * @param startTime
	 * @param endTime
	 */
	public int restAttendance(String address, Date startTime, Date endTime,Long userId);
	
	
	public Map<String, Object> getUser(String address ,String number);

}
