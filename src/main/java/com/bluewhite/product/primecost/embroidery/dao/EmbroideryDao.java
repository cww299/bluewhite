package com.bluewhite.product.primecost.embroidery.dao;

import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.product.primecost.embroidery.entity.Embroidery;

public interface EmbroideryDao extends BaseRepository<Embroidery, Long>{

	List<Embroidery> findByProductId(Long productId);

}
