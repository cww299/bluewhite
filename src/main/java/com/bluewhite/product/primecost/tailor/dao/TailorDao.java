package com.bluewhite.product.primecost.tailor.dao;

import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.product.primecost.tailor.entity.Tailor;

public interface TailorDao  extends BaseRepository<Tailor, Long>{

	List<Tailor> findByProductId(Long productId);

}
