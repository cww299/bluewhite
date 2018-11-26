package com.bluewhite.finance.ledger.service;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.finance.ledger.entity.Bill;

@Service
public interface BillService extends BaseCRUDService<Bill,Long>{
	
	/**
	 * 分页查看乙方账单
	 * @param bill
	 * @param page
	 * @return
	 */
	PageResult<Bill> findPages(Bill bill, PageParameter page);

}
