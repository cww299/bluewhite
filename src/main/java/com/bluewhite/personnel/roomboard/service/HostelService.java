package com.bluewhite.personnel.roomboard.service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.personnel.roomboard.entity.Hostel;
import com.bluewhite.system.user.entity.User;

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
	
	/**
	 * 修改宿舍人员信息
	 * @param onlineOrder
	 */
	public void updateUser(User user);
}
