package com.bluewhite.personnel.attendance.service;

import java.util.List;
import java.util.Map;

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
	
	public  List<Map<String, Object>> Statistics(Recruit recruit);
	
	public  List<Recruit> soon(Recruit recruit);
	
	public Map<String,List<Map<String, Object>>>  users(Recruit recruit);
	
	public Map<String,List<Map<String, Object>>>  analysis(Recruit recruit);
	
	public List<Recruit> findList();
}
