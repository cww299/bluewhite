package com.bluewhite.product.primecostbasedata.dao;

import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.product.primecostbasedata.entity.BaseFour;

public interface BaseFourDao  extends BaseRepository<BaseFour, Long>{

	
	List<BaseFour> findBySewingOrderLike(String sewingOrder);

}
