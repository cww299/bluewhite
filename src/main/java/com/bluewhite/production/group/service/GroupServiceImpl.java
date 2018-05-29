package com.bluewhite.production.group.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.production.group.dao.GroupDao;
import com.bluewhite.production.group.entity.Group;

@Service
public class GroupServiceImpl extends BaseServiceImpl<Group, Long> implements GroupService{
	@Autowired
	private GroupDao dao;
	
	@Override
	public List<Group> findByType(Integer type) {
		return dao.findByType(type);
	}

	@Override
	public List<Group> findList(Group param) {
			List<Group> pages = dao.findAll((root,query,cb) -> {
	        	List<Predicate> predicate = new ArrayList<>();
	        	//按id过滤
	        	if (param.getId() != null) {
					predicate.add(cb.equal(root.get("id").as(Long.class),param.getId()));
				}
	        	//按类型
	        	if(!StringUtils.isEmpty(param.getType())){
	        		predicate.add(cb.equal(root.get("type").as(Integer.class), param.getType()));
	        	}
				Predicate[] pre = new Predicate[predicate.size()];
				query.where(predicate.toArray(pre));
	        	return null;
	        });
	        return pages;
	}

}
