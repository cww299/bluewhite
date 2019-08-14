package com.bluewhite.system.user.service;



import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bluewhite.base.BaseCRUDService;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
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
	
	/**
	 * 分页查询
	 * @param page 分页参数
	 */
	public PageResult<Menu> getPage(PageParameter page, Menu menu);

	/**
	 * 查询所有菜单
	 * @return
	 */
	public List<Menu> getTreeMenuPage();

	/**
	 * 根据身份查询菜单
	 * @param identity
	 * @return
	 */
	public Optional<Menu> findByIdentity(String identity);

	/**
	 *  根据父id查询所有菜单
	 * @param id
	 * @return
	 */
	public  List<Menu> getTreeMenuParent(Long id);
}
