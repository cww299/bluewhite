package com.bluewhite.ledger.service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.ledger.entity.ApplyVoucher;

public interface ApplyVoucherService extends BaseCRUDService<ApplyVoucher, Long>{
	
	/**
	 * 生成请求
	 * @param applyVoucher
	 */
	public void saveApplyVoucher(ApplyVoucher applyVoucher);

}
