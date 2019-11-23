package com.bluewhite.ledger.service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.ledger.entity.RefundBills;

public interface RefundBillsService extends BaseCRUDService<RefundBills,Long>{
	
	/**
	 * 新增退货加工单
	 * @param refundBills
	 */
	public void saveRefundBills(RefundBills refundBills);
	
	/**
	 * 删除加工退货单
	 * @param ids
	 * @return
	 */
	public int deleteRefundBills(String ids);
	
	

}
