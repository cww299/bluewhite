package com.bluewhite.personnel.roomboard.service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.personnel.roomboard.entity.Total;

public interface TotalService  extends BaseCRUDService<Total,Long>{
	
	/**
	 * 按条件查看
	 * @param attendanceTime
	 * @return
	 */
	public PageResult<Total> findPage(Total total, PageParameter page);
	/**
	 * 新增记录
	 * @param onlineOrder
	 */
	public Total addTotal(Total total);
	
	
	
	
}
