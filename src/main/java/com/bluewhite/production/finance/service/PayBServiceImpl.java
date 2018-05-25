package com.bluewhite.production.finance.service;

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
import com.bluewhite.production.bacth.entity.Bacth;
import com.bluewhite.production.finance.dao.PayBDao;
import com.bluewhite.production.finance.entity.PayB;
@Service
public class PayBServiceImpl extends BaseServiceImpl<PayB, Long> implements PayBService{
	
	@Autowired
	private PayBDao dao;
	
	@Override
	public PageResult<PayB> findPages(PayB param, PageParameter page) {
			  Page<PayB> pages = dao.findAll((root,query,cb) -> {
		        	List<Predicate> predicate = new ArrayList<>();
		        	//按id过滤
		        	if (param.getId() != null) {
						predicate.add(cb.equal(root.get("id").as(Long.class),param.getId()));
					}
		        	//按产品id
		        	if(param.getProductId()!=null){
		        		predicate.add(cb.equal(root.get("productId").as(Long.class),param.getProductId()));
		        	}
		        	//按产品名称
		        	if(!StringUtils.isEmpty(param.getProductName())){
		        		predicate.add(cb.like(root.get("productName").as(String.class), "%"+param.getProductName()+"%"));
		        	}
		        	//按产品编号
		        	if(!StringUtils.isEmpty(param.getUserName())){
		        		predicate.add(cb.like(root.get("userName").as(String.class), "%"+param.getUserName()+"%"));
		        	}
		        	//按批次
		        	if(!StringUtils.isEmpty(param.getBacth())){
		        		predicate.add(cb.like(root.get("bacth").as(String.class), "%"+param.getBacth()+"%"));
		        	}
		        	//按类型
		        	if(!StringUtils.isEmpty(param.getType())){
		        		predicate.add(cb.equal(root.get("type").as(Integer.class), param.getType()));
		        	}
		            //按时间过滤
					if (!StringUtils.isEmpty(param.getOrderTimeBegin()) &&  !StringUtils.isEmpty(param.getOrderTimeEnd()) ) {
						predicate.add(cb.between(root.get("allotTime").as(Date.class),
								param.getOrderTimeBegin(),
								param.getOrderTimeEnd()));
					}
					Predicate[] pre = new Predicate[predicate.size()];
					query.where(predicate.toArray(pre));
		        	return null;
		        }, page);
		        PageResult<PayB> result = new PageResult<>(pages,page);
		        return result;
		    }

}
