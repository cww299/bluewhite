package com.bluewhite.personnel.attendance.dao;

import java.util.Date;
import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.personnel.attendance.entity.ApplicationLeave;

public interface ApplicationLeaveDao  extends BaseRepository<ApplicationLeave, Long>{
	
	/**
	 * 根据用户ID和考勤统计时间查询有没有需要核算的请假事项
	 * @param userId
	 * @param time
	 * @return
	 */
	public List<ApplicationLeave> findByUserIdAndTime(Long userId,Date time);
	
	/**
	 * 根据日期获取所有的请假事项
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public List<ApplicationLeave> findByTimeBetween(Date beginTime,Date endTime);
	
	

}
