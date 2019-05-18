package com.bluewhite.personnel.attendance.service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.personnel.attendance.entity.Hostel;

public interface HostelService  extends BaseCRUDService<Hostel,Long>{
	
	/**
	 * 按条件查看宿舍记录
	 * @param attendanceTime
	 * @return
	 */
	public PageResult<Hostel> findPage(Hostel hostel, PageParameter page);
	/**
	 * 新增宿舍
	 * @param onlineOrder
	 */
	public Hostel addHostel(Hostel hostel);
	/**
	 * 宿舍分配
	 * @param onlineOrder
	 */
	public void updateUserHostelId(Hostel hostel);
}
