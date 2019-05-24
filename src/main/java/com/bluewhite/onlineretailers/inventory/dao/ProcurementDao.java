package com.bluewhite.onlineretailers.inventory.dao;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.onlineretailers.inventory.entity.Procurement;

public interface ProcurementDao  extends BaseRepository<Procurement, Long>{
	
	/**
	 * 根据父id查询
	 * @param parentId
	 * @return
	 */
	Procurement findByParentId(Long parentId);

}
