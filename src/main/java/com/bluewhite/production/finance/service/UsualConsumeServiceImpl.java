package com.bluewhite.production.finance.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.production.finance.dao.UsualConsumeDao;
import com.bluewhite.production.finance.entity.UsualConsume;
@Service
public class UsualConsumeServiceImpl extends BaseServiceImpl<UsualConsume, Long> implements UsualConsumeService{
	@Autowired
	private UsualConsumeDao dao;

	@Override
	public UsualConsume usualConsume(UsualConsume usualConsume) {
		if(usualConsume.getType()==3){
			usualConsume.setEquipment((double) 290);
		}
		if(usualConsume.getType()==4){
			usualConsume.setEquipment((double) 6);
		}
		if(usualConsume.getType()==5){
			usualConsume.setMonthLogistics(3124.1);
		}else{
			usualConsume.setMonthLogistics(usualConsume.getPeopleLogistics()*usualConsume.getPeopleNumber());
		}
		usualConsume.setChummage(usualConsume.getMonthChummage()/30);
		if(usualConsume.getType()==1 || usualConsume.getType()==2 || usualConsume.getType()==5){
			usualConsume.setHydropower(usualConsume.getMonthHydropower()/30);
		}
		if(usualConsume.getType()==3 || usualConsume.getType()==4){
			usualConsume.setHydropower((usualConsume.getMonthHydropower()/30)+usualConsume.getEquipment());
		}
		usualConsume.setLogistics(usualConsume.getMonthLogistics()/30);
		return (UsualConsume)NumUtils.setzro(usualConsume);
	}

	@Override
	public PageResult<UsualConsume> findPages(UsualConsume param, PageParameter page) {
		 page.setSort(new Sort(Sort.Direction.DESC, "consumeDate"));
		 Page<UsualConsume> pages = dao.findAll((root,query,cb) -> {
	        	List<Predicate> predicate = new ArrayList<>();
	        	//按id过滤
	        	if (param.getId() != null) {
					predicate.add(cb.equal(root.get("id").as(Long.class),param.getId()));
				}
	        	//按类型
	        	if(!StringUtils.isEmpty(param.getType())){
	        		predicate.add(cb.equal(root.get("type").as(Integer.class), param.getType()));
	        	}
	            //按时间过滤
				if (!StringUtils.isEmpty(param.getOrderTimeBegin()) &&  !StringUtils.isEmpty(param.getOrderTimeEnd()) ) {
					predicate.add(cb.between(root.get("consumeDate").as(Date.class),
							param.getOrderTimeBegin(),
							param.getOrderTimeEnd()));
				}
				Predicate[] pre = new Predicate[predicate.size()];
				query.where(predicate.toArray(pre));
	        	return null;
	        }, page);
	        PageResult<UsualConsume> result = new PageResult<UsualConsume>(pages,page);
	        return result;
	    }

	@Override
	public List<UsualConsume> findByTypeAndConsumeDateBetween(Integer type, Date startTime, Date endTime) {
		return dao.findByTypeAndConsumeDateBetween(type, startTime, endTime);
	}

}
