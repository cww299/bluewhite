package com.bluewhite.onlineretailers.inventory.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.onlineretailers.inventory.entity.OnlineOrderChild;

public interface OnlineOrderChildDao  extends BaseRepository<OnlineOrderChild, Long>{
	
	
	/***
	 * 获取已发货的销售子单
	 * @param status
	 * @param orderTimeBegin
	 * @param orderTimeEnd
	 * @return
	 */
	List<OnlineOrderChild> findByStatusAndCreatedAtBetween(String status,Date orderTimeBegin,Date orderTimeEnd);
	
	/**
	 * 获取商品最后一次订单单价
	 * @param commodityId
	 * @return
	 * nativeQuery 表示原生sql 支持limit
	 */
	@Query(nativeQuery=true,value ="select price from online_order_child where commodity_id=?1 ORDER BY created_at DESC LIMIT 1")
	Double getOnlineOrderPrice(Long commodityId);

}
