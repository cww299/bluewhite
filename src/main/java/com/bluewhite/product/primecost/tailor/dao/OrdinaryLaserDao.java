package com.bluewhite.product.primecost.tailor.dao;

import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.product.primecost.tailor.entity.OrdinaryLaser;

public interface OrdinaryLaserDao  extends BaseRepository<OrdinaryLaser, Long>{
	
	/**
	 * 根据产品id查询
	 * @param productId
	 * @return
	 */
	List<OrdinaryLaser> findByProductId(Long productId);
	
	/**
	 * 根据裁剪id
	 * @param tailorId
	 * @return
	 */
	OrdinaryLaser findByTailorId(Long tailorId);
	
	/**
	 * 根据裁剪id和裁减类型id
	 * @param tailorId
	 * @return
	 */
	OrdinaryLaser findByTailorIdAndTailorTypeId(Long tailorId , Long tailorTypeId);

}
