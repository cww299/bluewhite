package com.bluewhite.common.entity;

import java.io.Serializable;
import java.util.Set;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
	 * 部门名
	 */
	private String name;
	
	/**
	 * 是否是管理员
	 */
	private Boolean isAdmin;

	/**
	 * 联系电话
	 */
	private String phone;
	
	/**
	 * 登录用户角色集合
	 */
	@JSONField(serialize = false)
	private Set<String> role;

	/**
	 * 用户权限集合
	 */
	@JSONField(serialize = false)
	private Set<String> permissions;

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

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public Set<String> getRole() {
		return role;
	}

	public void setRole(Set<String> role) {
		this.role = role;
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

	

	
}
