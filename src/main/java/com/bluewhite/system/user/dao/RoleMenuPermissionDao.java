package com.bluewhite.system.user.dao;

import com.bluewhite.base.BaseRepository;
import com.bluewhite.system.user.entity.RoleMenuPermission;
import java.lang.Long;
import java.util.List;

public interface RoleMenuPermissionDao extends BaseRepository<RoleMenuPermission, Long> {
	
	
	/**
	 * 根据菜单id查找
	 * @param menuid
	 * @return
	 */
	List<RoleMenuPermission> findByMenuId(Long menuid);

}
