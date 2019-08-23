package com.bluewhite.personnel.attendance.service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.finance.attendance.entity.AttendancePay;
import com.bluewhite.personnel.attendance.entity.ApplicationLeave;
import com.bluewhite.personnel.attendance.entity.AttendanceTime;

public interface ApplicationLeaveService  extends BaseCRUDService<ApplicationLeave,Long>{
	
	/**
	 * 按条件查看的请假事项记录
	 * @param attendanceTime
	 * @return
	 */
	public PageResult<ApplicationLeave> findApplicationLeavePage(ApplicationLeave applicationLeave, PageParameter page);
	
	/**
	 * 新增修改请假事项
	 * @param applicationLeave
	 */
	ApplicationLeave saveApplicationLeave(ApplicationLeave applicationLeave)  throws ParseException;
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	public int deleteApplicationLeave(String ids) throws ParseException;

	public void defaultRetroactive(ApplicationLeave applicationLeave)  throws ParseException ;
;

}
