package com.bluewhite.ledger.dao;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.ledger.entity.ApplyVoucher;
import java.lang.Long;
import java.util.List;

public interface ApplyVoucherDao  extends BaseRepository<ApplyVoucher, Long>{
	
	/**
	 * 根据发货单获取申请单
	 */
	List<ApplyVoucher> findBySendGoodsIdAndPass(Long sendgoodsid,Integer pass);
	
	
	/**
	 * 根据加工单获取申请单
	 */
	List<ApplyVoucher> findByOrderOutSourceIdAndPass(Long orderoutsourceid,Integer pass);
}
