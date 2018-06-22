package com.bluewhite.production.group.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.utils.NumUtils;
import com.bluewhite.production.bacth.entity.Bacth;
import com.bluewhite.production.finance.entity.PayB;
import com.bluewhite.production.group.dao.GroupDao;
import com.bluewhite.production.group.entity.Group;
import com.bluewhite.production.productionutils.constant.ProTypeUtils;
import com.bluewhite.production.task.entity.Task;
import com.bluewhite.system.user.entity.User;

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
	        	if(param.getType()!=null){
	        		predicate.add(cb.equal(root.get("type").as(Integer.class), param.getType()));
	        	}
	        	//按工种类型
	        	if(param.getKindWorkId()!=null){
	        		predicate.add(cb.equal(root.get("kindWorkId").as(Long.class), param.getKindWorkId()));
	        	}
				Predicate[] pre = new Predicate[predicate.size()];
				query.where(predicate.toArray(pre));
	        	return null;
	        });
	        return pages;
	}

	@Override
	public void deleteGroup(String ids) {
		if (!StringUtils.isEmpty(ids)) {
			String[] idArr = ids.split(",");
			if (idArr.length>0) {
				for (int i = 0; i < idArr.length; i++) {
					Long id = Long.parseLong(idArr[i]);
					Group group = dao.findOne(id);
					for(User user : group.getUsers()){
						user.setGroupId(null);	
						user.setGroup(null);
						}
					group.setUsers(null);
					dao.delete(group);
				};
			}
		}
	}
}
