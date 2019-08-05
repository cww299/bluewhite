package com.bluewhite.ledger.service;

import java.util.List;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.ledger.entity.Bill;
import com.bluewhite.ledger.entity.Packing;
import com.bluewhite.ledger.entity.ReceivedMoney;

public interface ReceivedMoneyService extends BaseCRUDService<ReceivedMoney,Long>{
	
	/**
	 * 根据条件查找到款
	 * @param receivedMoney
	 * @return
	 */
	List<ReceivedMoney> receivedMoneyList(Bill bill);

	/**
	 * 分页查看已到货款
	 * @param receivedMoney
	 * @param page
	 * @return
	 */
	public PageResult<ReceivedMoney> receivedMoneyPage(ReceivedMoney receivedMoney, PageParameter page);
	
	/**
	 * 删除货款
	 * @param ids
	 * @return
	 */
	int deleteReceivedMoney(String ids);

}
