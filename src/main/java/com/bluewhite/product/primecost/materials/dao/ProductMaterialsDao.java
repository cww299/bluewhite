package com.bluewhite.product.primecost.materials.dao;

import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.product.primecost.materials.entity.ProductMaterials;

public interface ProductMaterialsDao extends BaseRepository<ProductMaterials, Long>{
	
	/**
	 * 根据产品id查找dd
	 * @param productId
	 * @return
	 */
	List<ProductMaterials> findByProductId(Long productId);

}
