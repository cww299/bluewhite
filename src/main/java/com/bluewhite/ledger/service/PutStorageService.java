package com.bluewhite.ledger.service;

import java.util.List;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.ledger.entity.PutStorage;

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
	public PageResult<PutStorage> findPages(PageParameter page, PutStorage putStorage);
	
	/**
	 * 删除入库单
	 * @param ids
	 * @return
	 */
	public int deletePutStorage(String ids);
	
	/**
	 * 根据产品和库存查看实际入库单和生产计划单 （库存详情）
	 * @param warehouseTypeId
	 * @param productId
	 */
	public List<PutStorage> detailsInventory(Object warehouseTypeId, Long productId);
	
	/**
	 * 根据入库仓库和加工单id查找入库单
	 * @param inwarehousetypeid
	 * @param productId
	 * @return
	 */
	List<PutStorage> findByWarehouseTypeIdAndOrderOutSourceId(Long warehouseTypeId, Long orderOutSourceId);
	

}
