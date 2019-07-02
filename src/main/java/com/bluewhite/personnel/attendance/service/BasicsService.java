package com.bluewhite.personnel.attendance.service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.personnel.attendance.entity.Basics;

public interface BasicsService  extends BaseCRUDService<Basics,Long>{
	
	/**
	 * 按条件查寻招聘成本汇总
	 * @param attendanceTime
	 * @return
	 */
	public Basics  findBasics(Basics basics);
	
	/**
	 * 新增招聘汇总
	 * @param onlineOrder
	 */
	public Basics addBasics(Basics basics);
}
