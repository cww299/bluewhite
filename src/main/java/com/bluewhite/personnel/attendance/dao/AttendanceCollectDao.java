package com.bluewhite.personnel.attendance.dao;

import java.util.Date;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.personnel.attendance.entity.AttendanceCollect;
import com.bluewhite.personnel.attendance.entity.AttendanceTime;

public interface AttendanceCollectDao extends BaseRepository<AttendanceCollect, Long>{
	
	/**
	 * 根据日期和员工id查询汇总（有且仅有一条）
	 * @return
	 */
	public AttendanceCollect findByUserIdAndTime(Long userId , Date time);

}
