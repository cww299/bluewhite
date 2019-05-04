package com.bluewhite.system.user.action;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.system.user.dao.RoleMenuPermissionDao;
import com.bluewhite.system.user.entity.Menu;
import com.bluewhite.system.user.entity.Permission;
import com.bluewhite.system.user.entity.Role;
import com.bluewhite.system.user.entity.RoleMenuPermission;
import com.bluewhite.system.user.service.MenuService;
import com.bluewhite.system.user.service.PermissionService;
import com.bluewhite.system.user.service.RoleService;

@Controller
public class RoleAction {
	
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private RoleMenuPermissionDao roleMenuPermissionDao;
	
	@Autowired
	private MenuService menuService;
	
	@Autowired
	private PermissionService permissionService;

	private ClearCascadeJSON clearCascadeJSON;

	{
		clearCascadeJSON = ClearCascadeJSON.get()
				.addFilterTerm(Role.class,"users","resourcePermissions")
				.addFilterTerm(RoleMenuPermission.class, "id","role");
	}

	/**
	 * 查询全部的角色 
	 * @param request 请求
	 * @param role 角色
	 * @return cr
	 */
	@RequestMapping(value = "/roles", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getAllRoles(HttpServletRequest request, Role role) {
		List<Role> roles = roleService.findAll();
		CommonResponse cr = new CommonResponse(clearCascadeJSON.format(roles)
				.toJSON());
		return cr;
	}

	/**
	 * 分页查询全部的角色
	 * @param request 请求
	 * @param pp 分页参数
	 * @param role 角色
	 * @return cr
	 */
	@RequestMapping(value = "/roles/page", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getRoles(HttpServletRequest request,
			PageParameter pp, Role role) {
		PageResult<Role> roles = roleService.getPage(pp, role);
		CommonResponse cr = new CommonResponse(clearCascadeJSON.format(roles)
				.toJSON());
		return cr;
	}

	/**
	 * 根据id查询一个角色所拥有的菜单权限
	 * 
	 * @param id 角色id
	 * @return cr
	 */
	@RequestMapping(value = "/getRole", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getRole(Long id) {
		CommonResponse cr = new CommonResponse();
		Role role = roleService.findOne(id);
		role.getResourcePermission().stream().forEach(rmp->{
			Menu menu = menuService.findOne(rmp.getMenuId());
			rmp.setMenuName(menu.getName());
			List<Permission> listPermission = new ArrayList<>();
			rmp.getPermissionIds().stream().forEach(pi->{
				Permission permission = permissionService.findOne(pi);
				listPermission.add(permission);
			});
			rmp.setPermissionNames(listPermission.stream().map(Permission::getName).collect(Collectors.joining(",")));
		});
		cr.setData(ClearCascadeJSON
				.get()
				.addRetainTerm(RoleMenuPermission.class, "id","menuName", "permissionNames","createdAt","updatedAt").format(role.getResourcePermission()).toJSON());
		cr.setMessage("查询成功");
		return cr;
	}

	/**
	 * 添加一个角色
	 * @param request 请求
	 * @param role 角色
	 * @return cr
	 */
	@RequestMapping(value = "/roles/add", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse addRole(HttpServletRequest request, Role role) {
		CommonResponse cr = new CommonResponse();
		if (role.getName() != null) {
			if (roleService.findByName(role.getName()) == null) {
				cr.setData(roleService.save(role));
			} else {
				cr.setCode(ErrorCode.SYSTEM_USER_NAME_REPEAT.getCode());
				cr.setData("角色名已存在");
			}
		} else {
			cr.setData("没有传递必要的参数name");
		}
		return cr;
	}



	
	
	/**
	 * 角色新增(菜单-权限)
	 * @param request 请求
	 * @param role 角色实体类
	 * @return cr
	 */
	@RequestMapping(value = "/roles/changeRole", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse changeRole(HttpServletRequest request, Long id , Long roleId, String permissions ) {
		CommonResponse cr = new CommonResponse();
		Role role = roleService.findOne(roleId);
		if(!StringUtils.isEmpty(permissions)){
			JSONArray jsonArray = JSON.parseArray(permissions);
			for (int i = 0; i < jsonArray.size(); i++) {
				HashSet<Long> permissionIdsLong = new HashSet<>();
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				Long menuId = Long.valueOf(jsonObject.getString("menuId"));
				String permissionIds = jsonObject.getString("permissionIds");
				RoleMenuPermission roleMenuPermission = new RoleMenuPermission();
				if(id!=null){
					roleMenuPermission = roleMenuPermissionDao.findOne(id);
				}
				String[] pers = permissionIds.split(",");
				for(String idString : pers){
					permissionIdsLong.add(Long.valueOf(idString));
				}
				roleMenuPermission.setRole(role);
				roleMenuPermission.setMenuId(menuId);
				roleMenuPermission.setPermissionIds(permissionIdsLong);
				roleMenuPermissionDao.save(roleMenuPermission);
			}
		}
		return cr;
	}
	
	
	/**
	 * 角色删除(菜单-权限)
	 * @param request 请求
	 * @param role 角色实体类
	 * @return cr
	 */
	@RequestMapping(value = "/roles/deleteRole", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse deleteRole(Long id) {
		CommonResponse cr = new CommonResponse();
		RoleMenuPermission roleMenuPermission = roleMenuPermissionDao.findOne(id);
		if(roleMenuPermission!=null){
			roleMenuPermission.setRole(null);
			roleMenuPermissionDao.delete(roleMenuPermission);
		}
		cr.setMessage("删除成功");
		return cr;
	}

	/**
	 * 修改角色信息
	 * @param request 请求
	 * @param role 角色实体类
	 * @return cr
	 */
	@RequestMapping(value = "/roles/update", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse updateRole(HttpServletRequest request, Role role) {
		CommonResponse cr = new CommonResponse();
		Role oldRole = roleService.findOne(role.getId());
		roleService.update(role,oldRole);
		cr.setMessage("修改成功");
		return cr;
	}

	/**
	 * 删除一个角色信息
	 * @param request 请求
	 * @param id roleid
	 * @return cr
	 */
	@RequestMapping(value = "/roles/delete", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse changeTeacher(HttpServletRequest request, String ids) {
		CommonResponse cr = new CommonResponse();
		int count = 0;
		String[] arrIds = ids.split(",");
		for (int i = 0; i < arrIds.length; i++) {
			Long id = Long.valueOf(arrIds[i]);
			Role role = roleService.findOne(id);
			List<RoleMenuPermission> resourcePermissionList = role.getResourcePermission();
			if(resourcePermissionList.size()>0){
				for(RoleMenuPermission rp: resourcePermissionList){
					rp.setRole(null);
					roleMenuPermissionDao.delete(rp);
				}
			}
			role.setUsers(null);
			role.setResourcePermission(null);
			roleService.delete(id);
			count++;
		}
		cr.setMessage("删除成功"+count+"个角色");
		return cr;
	}

}
