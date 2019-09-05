package com.bluewhite.personnel.roomboard.dao;

import java.util.Date;
import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.personnel.roomboard.entity.CostLiving;

public interface CostLivingDao extends BaseRepository<CostLiving, Long>{
	
	
	/**
	 * 根据地点，类型，时间查找生活费用
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	CostLiving findByCostTypeIdAndSiteTypeIdAndBeginTimeAndEndTime(Long costTypeId, Long siteTypeId,Date beginTime,Date endTime);
	
	/**
	 *  根据地点，类型
	 * @param CostTypeId
	 * @param siteTypeId
	 * @return
	 */
	List<CostLiving> findByCostTypeIdAndSiteTypeId(Long CostTypeId, Long siteTypeId);
}
