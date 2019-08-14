package com.bluewhite.product.product.dao;

import java.util.List;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.product.product.entity.Product;
import java.lang.String;

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
	
	/**
	 * 根据名称且部门编号是null
	 * @param name
	 * @return
	 */
	public Product  findByNameAndOriginDepartmentIsNull(String name );
	
	
	public Product findByNumberNotNullAndName(String name);


	public Product findByDepartmentNumber(String departmentNumber);
	
	/**
	 * 查找出所有的产品
	 * @return
	 */
	public List<Product> findByNumberNotNull();
	
	
	List<Product> findByNumberNotNullAndNameLike(String name);

}
