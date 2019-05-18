package com.bluewhite.personnel.attendance.service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.personnel.attendance.entity.Live;

public interface LiveService  extends BaseCRUDService<Live,Long>{
	
	/**
	 * 按条件查看宿舍记录
	 * @param attendanceTime
	 * @return
	 */
	public PageResult<Live> findPage(Live live, PageParameter page);
	/**
	 * 新增记录
	 * @param onlineOrder
	 */
	public Live addLive(Live live);
	
}
