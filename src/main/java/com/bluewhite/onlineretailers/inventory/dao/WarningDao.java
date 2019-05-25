package com.bluewhite.onlineretailers.inventory.dao;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.onlineretailers.inventory.entity.Warning;

public interface WarningDao extends BaseRepository<Warning, Long>{
	
	/**
	 * 根据仓库类型和预警类型获取库存预警
	 * @param type
	 * @param warehouseId
	 * @return
	 */
	Warning findByTypeAndWarehouseId(int type,Long warehouseId);

}
