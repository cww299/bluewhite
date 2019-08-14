package com.bluewhite.ledger.dao;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.ledger.entity.SendGoods;

public interface SendGoodsDao extends BaseRepository<SendGoods, Long>{
	
	/**
	 * 根据批次和客户查找
	 * @param bacthNumber
	 * @param id
	 * @return
	 */
	public SendGoods findByBacthNumberAndCustomerId(String bacthNumber,Long id);

}
