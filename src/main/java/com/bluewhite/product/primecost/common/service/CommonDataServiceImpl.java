package com.bluewhite.product.primecost.common.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.product.primecost.common.dao.CommonDataDao;
import com.bluewhite.product.primecost.common.entity.CommonData;

@Service
public class CommonDataServiceImpl extends BaseServiceImpl<CommonData, Long> implements CommonDataService{

	@Autowired
	private CommonDataDao dao;
	
	@Override
	public List<CommonData> findPages(CommonData param) {
		 List<CommonData> result = dao.findAll((root,query,cb) -> {
	        	List<Predicate> predicate = new ArrayList<>();
	        	//按id过滤
	        	if (param.getId() != null) {
					predicate.add(cb.equal(root.get("id").as(Long.class),param.getId()));
				}
	        	//按产品id过滤
	        	if (param.getType() != null) {
					predicate.add(cb.equal(root.get("type").as(Long.class),param.getType()));
				}
				Predicate[] pre = new Predicate[predicate.size()];
				query.where(predicate.toArray(pre));
	        	return null;
	        });
		return result;
	}

}
