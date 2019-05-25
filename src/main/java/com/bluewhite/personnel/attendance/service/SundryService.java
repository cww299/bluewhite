package com.bluewhite.personnel.attendance.service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.personnel.attendance.entity.Sundry;

public interface SundryService  extends BaseCRUDService<Sundry,Long>{
	
	/**
	 * 按条件查看
	 * @param attendanceTime
	 * @return
	 */
	public PageResult<Sundry> findPage(Sundry sundry, PageParameter page);
	/**
	 * 新增记录
	 * @param onlineOrder
	 */
	public Sundry addSundry(Sundry sundry);
	
	
	
	
}
