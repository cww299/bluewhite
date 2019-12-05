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


	

}
