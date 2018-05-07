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
	private String username;
	/**
	 * 登录用户真实名
	 */
	private String realname;
	/**
	 * 性别
	 */
	private Integer sex;
	/*个人介绍*/
	private String note;
	/**
	 * 登录用户角色集合
	 */
	private Set<String> roles;
	/**
	 * 管理员显示区域，其它显示部门名
	 */
	private String name;
	@JSONField(serialize = false)
	/**
	 * 登录用户角色标志符集合
	 */
	private Set<String> rolesIdenti;// 登录用户的角色标识符
	/**
	 * 是否是管理员
	 */
	private Boolean admin;
	/**
	 * 区划id
	 */
	private Long areaId;
	/**
	 * 区划名称
	 */
	private String areaName;
	/**
	 * 区划编码
	 */
	private String areaCode;
	/**
	 * 联系电话
	 */
	private String mobileTelephone;
	/**
	 * 学校id
	 */
	private Long schoolId;
	/**
	 * 学校名称
	 */
	private String schoolName;
	/**
	 * 用户openid
	 */
	private String openId;
	/**
	 * 用户token
	 */
	private String userToken;
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public String getMobileTelephone() {
		return mobileTelephone;
	}

	public void setMobileTelephone(String mobileTelephone) {
		this.mobileTelephone = mobileTelephone;
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

	public Long getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Long schoolId) {
		this.schoolId = schoolId;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public String getUserToken() {
		return userToken;
	}

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
}
