package com.bluewhite.product.primecostbasedata.dao;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.product.primecostbasedata.entity.BaseThree;

public interface BaseThreeDao extends BaseRepository<BaseThree, Long>{
	
	BaseThree findByOrdinaryLaser(Double number);

}
