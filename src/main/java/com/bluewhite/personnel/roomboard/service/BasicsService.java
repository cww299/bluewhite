package com.bluewhite.personnel.roomboard.service;

import java.util.List;
import java.util.Map;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.personnel.roomboard.entity.Basics;

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
	
	/**
	 * 部门汇总
	 * 
	 */
	public List<Map<String, Object>> findBasicsSummary(Basics basics);
}
