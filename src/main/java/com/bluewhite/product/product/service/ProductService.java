package com.bluewhite.product.product.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.product.primecost.primecost.entity.PrimeCost;
import com.bluewhite.product.product.entity.Product;


@Service
public interface ProductService extends BaseCRUDService<Product,Long>{
	
	/**
	 * 分页查询产品列表
	 * @return
	 */
	public PageResult<Product>  findPages(Product product,PageParameter page);

	
	/**
	 * 查看产品成本价格
	 * @param product
	 * @param page
	 * @return
	 */
	public PrimeCost getPrimeCost(PrimeCost primeCost,HttpServletRequest request);


	/**
	 * 查看所有的产品
	 * @param product
	 * @return
	 */
	public List<Product> getAllProduct();
	

}
