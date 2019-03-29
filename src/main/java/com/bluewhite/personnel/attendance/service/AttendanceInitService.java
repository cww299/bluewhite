package com.bluewhite.personnel.attendance.service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.personnel.attendance.entity.ApplicationLeave;
import com.bluewhite.personnel.attendance.entity.AttendanceInit;

public interface AttendanceInitService  extends BaseCRUDService<AttendanceInit,Long>{
	
	/**
	 * 分页查看
	 * @param attendanceInit
	 * @param page
	 * @return
	 */
	PageResult<AttendanceInit> findAttendanceInitPage(AttendanceInit attendanceInit, PageParameter page);
	

}
