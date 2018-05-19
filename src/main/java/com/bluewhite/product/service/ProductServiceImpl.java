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
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.product.dao.ProductDao;
import com.bluewhite.product.entity.Product;
import com.bluewhite.production.procedure.dao.ProcedureDao;
import com.bluewhite.production.procedure.entity.Procedure;
import com.bluewhite.production.productionutils.ProTypeUtils;
@Service
public class ProductServiceImpl  extends BaseServiceImpl<Product, Long> implements ProductService{
	
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ProcedureDao procedureDao;

	@Override
	public PageResult<Product> findPages(Product product,PageParameter page) {
		  Page<Product> productPages = productDao.findAll((root,query,cb) -> {
	        	List<Predicate> predicate = new ArrayList<>();
	        	//按id过滤
	        	if (product.getId() != null) {
					predicate.add(cb.equal(root.get("id").as(Long.class),product.getId()));
				}
	        	//按编号过滤
	        	if (product.getNumber() != null) {
					predicate.add(cb.like(root.get("number").as(String.class),"%"+product.getNumber()+"%"));
				}
	        	//按产品名称过滤
	        	if (product.getName() != null) {
					predicate.add(cb.like(root.get("name").as(String.class),"%"+product.getName()+"%"));
				}
				Predicate[] pre = new Predicate[predicate.size()];
				query.where(predicate.toArray(pre));
	        	return null;
	        }, page);
		  if(product.getType()!=null){
			  this.formulaPrice(productPages,product.getType());
		  }
	        PageResult<Product> result = new PageResult<>(productPages,page);
	        return result;
	    }
	
	/**
	 * 计算当部门预计生产价格
	 * @param productPages
	 */
	private void formulaPrice(Page<Product> productPages,Integer type) {
			List<Product> productList = productPages.getContent();
			for(Product product : productList){
				List<Procedure> procedureList = procedureDao.findByProductIdAndType(product.getId(), type);
				Double sumTime = 0.0;
				for(Procedure procedure : procedureList){
					sumTime += procedure.getWorkingTime();
				}
				Double sumPrice = ProTypeUtils.sumProTypePrice(sumTime,type);
				product.setDepartmentPrice(NumUtils.round(sumPrice));
			}
		
		
	}

}
