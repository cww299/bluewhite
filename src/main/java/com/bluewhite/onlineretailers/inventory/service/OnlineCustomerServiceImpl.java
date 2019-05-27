package com.bluewhite.onlineretailers.inventory.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DaoSupport;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.onlineretailers.inventory.dao.OnlineCustomerDao;
import com.bluewhite.onlineretailers.inventory.entity.OnlineCustomer;
import com.bluewhite.onlineretailers.inventory.entity.OnlineOrder;
import com.bluewhite.onlineretailers.inventory.entity.Procurement;
@Service
public class OnlineCustomerServiceImpl extends BaseServiceImpl<OnlineCustomer, Long> implements  OnlineCustomerService{
	@Autowired
	private OnlineCustomerDao dao;
	
	
	@Override
	public PageResult<OnlineCustomer> findPage(OnlineCustomer param, PageParameter page) {
		Page<OnlineCustomer> pages = dao.findAll((root,query,cb) -> {
        	List<Predicate> predicate = new ArrayList<>();
        	//按id过滤
        	if (param.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class),param.getId()));
			}
        	//按编号过滤
        	if (!StringUtils.isEmpty(param.getName())) {
				predicate.add(cb.equal(root.get("name").as(String.class),param.getName()));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
        	return null;
        }, page);

        PageResult<OnlineCustomer> result = new PageResult<>(pages,page);
        return result;
    }


	@Override
	public int deleteOnlineCustomer(String ids) {
		int count = 0;
		if(!StringUtils.isEmpty(ids)){
			String[] pers = ids.split(",");
			if(pers.length>0){
				for(String idString : pers){
					dao.delete(Long.valueOf(idString));
					count++;
				}
			}
		}
		return count;
	}

}
