package com.bluewhite.product.primecost.materials.service;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.product.primecost.materials.entity.ProductMaterials;

@Service
public interface ProductMaterialsService extends BaseCRUDService<ProductMaterials,Long>{
	
	/**
	 * dd除裁片以外的所有生产用料填写
	 * @param productMaterials
	 * @return
	 * @throws Exception
	 */
	public ProductMaterials saveProductMaterials(ProductMaterials productMaterials) throws Exception;
	
	/**
	 * dd除裁片以外的所有生产用料分页
	 * @param productMaterials
	 * @param page
	 * @return
	 */
	public PageResult<ProductMaterials> findPages(ProductMaterials productMaterials, PageParameter page);

	/**
	 * 删除dd除裁片以外的所有生产用料
	 * @param productMaterials
	 */
	public void deleteProductMaterials(Long id);

}
