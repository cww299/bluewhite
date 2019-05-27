package com.bluewhite.system.user.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.BeanCopyUtils;
import com.bluewhite.common.SessionManager;
import com.bluewhite.common.entity.CurrentUser;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.system.user.dao.RoleDao;
import com.bluewhite.system.user.entity.Role;



@Service
public class RoleServiceImpl extends BaseServiceImpl<Role, Long> implements
		RoleService {

	@Autowired
	private RoleDao dao;

	@Override
	public PageResult<Role> getPage(PageParameter page, Role role) {
		CurrentUser cu = SessionManager.getUserSession();
		if(!cu.getRole().contains("superAdmin")){
			role.setRoles(cu.getRole());
		}
		Page<Role> pageData = dao.findAll((root, query, cb) -> {
			List<Predicate> predicate = new ArrayList<>();
			if (role.getId() != null) {
				predicate.add(cb.equal(root.get("id").as(Long.class),
						role.getId()));
			}
			
			//角色,多个
			if (!StringUtils.isEmpty(role.getRoles())) {
				predicate.add(cb.and(root.get("role").as(String.class).in(role.getRoles())));
			}
			
			if (!StringUtils.isEmpty(role.getName())) {
				predicate.add(cb.like(root.get("name").as(String.class), "%"
						+ role.getName() + "%"));
			}
			Predicate[] pre = new Predicate[predicate.size()];
			query.where(predicate.toArray(pre));
			return null;
		}, page);
		PageResult<Role> result = new PageResult<>(pageData,page);
		return result;
	}

	@Override
	public Role findByName(String name) {
		return dao.findByName(name);
	}


}
