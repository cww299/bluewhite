package com.bluewhite.product.primecost.tailor.dao;

import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.product.primecost.tailor.entity.OrdinaryLaser;

public interface OrdinaryLaserDao  extends BaseRepository<OrdinaryLaser, Long>{

	List<OrdinaryLaser> findByProductId(Long productId);
	
	OrdinaryLaser findByTailorId(Long tailorId);

}
