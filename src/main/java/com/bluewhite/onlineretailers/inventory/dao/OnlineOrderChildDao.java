package com.bluewhite.onlineretailers.inventory.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.Transient;

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
	 */
	@Query("select price from OnlineOrderChild c group by c.commodityId HAVING c.commodityId=?1")
	double getOnlineOrderPrice(Long commodityId);

}
