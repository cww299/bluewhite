package com.bluewhite.system.user.service;



import java.util.List;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.system.user.entity.Menu;

@Service
public interface MenuService extends BaseCRUDService<Menu,Long>{

	/**
	 * 通过用户名查找
	 * @param username
	 * @return list
	 */
	public List<Menu> findHasPermissionMenusByUsername(String username);
	
	
	/**
	 * 通过用户名查找 -new 加入权限及时间判断
	 * @param username
	 * @return list
	 */
	public List<Menu> findHasPermissionMenusByUsernameNew(String username);
}
