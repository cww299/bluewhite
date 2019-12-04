package com.bluewhite.ledger.service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.ledger.entity.MaterialRequisition;

public interface MaterialRequisitionService  extends BaseCRUDService<MaterialRequisition, Long>{
	
	/**
	 * 生成领料单
	 * @param materialRequisition
	 */
	public void saveMaterialRequisition(MaterialRequisition materialRequisition);
	
	/**
	 * 修改领料单
	 * @param materialRequisition
	 */
	public void updateMaterialRequisition(MaterialRequisition materialRequisition);
	
	/**
	 * 分页查看领料单
	 */
	public PageResult<MaterialRequisition> findPages(PageParameter page, MaterialRequisition materialRequisition);
	
	/**
	 * 审核领料单
	 * @param ids
	 * @return
	 */
	public int auditMaterialRequisition(String ids);
	
	/**
	 * 删除领料单
	 * @param ids
	 * @return
	 */
	public int deleteMaterialRequisition(String ids);
	
}
