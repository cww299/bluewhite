package com.bluewhite.ledger.service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.ledger.entity.PutStorage;
import com.bluewhite.ledger.entity.SendGoods;

public interface PutStorageService extends BaseCRUDService<PutStorage, Long> {
	
	/**
	 * 仓库收货 产生入库单
	 * @param id
	 * @param number
	 */
	public void savePutStorage(PutStorage putStorage);
	
	/**
	 * 入库单分页
	 * @param page
	 * @param putStorage
	 */
	public  PageResult<SendGoods> findPages(PageParameter page, PutStorage putStorage);

}
