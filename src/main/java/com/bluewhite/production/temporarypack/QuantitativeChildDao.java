package com.bluewhite.production.temporarypack;

import java.util.List;

import com.bluewhite.base.BaseRepository;

public interface QuantitativeChildDao  extends BaseRepository<QuantitativeChild, Long>{
	
	/**
	 * 根据下货单
	 * @param undergoodsid
	 * @return
	 */
	List<QuantitativeChild> findByUnderGoodsId(Long undergoodsId);
	
	/**
     * 根据下货单in
     * @param undergoodsid
     * @return
     */
    List<QuantitativeChild> findByUnderGoodsIdIn(List<Long> undergoodsIds);
	
	/**
	 * 根据多个id查询
	 * @param ids
	 * @return
	 */
	List<QuantitativeChild> findByIdIn(List<Long> ids);

	List<QuantitativeChild> findBySaleId(Long saleId);

}
