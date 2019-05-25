package com.bluewhite.personnel.attendance.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
	
	/**
	 * 按条件查看宿舍分摊记录
	 * @param 
	 * @return
	 */
	public List<Live> findLivePage(Live live);
	
	/**
	 * 人员分摊
	 * @param userId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List<Map<String, Object>> findShareSummary(Date monthDate, Long hostelId);
}
