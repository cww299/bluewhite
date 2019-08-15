package com.bluewhite.personnel.roomboard.service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.personnel.roomboard.entity.Advertisement;

public interface AdvertisementService  extends BaseCRUDService<Advertisement,Long>{
	
	/**
	 * 按条件查寻招聘成本
	 * @param attendanceTime
	 * @return
	 */
	public PageResult<Advertisement> findPage(Advertisement advertisement, PageParameter page);
	
	/**
	 * 新增招聘成本
	 * @param onlineOrder
	 */
	public Advertisement addAdvertisement(Advertisement advertisement);
	
	/**
	 * 查询培训费用
	 * @param onlineOrder
	 */
	public Advertisement findRecruitId(Long recruitId);
}
