package com.bluewhite.ledger.dao;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.ledger.entity.SendGoods;

public interface SendGoodsDao extends BaseRepository<SendGoods, Long>{
	
	public SendGoods findByBacthNumberAndCustomerId(String bacthNumber,Long id);

}
