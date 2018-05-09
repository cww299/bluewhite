package com.bluewhite.system.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.bluewhite.base.BaseServiceImpl;
import com.bluewhite.common.SessionManager;
import com.bluewhite.common.entity.CurrentUser;
import com.bluewhite.system.user.dao.MenuDao;
import com.bluewhite.system.user.entity.Menu;
import com.bluewhite.system.user.entity.Role;
import com.bluewhite.system.user.entity.RoleMenuPermission;
import com.bluewhite.system.user.entity.User;

@Service
public class MenuServiceImpl extends BaseServiceImpl<Menu, Long> implements MenuService {

	@Autowired
	private MenuDao menuDao;

	@Autowired
	private UserService userService;

	/**
	 * 查找用户有权限访问的菜单
	 */
	@Override
	public List<Menu> findHasPermissionMenusByUsername(String username) {
		User user = userService.findByUserName(username);
		List<Menu> result = new ArrayList<Menu>();
		Set<Role> roles = user.getRoles();
		if (!CollectionUtils.isEmpty(roles)) {
			Set<Long> menuIds = new HashSet<>();
			for (Role role : roles) {
				List<RoleMenuPermission> roleMenuPermissions = role.getResourcePermission();
				if (!CollectionUtils.isEmpty(roleMenuPermissions)) {
					for (RoleMenuPermission roleMenuPermission : roleMenuPermissions) {
						menuIds.add(roleMenuPermission.getMenuId());
					}
				}
			}

			// 取到所有的菜单ID后，获取菜单数据
			if (!CollectionUtils.isEmpty(menuIds)) {
				result.addAll(menuDao.findByIdInAndIsShowOrderByOrderNo(menuIds, true));
			}
		}

		// 为分类建立键值对
		Map mapNodes = new HashMap(result.size());
		for (Menu treeNode : result) {
			mapNodes.put(treeNode.getId(), treeNode);
		}
		// 初始化多叉树信息，里面只保存顶级分类信息
		List<Menu> topTree = new ArrayList<Menu>();// 多叉树
		for (Menu treeNode : result) {
			if (treeNode.getParentId() != null && treeNode.getParentId() == 0) {// 添加根节点（顶级分类）
				Menu rootNode = (Menu) mapNodes.get(treeNode.getId());
				topTree.add(rootNode);
			} // end if
			else {
				Menu parentNode = (Menu) mapNodes.get(treeNode.getParentId());
				if (parentNode != null) {
					if (parentNode.getChildren() == null) {
						parentNode.setChildren(new ArrayList<Menu>());
					}
					List<Menu> children = parentNode.getChildren();
					children.add(treeNode);
				}
			} // end else
		} // end for
		return topTree;
	}

	private boolean isAdminRole(Set<Role> roles) {
		if (CollectionUtils.isEmpty(roles)) {
			return false;
		}
		for (Role role : roles) {
			if ("管理员".equals(role.getName())) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<Menu> findHasPermissionMenusByUsernameNew(String username) {
		List<Menu> validMenus = findHasPermissionMenusByUsername(username);
		CurrentUser currentUser = SessionManager.getUserSession();
		Set<String> filterMenus = getFilterMenus(currentUser);
		return validMenus.stream().filter(m -> {
			boolean isContain = !filterMenus.contains(m.getIdentity());
			if (isContain && !CollectionUtils.isEmpty(m.getChildren())) {
				// 如果二级菜单需要过滤也来判断一把
				List<Menu> removeMenus = new ArrayList<>();
				m.getChildren().forEach(me -> {
					if (filterMenus.contains(me.getIdentity())) {
						removeMenus.add(me);
					}
				});
				m.getChildren().removeAll(removeMenus);
			}
			return isContain;
		}).collect(Collectors.toList());
	}

	/**
	 * 获取需要特殊滤掉的菜单
	 * 
	 * @param currentUser
	 * @return
	 */
	private Set<String> getFilterMenus(CurrentUser currentUser) {
		Set<String> result = new HashSet<>();
		if (!currentUser.getAdmin()) {
			// 非超管，过滤掉省馆菜单，包括省管理员也没有该菜单
			result.add("/users/province");
		}
		return result;
	}

}
