package com.bluewhite.personnel.roomboard.service;

import java.util.List;
import java.util.Map;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.personnel.roomboard.entity.Recruit;

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
	 * 招聘汇总
	 * @param onlineOrder
	 */
	public  List<Map<String, Object>> Statistics(Recruit recruit);
	
	public  List<Recruit> soon(Recruit recruit);
	
	public Map<String,List<Map<String, Object>>>  users(Recruit recruit);
	
	public Map<String,List<Map<String, Object>>>  analysis(Recruit recruit);
	
	public List<Recruit> findList();
	public List<Map<String, Object>> findfGroupList();
	/*
	 * 按条件查询被招聘的人
	 */
	public List<Recruit> findCondition(Recruit recruit);
	
	/*
	 * 按条件查询被招聘的人合计奖金
	 */
	public Recruit findPrice(Recruit recruit);
	
	/**
	 * 招聘每天汇总
	 * @param onlineOrder
	 */
	public  List<Map<String, Object>> sumday(Recruit recruit);
	
	public int deletes(String[] ids);

	public int updateRecruit(String[] ids, Integer state);
}
