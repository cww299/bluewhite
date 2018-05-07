package com.bluewhite.system.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;

/**
 * 权限管理
 * @author zhangliang
 *
 */
@Entity
@Table(name = "sys_permission")
public class Permission extends BaseEntity<Long> {

	/**
	 * 前端显示名称
	 */
	@Column(name = "name")
	private String name;
	/**
	 * 系统中验证时使用的权限标识
	 */
	@Column(name = "permission")
	private String permission;

	/**
	 * 详细描述
	 */
	@Column(name = "description")
	private String description;

	/**
	 * 是否显示 也表示是否可用 为了统一 都使用这个
	 */
	@Column(name = "is_show")
	private Boolean show = Boolean.FALSE;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getShow() {
		return show;
	}

	public void setShow(Boolean show) {
		this.show = show;
	}

}
