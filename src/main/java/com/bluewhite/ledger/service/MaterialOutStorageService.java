package com.bluewhite.ledger.service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.ledger.entity.MaterialOutStorage;
import com.bluewhite.ledger.entity.MaterialPutStorage;

public interface MaterialOutStorageService  extends BaseCRUDService<MaterialOutStorage, Long>{
	
	/**
	 * 生成出库单
	 * @param materialOutStorage
	 */
	public void saveMaterialOutStorage(MaterialOutStorage materialOutStorage);
	
	/**
	 * 出库单列表
	 * @param page
	 * @param materialOutStorage
	 * @return
	 */
	public PageResult<MaterialOutStorage> findPages(PageParameter page, MaterialOutStorage materialOutStorage);
	
	/**
	 * 删除出库单
	 * @param ids
	 */
	public int deleteMaterialOutStorage(String ids);
	
	/**
	 * 领料出库
	 * @param materialOutStorage
	 */
	public void outboundMaterialRequisition(MaterialOutStorage materialOutStorage);

}
