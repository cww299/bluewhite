package com.bluewhite.product.primecost.pack.dao;

import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.product.primecost.pack.entity.Pack;

public interface PackDao extends BaseRepository<Pack, Long>{
	
	List<Pack> findByProductId(Long productId);

}
