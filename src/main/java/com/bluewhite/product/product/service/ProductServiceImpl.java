package com.bluewhite.product.product.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.product.product.dao.ProductDao;
import com.bluewhite.product.product.entity.Product;
import com.bluewhite.production.procedure.dao.ProcedureDao;
import com.bluewhite.production.procedure.entity.Procedure;
@Service
public class ProductServiceImpl  extends BaseServiceImpl<Product, Long> implements ProductService{
	
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ProcedureDao procedureDao;

	@Override
	public PageResult<Product> findPages(Product product,PageParameter page) {
		  Page<Product> pages = productDao.findAll((root,query,cb) -> {
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
		  		for(Product pro : pages.getContent()){
		  			  List<Procedure> procedureList = procedureDao.findByProductIdAndTypeAndFlag(pro.getId(), product.getType(),0);
		  				  if(procedureList!=null && procedureList.size()>0){
		  						  pro.setHairPrice(procedureList.get(0).getHairPrice());
		  						  pro.setDepartmentPrice(procedureList.get(0).getDepartmentPrice());
		  						  if(product.getType()==3){
		  							  pro.setDeedlePrice(procedureList.get(0).getDeedlePrice());
		  						  }
		  				  }
			  		  }
	        PageResult<Product> result = new PageResult<>(pages,page);
	        return result;
	    }


}
