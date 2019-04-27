package com.bluewhite.system.user.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluewhite.common.ClearCascadeJSON;
import com.bluewhite.common.entity.CommonResponse;
import com.bluewhite.common.entity.ErrorCode;
import com.bluewhite.common.entity.PageParameter;
import com.bluewhite.common.entity.PageResult;
import com.bluewhite.system.user.entity.Role;
import com.bluewhite.system.user.entity.RoleMenuPermission;
import com.bluewhite.system.user.service.RoleService;

@Controller
public class RoleAction {
	
	
	@Autowired
	private RoleService roleService;

	private ClearCascadeJSON clearCascadeJSON;

	{
		clearCascadeJSON = ClearCascadeJSON.get()
				.addFilterTerm(Role.class, "users","resourcePermissions")
				.addFilterTerm(RoleMenuPermission.class, "role");
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
	 * 根据id查询一个角色
	 * 
	 * @param id 角色id
	 * @return cr
	 */
	@RequestMapping(value = "/roles/{id}", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse getRole(@PathVariable("id") long id) {
		Role role = roleService.findOne(id);
		CommonResponse cr = new CommonResponse(clearCascadeJSON.format(role)
				.toJSON());
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
		cr.setData(roleService.save(role));
		return cr;
	}

	/**
	 * 角色名是否存在
	 * @param request 请求
	 * @param role 角色实体类
	 * @return cr
	 */
	@RequestMapping(value = "/roles/exists", method = RequestMethod.GET)
	@ResponseBody
	public CommonResponse checkRole(HttpServletRequest request, Role role) {
		return exists(role);
	}

	/**
	 * 判断角色的name是否存在相同的
	 * 
	 * @param role 角色实体类
	 * @return cr
	 */
	private CommonResponse exists(Role role) {
		CommonResponse cr = new CommonResponse();
		if (role.getName() != null) {
			if (roleService.findByName(role.getName()) == null) {
				cr.setData("角色名可以使用");
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
	public CommonResponse changeRole(HttpServletRequest request, Role role) {
		CommonResponse cr = new CommonResponse();
		
		
		
		
		
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
		roleService.delete(ids);
		cr.setMessage("删除成功");
		return cr;
	}

}
