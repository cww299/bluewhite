package com.bluewhite.product.product.dao;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.product.product.entity.Product;

/**
 * 
 * @author zhangliang
 *
 */
public interface ProductDao extends BaseRepository<Product, Long> {
	
	/**
	 * 根据产品编号查询
	 * @return
	 */
	public Product  findByNumber(String number);
	
	public Product  findByNameAndOriginDepartmentIsNull(String name );
	
	public Product findByNumberNotNullAndName(String name);


	public Product findByDepartmentNumber(String departmentNumber);

}
