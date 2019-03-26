package com.bluewhite.personnel.attendance.service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.personnel.attendance.entity.ApplicationLeave;

public interface ApplicationLeaveService  extends BaseCRUDService<ApplicationLeave,Long>{
	/**
	 * 修改请假事项
	 * @param applicationLeave
	 */
	ApplicationLeave updateApplicationLeave(ApplicationLeave applicationLeave);
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	int deleteApplicationLeave(String ids);

}
