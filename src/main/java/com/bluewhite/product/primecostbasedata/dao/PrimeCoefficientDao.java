package com.bluewhite.product.primecostbasedata.dao;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.product.primecostbasedata.entity.PrimeCoefficient;

public interface PrimeCoefficientDao extends BaseRepository<PrimeCoefficient, Long>{

	/**
	 * 根据类型查找数据
	 * @param type
	 * @return
	 */
	PrimeCoefficient findByType(String type);

}
