package com.bluewhite.personnel.attendance.service;

import java.util.List;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.personnel.attendance.entity.ApplicationLeave;
import com.bluewhite.personnel.attendance.entity.AttendanceCollect;

public interface AttendanceCollectService  extends BaseCRUDService<AttendanceCollect,Long>{
	
	/**
	 * 按条件查看的考勤汇总
	 * @param attendanceTime
	 * @return
	 */
	public List<AttendanceCollect> findAttendanceCollect(AttendanceCollect attendanceCollect);

}
