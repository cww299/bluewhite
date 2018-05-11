package com.bluewhite.system.user.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.bluewhite.base.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 *  角色管理
 * @author zhangliang
 *
 */

@Entity
@Table(name = "sys_role")
public class Role extends BaseEntity<Long> {

	/**
	 * 名称
	 */
	@Column(name = "name")
	private String name;

	/**
	 * 名称英文
	 */
	@Column(name = "role")
	private String role;
	
	/**
	 * 角色类型
	 */
	@Column(name = "role_type")
	private String roleType;

	/**
	 *描述
	 */
	@Column(name = "description")
	private String description;

	/**
	 * 是否可用 
	 */
	@Column(name = "is_show")
	private Boolean isShow;

	/**
	 * 该角色所有用户集合
	 */
	@ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
	private Set<User> users = new HashSet<User>();

	/**
	 * 角色菜单权限类
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = RoleMenuPermission.class, mappedBy = "role", orphanRemoval = true)
	@Fetch(FetchMode.SELECT)
	@Basic(optional = true, fetch = FetchType.EAGER)
	private List<RoleMenuPermission> resourcePermission;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getIsShow() {
		return isShow;
	}

	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public String getRoleType() {
		return roleType;
	}

	public List<RoleMenuPermission> getResourcePermission() {
		return resourcePermission;
	}

	public void setResourcePermission(List<RoleMenuPermission> resourcePermission) {
		this.resourcePermission = resourcePermission;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	

}

