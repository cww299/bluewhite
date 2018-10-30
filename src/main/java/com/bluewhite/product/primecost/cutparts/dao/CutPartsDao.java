package com.bluewhite.product.primecost.cutparts.dao;

import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.product.primecost.cutparts.entity.CutParts;

/**
 * 
 * @author zhangliang
 *
 */
public interface CutPartsDao extends BaseRepository<CutParts, Long>{
	
	/**
	 * 
	 * @param productId
	 * @return
	 */
	List<CutParts> findByProductId(Long productId);

	List<CutParts> findByProductIdAndOverstockId(Long productId, Long id);

}
