package com.bluewhite.product.primecost.needlework.dao;

import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.product.primecost.needlework.entity.Needlework;

public interface NeedleworkDao extends BaseRepository<Needlework, Long>{
	
	
	List<Needlework> findByProductId(Long productId);

}
