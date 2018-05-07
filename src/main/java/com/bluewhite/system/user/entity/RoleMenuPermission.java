package com.bluewhite.system.user.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.bluewhite.base.BaseEntity;

/**
 * 角色权限菜单管理
 * @author zhangliang
 *
 */
@TypeDef(name = "SetToStringUserType", typeClass = CollectionToStringUserType.class, parameters = {
		@Parameter(name = "separator", value = ","),
		@Parameter(name = "collectionType", value = "java.util.HashSet"),
		@Parameter(name = "elementType", value = "java.lang.Long") })
@Entity
@Table(name = "sys_role_menu_permission")
public class RoleMenuPermission extends BaseEntity<Long> {

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@Fetch(FetchMode.SELECT)
	private Role role;

	/**
	 * 菜单id
	 */
	@Column(name = "menu_id")
	private Long menuId;

	/**
	 * 权限id列表 数据库通过字符串存储 逗号分隔
	 */
	@Column(name = "permission_ids")
	@Type(type = "SetToStringUserType")
	private Set<Long> permissionIds;

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public Set<Long> getPermissionIds() {
		return permissionIds;
	}

	public void setPermissionIds(Set<Long> permissionIds) {
		this.permissionIds = permissionIds;
	}

	@Override
	public String toString() {
		return "RoleResourcePermission{id=" + this.getId() + ",roleId="
				+ (role != null ? role.getId() : "null") + ", menuId=" + menuId
				+ ", permissionIds=" + permissionIds + '}';
	}
}
