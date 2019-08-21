package com.bluewhite.personnel.roomboard.service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.personnel.roomboard.entity.CostLiving;

public interface CostLivingService  extends BaseCRUDService<CostLiving,Long>{
	
	/**
	 * 按条件查看
	 * @param attendanceTime
	 * @return
	 */
	public PageResult<CostLiving> findPage(CostLiving costLiving, PageParameter page);
	/**
	 * 新增记录
	 * @param onlineOrder
	 */
	public CostLiving saveCostLiving(CostLiving costLiving);
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	public int deleteCostLiving(String ids);
	
	
	
	
}
