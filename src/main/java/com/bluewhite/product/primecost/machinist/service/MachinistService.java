package com.bluewhite.product.primecost.machinist.service;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.product.primecost.machinist.entity.Machinist;
import com.bluewhite.product.primecost.materials.entity.ProductMaterials;
@Service
public interface MachinistService extends BaseCRUDService<Machinist,Long>{
	/**
	 * 机工填写
	 * @param productMaterials
	 * @return
	 * @throws Exception
	 */
	public Machinist saveMachinist(Machinist machinist);
	/**
	 * 机工分页
	 * @param productMaterials
	 * @param page
	 * @return
	 */
	public PageResult<Machinist>  findPages(Machinist machinist, PageParameter page);
	/**
	 * 删除机工
	 * @param productMaterials
	 */
	public void deleteProductMaterials(Machinist machinist);

}
