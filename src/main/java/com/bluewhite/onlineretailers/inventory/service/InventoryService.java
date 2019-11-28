package com.bluewhite.onlineretailers.inventory.service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.onlineretailers.inventory.entity.Inventory;

public interface InventoryService extends BaseCRUDService<Inventory,Long>{
	
	/**
	 * 入库操作
	 */
	public void putInStorage(Long  productId, Long inWarehouseTypeId,Integer arrivalNumber); 



	
}
