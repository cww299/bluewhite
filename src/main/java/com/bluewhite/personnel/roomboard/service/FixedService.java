package com.bluewhite.personnel.roomboard.service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.personnel.roomboard.entity.Fixed;

public interface FixedService  extends BaseCRUDService<Fixed,Long>{
	
	/**
	 * 按条件查看宿舍固定资产
	 * @param attendanceTime
	 * @return
	 */
	public PageResult<Fixed> findPage(Fixed fixed, PageParameter page);
	/**
	 * 新增记录
	 * @param onlineOrder
	 */
	public Fixed addFixed(Fixed fixed);
	
	
}
