package com.bluewhite.system.user.service;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.system.user.entity.Role;


@Service
public interface RoleService extends BaseCRUDService<Role, Long> {

	/**
	 * 分页查询
	 * @param page 分页参数
	 * @param role 角色
	 * @return pageresult
	 */
	public PageResult<Role> getPage(PageParameter page, Role role);

	/**
	 * 通过角公名查询
	 * @param name 角色名
	 * @return role
	 */
	public Role findByName(String name);
	
	/**
	 * 清除权限缓存
	 */
	public void cleanRole();
	
	/**
	 * 清除指定用户权限缓存
	 */
	public void cleanRole(String username);
}
