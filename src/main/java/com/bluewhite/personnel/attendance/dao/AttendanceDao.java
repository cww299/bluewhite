package com.bluewhite.personnel.attendance.dao;

import java.util.Date;
import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.personnel.attendance.entity.Attendance;

public interface AttendanceDao extends BaseRepository<Attendance, Long>{
	
	/**
	 * 按签到时间区间和员工id查询
	 * @param userId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List<Attendance> findByUserIdAndTimeBetween(Long userId,Date beginDate, Date endDate);
	
	/**
	 * 按签到时间区间和员工id查询
	 * @param userId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List<Attendance> findByTimeBetween(Date beginDate, Date endDate);
	
	/**
	 * 按签到时间和员工id查询
	 * @param userId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List<Attendance> findByUserIdAndTime(Long userId,Date time);
	
	/**
	 * 按补签id查询
	 * @param userId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List<Attendance> findByApplicationLeaveId(Long id);
	
	/**
	 *  按签到时间和员工ids查询
	 * @param userLong
	 * @param time
	 * @return
	 */
	public List<Attendance> findByUserIdInAndTimeBetween(List<Long> userLong, Date beginDate, Date endDate);

}
