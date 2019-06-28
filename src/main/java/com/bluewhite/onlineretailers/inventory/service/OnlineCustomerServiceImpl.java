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
import com.bluewhite.common.utils.StringUtil;
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
        	//按名称过滤
        	if (!StringUtils.isEmpty(param.getName())) {
				predicate.add(cb.like(root.get("name").as(String.class),"%" + StringUtil.specialStrKeyword(param.getName()) + "%"));
			}
        	//按真实名称过滤
        	if (!StringUtils.isEmpty(param.getBuyerName())) {
				predicate.add(cb.like(root.get("buyerName").as(String.class),"%" + StringUtil.specialStrKeyword(param.getBuyerName()) + "%"));
			}
        	//按手机号过滤
        	if (!StringUtils.isEmpty(param.getPhone())) {
				predicate.add(cb.like(root.get("phone").as(String.class),"%" + StringUtil.specialStrKeyword(param.getPhone()) + "%"));
			}
        	//按类型过滤
        	if (param.getType()!= null) {
				predicate.add(cb.equal(root.get("type").as(Integer.class),param.getType()));
			}
        	//按等级过滤
        	if (param.getGrade()!= null) {
				predicate.add(cb.equal(root.get("grade").as(Integer.class),param.getGrade()));
			}
        	//按经手人过滤
        	if (param.getUserId() != null) {
				predicate.add(cb.equal(root.get("userId").as(Long.class),param.getUserId()));
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
