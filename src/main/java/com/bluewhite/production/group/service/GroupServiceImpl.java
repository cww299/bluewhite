package com.bluewhite.production.group.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

}
