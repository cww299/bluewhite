package com.bluewhite.ledger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.ledger.dao.PutStorageDao;
import com.bluewhite.ledger.entity.PutStorage;
import com.bluewhite.ledger.entity.SendGoods;
import com.bluewhite.onlineretailers.inventory.service.InventoryService;

@Service
public class PutStorageServiceImpl extends BaseServiceImpl<PutStorage, Long> implements PutStorageService {

	@Autowired
	private PutStorageDao dao;
	@Autowired
	private InventoryService inventoryService;

	@Override
	public void savePutStorage(PutStorage putStorage) {
		inventoryService.putInStorage(putStorage.getProductId(), putStorage.getInWarehouseTypeId(),putStorage.getArrivalNumber());
		dao.save(putStorage);
	}

	@Override
	public PageResult<SendGoods> findPages(PageParameter page, PutStorage putStorage) {
		// TODO Auto-generated method stub
		return null;
	}

}
