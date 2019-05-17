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
import com.bluewhite.onlineretailers.inventory.dao.CommodityDao;
import com.bluewhite.onlineretailers.inventory.entity.Commodity;
import com.bluewhite.onlineretailers.inventory.entity.OnlineOrder;
@Service
public class CommodityServiceImpl  extends BaseServiceImpl<Commodity, Long> implements  CommodityService{
	
	@Autowired
	private CommodityDao dao;

	@Override
	public PageResult<Commodity> findPage(Commodity param, PageParameter page) {
		 Page<Commodity> pages = dao.findAll((root,query,cb) -> {
	        	List<Predicate> predicate = new ArrayList<>();
	        	//按id过滤
	        	if (param.getId() != null) {
					predicate.add(cb.equal(root.get("id").as(Long.class),param.getId()));
				}
	        	
	        	//按编号过滤
	        	if (!StringUtils.isEmpty(param.getNumber())) {
					predicate.add(cb.equal(root.get("number").as(String.class),param.getNumber()));
				}
	        	
	        	//按产品名称过滤
	        	if (!StringUtils.isEmpty(param.getName())) {
					predicate.add(cb.like(root.get("name").as(String.class),"%"+StringUtil.specialStrKeyword(param.getName())+"%"));
				}
	        	
				Predicate[] pre = new Predicate[predicate.size()];
				query.where(predicate.toArray(pre));
	        	return null;
	        }, page);

	        PageResult<Commodity> result = new PageResult<>(pages,page);
	        return result;
	    }

	@Override
	public int deleteCommodity(String ids) {
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
