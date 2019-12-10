package com.bluewhite.ledger.service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.ledger.entity.ApplyVoucher;

public interface ApplyVoucherService extends BaseCRUDService<ApplyVoucher, Long>{
	
	/**
	 * 生成请求
	 * @param applyVoucher
	 */
	public void saveApplyVoucher(ApplyVoucher applyVoucher);
	
	/**
	 * 分页查看
	 * @param applyVoucher
	 * @param page
	 * @return
	 */
	public PageResult<ApplyVoucher> findPages(ApplyVoucher applyVoucher, PageParameter page);
	
	/**
	 * 请求通过
	 * @param ids
	 * @return
	 */
	public int passApplyVoucher(String ids);

}
