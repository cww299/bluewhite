package com.bluewhite.system.user.service;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.system.user.entity.Permission;


@Service
public interface PermissionService extends BaseCRUDService<Permission, Long> {

	/**
	 * 真正的权限字符串,schools:*或者schools:view
	 * 
	 * @param menuId menuid
	 * @param permissionIds 权限ids
	 * @return set
	 */
	public Set<String> getActualPermissionStr(Long menuId,
			Set<Long> permissionIds);

	/**
	 * admin用户的权限字符串，所有菜单权限
	 * @return set
	 */
	public Set<String> getAdminActualPermissionStr();

}
