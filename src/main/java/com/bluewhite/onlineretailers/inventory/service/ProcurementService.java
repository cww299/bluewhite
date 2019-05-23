package com.bluewhite.onlineretailers.inventory.service;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.onlineretailers.inventory.entity.Procurement;
@Service
public interface ProcurementService  extends BaseCRUDService<Procurement,Long>{
	
	/**
	 * 分页查询
	 * @param param
	 * @param page
	 * @return
	 */
	PageResult<Procurement> findPage(Procurement param, PageParameter page);
	
	/**
	 * 新增采购单
	 * @param procurement
	 */
	Procurement saveProcurement(Procurement procurement);
	
	/**
	 * 作废采购单
	 * @param ids
	 * @return
	 */
	int deleteProcurement(String ids);

}
