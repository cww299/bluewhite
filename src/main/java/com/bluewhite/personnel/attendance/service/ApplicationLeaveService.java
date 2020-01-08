package com.bluewhite.personnel.attendance.service;

import java.text.ParseException;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.personnel.attendance.entity.ApplicationLeave;

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
	ApplicationLeave saveApplicationLeave(ApplicationLeave applicationLeave) ;
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	public int deleteApplicationLeave(String ids) ;
	
	/**
	 * 默认补签
	 * @param applicationLeave
	 * @throws ParseException
	 */
	public void defaultRetroactive(ApplicationLeave applicationLeave) ;
;

}
