package com.bluewhite.finance.ledger.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.DatesUtil;
import com.bluewhite.finance.ledger.dao.OrderDao;
import com.bluewhite.finance.ledger.entity.Order;

@Service
public class OrderServiceImpl extends BaseServiceImpl<Order, Long> implements OrderService{
	 
	@Autowired
	private OrderDao dao;
	@Override
	public PageResult<Order> findPages(Order param, PageParameter page) {
		if(!StringUtils.isEmpty(param.getContractTime())){
		Sort sort = new Sort(Direction.DESC,"createdAt");
		page.setSort(sort);
		}
		Page<Order> pages = dao.findAll((root,query,cb) -> {
        	List<Predicate> predicate = new ArrayList<>();
        	//按id过滤
        	if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class),param.getId()));
			}
        	
        	if(!StringUtils.isEmpty(param.getContractTime())){
        		
					predicate.add(cb.between(root.get("contractTime").as(Date.class),
							DatesUtil.getFirstDayOfMonth(param.getContractTime()),
							DatesUtil.getLastDayOfMonth(param.getContractTime())));
        	}
			Predicate[] pre = new Predicate[predicate.size()];
			
			query.where(predicate.toArray(pre));
			
        	return null;
        },page);
		PageResult<Order> result = new PageResult<Order>(pages, page);
        return result;
	}

	@Override
	public void addOrder(Order order) {
	
		List<Order> orderList  = this.findPages(order, new PageParameter()).getRows();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM") ;
		String w=dateFormat.format(order.getContractTime());
		if(orderList.size()>0){
		order.setSalesNumber(w+"-"+"#"+(orderList.size()+1));
		}else{
			order.setSalesNumber(w+"-"+"#"+"1");
		}
		dao.save(order);
	}
}
