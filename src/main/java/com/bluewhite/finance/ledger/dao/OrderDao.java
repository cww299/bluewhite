package com.bluewhite.finance.ledger.dao;


import java.util.Date;
import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.finance.ledger.entity.Order;

public interface OrderDao extends BaseRepository<Order, Long>{
	
	
  public List<Order> findBetweenByCreatedAt(Date fristTime ,Date lastTime);

  /**
	 * 根据订单日期查询在这个月中有没有符合的乙方订单记录
	 * @param partyNamesId
	 * @param firstDayOfMonth
	 * @param lastDayOfMonth
	 * @return
	 */
  public List<Order> findByPartyNamesIdAndContractTimeBetween(Long partyNamesId, Date firstDayOfMonth,
		Date lastDayOfMonth);
	
  public List<Order> findByBatchNumberAndProductNameAndContractTimeBetween(String batchNumber,String productName,Date firstDayOfMonth,Date lastDayOfMonth);
}
