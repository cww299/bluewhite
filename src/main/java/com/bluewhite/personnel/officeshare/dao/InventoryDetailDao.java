package com.bluewhite.personnel.officeshare.dao;

import java.util.Date;
import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.personnel.officeshare.entity.InventoryDetail;

public interface InventoryDetailDao extends BaseRepository<InventoryDetail, Long> {

	/**
	 * 按出庫时间查询
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<InventoryDetail> findByFlagAndStatusAndTimeBetween(Integer flag ,Integer status, Date startTime, Date endTime);
	
	/**
	 * 按出庫时间 部门查询
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<InventoryDetail> findByFlagAndStatusAndTimeBetweenAndOrgNameId(Integer flag ,Integer status, Date startTime, Date endTime,Long orgNameId);
	/**
     * 根据物品查找出入库记录
     * 
     */
	List<InventoryDetail> findByOfficeSuppliesId(Long id);
}
