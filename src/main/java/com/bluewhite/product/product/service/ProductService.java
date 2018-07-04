package com.bluewhite.product.product.service;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.product.product.entity.Product;


@Service
public interface ProductService extends BaseCRUDService<Product,Long>{
	
	/**
	 * 分页查询产品列表
	 * @return
	 */
	public PageResult<Product>  findPages(Product product,PageParameter page);

}
