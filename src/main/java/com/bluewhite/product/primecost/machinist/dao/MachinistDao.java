package com.bluewhite.product.primecost.machinist.dao;

import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.product.primecost.machinist.entity.Machinist;

public interface MachinistDao extends BaseRepository<Machinist, Long>{

	List<Machinist> findByProductId(Long productId);

}
