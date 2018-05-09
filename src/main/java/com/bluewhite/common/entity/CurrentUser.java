package com.bluewhite.common.entity;

import java.io.Serializable;
import java.util.Set;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 当前登录用户信息
 * 
 * @author long.xin
 *
 */
public class CurrentUser {

	/**
	 * 登录用户ID
	 */
	private Long id;
	/**
	 * 登录用户名
	 */
	private String userName;
	/**
	 * 登录用户真实名
	 */
	private String realname;
	/**
	 * 性别
	 */
	private Integer sex;
	/**
	 * 登录用户角色集合
	 */
	private Set<String> roles;
	
	/**
	 * 部门名
	 */
	private String name;
	
	/**
	 * 登录用户角色标志符集合
	 */
	@JSONField(serialize = false)
	private Set<String> rolesIdenti;// 登录用户的角色标识符
	/**
	 * 是否是管理员
	 */
	private Boolean admin;

	/**
	 * 联系电话
	 */
	private String phone;
	
	/**
	 * 密码
	 */
	private String password;
	
	
	/**

	/**
	 * 用户权限集合
	 */
	@JSONField(serialize = false)
	private Set<String> permissions;// 登录用户的权限

	public Long getId() {
		return id;
	}

	public void setId(Serializable id) {
		this.id = (Long)id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}

	public Set<String> getRolesIdenti() {
		return rolesIdenti;
	}

	public void setRolesIdenti(Set<String> rolesIdenti) {
		this.rolesIdenti = rolesIdenti;
	}

	public Boolean getAdmin() {
		return admin;
	}

	public void setAdmin(Boolean admin) {
		this.admin = admin;
	}

	public Set<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<String> permissions) {
		this.permissions = permissions;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	

	
}
