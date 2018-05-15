package com.bluewhite.product.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.product.dao.ProductDao;
import com.bluewhite.product.entity.Product;
@Service
public class ProductServiceImpl  extends BaseServiceImpl<Product, Long> implements ProductService{
	
	@Autowired
	private ProductDao productDao;

	@Override
	public PageResult<Product> findPages(Product product,PageParameter page) {
		  Page<Product> pageMessages = productDao.findAll((root,query,cb) -> {
	        	List<Predicate> predicate = new ArrayList<>();
				Predicate[] pre = new Predicate[predicate.size()];
				query.where(predicate.toArray(pre));
	        	return null;
	        }, page);
	        PageResult<Product> result = new PageResult<>(pageMessages);
	        return result;
	    }

}
