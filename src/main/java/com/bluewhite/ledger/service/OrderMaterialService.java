package com.bluewhite.ledger.service;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.ledger.entity.OrderMaterial;
@Service
public interface OrderMaterialService extends BaseCRUDService<OrderMaterial, Long> {
	
	/**
	 * 分页订单耗料
	 * 
	 * @param mixed
	 * @param page
	 * @return
	 */
	public PageResult<OrderMaterial> findPages(OrderMaterial orderMaterial, PageParameter page);
	
	/**
	 * 生成订单耗料
	 * @param ids
	 */
	public int confirmOrderMaterial(String ids);
	
	/**
	 * 修改订单耗料
	 * @param ids
	 */
	public void updateOrderMaterial(OrderMaterial orderMaterial);
	
	/**
	 * 删除订单耗料
	 * @param ids
	 * @return
	 */
	public int deleteOrderMaterial(String ids);
	
	/**
	 * 审核订单耗料
	 * @param ids
	 * @return
	 */
	public int auditOrderMaterial(String ids);
	
}
