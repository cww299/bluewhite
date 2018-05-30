package com.bluewhite.production.finance.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.production.finance.dao.CollectPayDao;
import com.bluewhite.production.finance.entity.CollectPay;
@Service
public class CollectPayServiceImpl extends BaseServiceImpl<CollectPay, Long> implements CollectPayService{
	
	@Autowired
	private CollectPayDao dao;
	
	@Override
	public PageResult<CollectPay> findPages(CollectPay param, PageParameter page) {
		 Page<CollectPay> pages = dao.findAll((root,query,cb) -> {
	        	List<Predicate> predicate = new ArrayList<>();
	        	//按id过滤
	        	if (param.getId() != null) {
					predicate.add(cb.equal(root.get("id").as(Long.class),param.getId()));
				}
	         	//按id过滤
	        	if (param.getUserId() != null) {
					predicate.add(cb.equal(root.get("userId").as(Long.class),param.getUserId()));
				}
	        	//按员工姓名
	        	if(!StringUtils.isEmpty(param.getUserName())){
	        		predicate.add(cb.like(root.get("userName").as(String.class), "%"+param.getUserName()+"%"));
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
	        PageResult<CollectPay> result = new PageResult<CollectPay>(pages,page);
	        return result;
	    }

	@Override
	public List<CollectPay> collect(CollectPay collectPay) {
		PageParameter page  = new PageParameter();
		page.setSize(Integer.MAX_VALUE);
		List<CollectPay>  collectPayList = this.findPages(collectPay, page).getRows();
		Map<Object, List<CollectPay>> mapCollectPay = collectPayList.stream().collect(Collectors.groupingBy(CollectPay::getUserId,Collectors.toList()));
		List<CollectPay> list = new ArrayList<CollectPay>();
		for(Object ps : mapCollectPay.keySet()){
			List<CollectPay> psList= mapCollectPay.get(ps);
			//计算出加绩总和
			double sumPay = psList.stream().mapToDouble(CollectPay::getAddPerformancePay).sum();
			CollectPay collect = new CollectPay();
			collect.setOrderTimeBegin(collectPay.getOrderTimeBegin());
			collect.setOrderTimeEnd(collectPay.getOrderTimeEnd());
			collect.setUserName(psList.get(0).getUserName());
			collect.setAddPerformancePay(sumPay);
			list.add(collect);
		}
		return list;
	}
}
