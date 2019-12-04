package com.bluewhite.onlineretailers.inventory.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.onlineretailers.inventory.dao.InventoryDao;
import com.bluewhite.onlineretailers.inventory.entity.Inventory;
import com.bluewhite.product.product.dao.ProductDao;
import com.bluewhite.product.product.entity.Product;
@Service
public class InventoryServiceImpl extends BaseServiceImpl<Inventory, Long> implements InventoryService{
	
	@Autowired
	private InventoryDao dao;
	@Autowired
	private ProductDao productDao;

	@Override
	public void putInStorage(Long  productId, Long inWarehouseTypeId) {
		// 库存表
		Product product = productDao.findOne(productId);
		if (product != null) {
			List<Inventory> inventoryList = product.getInventorys().stream().filter(Inventory -> Inventory
					.getWarehouseTypeId().equals(inWarehouseTypeId))
					.collect(Collectors.toList());
			// 当前仓库只存在一种
			Inventory inventory = null;
			if (inventoryList.size() > 0) {
				inventory = inventoryList.get(0);
			} else {
				inventory = new Inventory();
				inventory.setProductId(productId);
				inventory.setWarehouseTypeId(inWarehouseTypeId);
			}
			dao.save(inventory);
		}
		
	}

	

}
