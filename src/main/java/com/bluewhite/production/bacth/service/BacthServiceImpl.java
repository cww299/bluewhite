package com.bluewhite.production.bacth.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.production.bacth.dao.BacthDao;
import com.bluewhite.production.bacth.entity.Bacth;
@Service
public class BacthServiceImpl extends BaseServiceImpl<Bacth, Long> implements BacthService{

	@Autowired
	private BacthDao dao;
	
	@Override
	public PageResult<Bacth> findPages(Bacth param, PageParameter page) {
			  Page<Bacth> pages = dao.findAll((root,query,cb) -> {
		        	List<Predicate> predicate = new ArrayList<>();
		        	//按id过滤
		        	if (param.getId() != null) {
						predicate.add(cb.equal(root.get("id").as(Long.class),param.getId()));
					}
		        	//按产品id
		        	if(param.getProductId()!=null){
		        		predicate.add(cb.equal(root.get("productId").as(Long.class),param.getId()));
		        	}
		        	//按产品名称
		        	if(!StringUtils.isEmpty(param.getName())){
		        		predicate.add(cb.equal(root.get("product").get("name").as(String.class), "%"+param.getName()+"%"));
		        	}
		        	//按产品编号
		        	if(!StringUtils.isEmpty(param.getProductNumber())){
		        		predicate.add(cb.equal(root.get("product").get("number").as(String.class), "%"+param.getProductNumber()+"%"));
		        	}
		        	//按
		        	if(!StringUtils.isEmpty(param.getType())){
		        		predicate.add(cb.equal(root.get("type").as(Integer.class), param.getType()));
		        	}
		            //按时间过滤
					if (!StringUtils.isEmpty(param.getOrderTimeBegin()) &&  !StringUtils.isEmpty(param.getOrderTimeEnd()) ) {
						predicate.add(cb.between(root.get("createdAt").as(Date.class),
								param.getOrderTimeBegin(),
								param.getOrderTimeEnd()));
					}
		        	
					Predicate[] pre = new Predicate[predicate.size()];
					query.where(predicate.toArray(pre));
		        	return null;
		        }, page);
		        PageResult<Bacth> result = new PageResult<>(pages,page);
		        return result;
		    }

}
