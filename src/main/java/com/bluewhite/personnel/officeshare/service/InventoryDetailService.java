package com.bluewhite.personnel.officeshare.service;

import java.util.List;
import java.util.Map;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.personnel.officeshare.entity.InventoryDetail;

public interface InventoryDetailService  extends BaseCRUDService<InventoryDetail,Long>{

	/**
	 * 分頁查看
	 * @param InventoryDetail
	 * @param page
	 * @return
	 */
	public PageResult<InventoryDetail> findPages(InventoryDetail inventoryDetail, PageParameter page);
	
	/**
	 * 新增
	 * @param onventoryDetail
	 */
	public void addInventoryDetail(InventoryDetail onventoryDetail);
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	public int deleteInventoryDetail(String ids);
	
	/**
	 * 统计部门分摊费用
	 * @param onventoryDetail
	 * @return
	 */
	public List<Map<String, Object>> statisticalInventoryDetail(InventoryDetail onventoryDetail);
	
	

}
