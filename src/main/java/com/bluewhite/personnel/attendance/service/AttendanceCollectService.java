package com.bluewhite.personnel.attendance.service;

import java.util.List;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.personnel.attendance.entity.AttendanceCollect;

public interface AttendanceCollectService  extends BaseCRUDService<AttendanceCollect,Long>{
	
	/**
	 * 按条件查看的考勤汇总
	 * @param attendanceTime
	 * @return
	 */
	public List<AttendanceCollect> findAttendanceCollect(AttendanceCollect attendanceCollect);
	
	/**
	 * 存档考勤数据
	 * @param attendanceCollect
	 */
	public void sealAttendanceCollect(AttendanceCollect attendanceCollect);

}
