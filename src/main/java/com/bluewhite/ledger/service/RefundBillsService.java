package com.bluewhite.ledger.service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.ledger.entity.MaterialPutStorage;
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
	
	/**
	 * 修改
	 * @param refundBills
	 */
	public void updateRefundBills(RefundBills refundBills);
	
	/**
	 * 分页查看
	 * @param refundBills
	 * @param page
	 * @return
	 */
	public PageResult<RefundBills> findPages(RefundBills refundBills, PageParameter page);

    /**审核
     * @param ids
     * @return
     */
    public int auditRefundBills(String ids);
	
	

}
