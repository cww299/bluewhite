package com.bluewhite.personnel.officeshare.dao;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.personnel.officeshare.entity.InventoryDetail;
import java.util.Date;
import java.util.List;

public interface InventoryDetailDao extends BaseRepository<InventoryDetail, Long> {

	/**
	 * 按出庫时间查询
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<InventoryDetail> findByFlagAndTimeBetween(Integer flag , Date startTime, Date endTime);
}
