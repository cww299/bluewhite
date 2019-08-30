package com.bluewhite.product.primecost.primecost.dao;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.product.primecost.primecost.entity.PrimeCost;

public interface PrimeCostDao extends BaseRepository<PrimeCost, Long>{
	
	/**
	 * 根据产品id查询成本价格
	 * @param id
	 * @return
	 */
	PrimeCost findByProductId(Long id);

}
