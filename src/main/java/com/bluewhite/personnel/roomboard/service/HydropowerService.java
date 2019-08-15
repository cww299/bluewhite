package com.bluewhite.personnel.roomboard.service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.personnel.roomboard.entity.Hydropower;

public interface HydropowerService  extends BaseCRUDService<Hydropower,Long>{
	
	/**
	 * 按条件查看宿舍水电费记录
	 * @param attendanceTime
	 * @return
	 */
	public PageResult<Hydropower> findPage(Hydropower hydropower, PageParameter page);
	/**
	 * 新增水电费
	 * @param onlineOrder
	 */
	public Hydropower addHydropower(Hydropower hydropower);
	
}
