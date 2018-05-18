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
	 * 部门id
	 */
	private Long orgNameId ;
	
	/**
	 * 部门名
	 */
	private String orgName;
	
	/**
	 * 职位id
	 */
	private Long positionId ;
	
	/**
	 * 职位名
	 */
	private String position;
	
	/**
	 * 是否是管理员
	 */
	private Boolean isAdmin;

	/**
	 * 联系电话
	 */
	private String phone;
	
	/**
	 * 照片url
	 */
	private String pictureUrl;
	
	
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

	public Long getOrgNameId() {
		return orgNameId;
	}

	public void setOrgNameId(Long orgNameId) {
		this.orgNameId = orgNameId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Long getPositionId() {
		return positionId;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
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

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	
	

	

	
}
