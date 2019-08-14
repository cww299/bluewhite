package com.bluewhite.personnel.attendance.dao;

import java.util.Date;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.personnel.attendance.entity.AttendanceTime;

public interface AttendanceTimeDao  extends BaseRepository<AttendanceTime, Long>{
	
	/**
	 * 根据时间和员工ID查询考勤详细
	 * @param userId
	 * @param time
	 * @return
	 */
	AttendanceTime findByUserIdAndTime(Long userId, Date time);


}
