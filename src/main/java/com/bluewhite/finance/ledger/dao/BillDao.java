package com.bluewhite.finance.ledger.dao;

import java.util.Date;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.finance.ledger.entity.Bill;

public interface BillDao extends BaseRepository<Bill, Long>{
	
	/**
	 * 根据订单日期查询在这个月中有没有符合的乙方账单记录
	 * @param partyNamesId
	 * @param firstDayOfMonth
	 * @param lastDayOfMonth
	 * @return
	 */
	Bill findByPartyNamesIdAndBillDateBetween(Long partyNamesId, Date firstDayOfMonth, Date lastDayOfMonth);

}
