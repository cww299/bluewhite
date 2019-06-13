package com.bluewhite.personnel.attendance.service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.personnel.attendance.entity.Recruit;

public interface RecruitService  extends BaseCRUDService<Recruit,Long>{
	
	/**
	 * 按条件查看招聘信息
	 * @param attendanceTime
	 * @return
	 */
	public PageResult<Recruit> findPage(Recruit recruit, PageParameter page);
	
	/**
	 * 新增招聘信息
	 * @param onlineOrder
	 */
	public Recruit addRecruit(Recruit recruit);
	
	/**
	 * 删除
	 * 
	 * @param ids
	 *            多条id
	 * @return boolean
	 */
	public boolean deleteRecruit(String ids);
	
}
