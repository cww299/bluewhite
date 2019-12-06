package com.bluewhite.production.temporarypack;

import com.bluewhite.base.BaseRepository;
import java.lang.Long;
import com.bluewhite.production.temporarypack.QuantitativeChild;
import java.util.List;

public interface QuantitativeChildDao  extends BaseRepository<QuantitativeChild, Long>{
	
	/**
	 * 根据下货单
	 * @param undergoodsid
	 * @return
	 */
	List<QuantitativeChild> findByUnderGoodsId(Long undergoodsid);

}
