package com.bluewhite.system.user.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.system.user.entity.Menu;
import com.bluewhite.system.user.entity.Permission;



@Service
public class PermissionServiceImpl extends BaseServiceImpl<Permission, Long>
		implements PermissionService {

	@Autowired
	private MenuService menuService;

	public Set<String> getActualPermissionStr(Long menuId,
			Set<Long> permissionIds) {
		Menu menu = menuService.findOne(menuId);
		Set<String> result = new HashSet<String>();
		if (!CollectionUtils.isEmpty(permissionIds)) {
			for (Long permissionId : permissionIds) {
				Permission permission = findOne(permissionId);
				String actualPermissionStr = menu.getIdentity() + ":"
						+ permission.getPermission();
				result.add(actualPermissionStr);
			}
		}
		return result;
	}

	public Set<String> getAdminActualPermissionStr() {
		List<Menu> menus = menuService.findAll();
		Set<String> result = new HashSet<String>();
		if (!CollectionUtils.isEmpty(menus)) {
			for (Menu menu : menus) {
				String actualPermissionStr = menu.getIdentity() + ":*";
				result.add(actualPermissionStr);
			}
		}
		return result;
	}
}
