package com.bluewhite.ledger.dao;

import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.ledger.entity.Sale;

public interface SaleDao  extends BaseRepository<Sale, Long>{
	
	/**
	 * 按产品和客户查找
	 */
	List<Sale> findByProductIdAndCustomerIdAndAudit(Long productId,Long customerId,Integer audit);

}
