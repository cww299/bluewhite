package com.bluewhite.ledger.dao;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.ledger.entity.PackingChild;
import java.lang.Long;
import java.util.List;

import javax.persistence.Transient;

import java.util.Date;

public interface PackingChildDao  extends BaseRepository<PackingChild, Long>{

	/**
	 * 根据待发货单id
	 * @param sendgoodsid
	 * @return
	 */
	List<PackingChild> findBySendGoodsId(Long sendGoodsId);
	
	/**
	 * 按产品和客户查找
	 */
	List<PackingChild> findByProductIdAndCustomerIdAndAudit(Long productId,Long customerId,Integer audit);
	
	/**
	 * 按发货日期查找
	 */
	List<PackingChild> findBySendDateBetween(Date orderTimeBegin, Date orderTimeEnd);
}
