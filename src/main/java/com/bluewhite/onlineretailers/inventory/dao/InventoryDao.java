package com.bluewhite.onlineretailers.inventory.dao;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.onlineretailers.inventory.entity.Inventory;

public interface InventoryDao extends BaseRepository<Inventory, Long>{
	
	
	
	/**
	 * 根据商品id和仓库id的获取库存
	 * @return
	 */
	Inventory findByProductIdAndWarehouseId(Long productId, Long warehouseId);
	
	
	

}
