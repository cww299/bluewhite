package com.bluewhite.system.user.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.alibaba.fastjson.annotation.JSONField;
import com.bluewhite.base.BaseEntity;

/**
 * 用户实体
 * @author zhangliang
 *
 */
/**
 * @author LB-BY06
 *
 */
@Entity
@Table(name = "sys_user")
// @Inheritance 的 strategy 属性是指定继承关系的生成策略，JOINED
// 是将父类、子类分别存放在不同的表中，并且建立相应的外键，以确定相互之间的关系。
@Inheritance(strategy = InheritanceType.JOINED)
public class User extends BaseEntity<Long> {
	/**
	 * 系统管理员标识
	 */
	@Column(name = "isAdmin", nullable = false)
	private Boolean isAdmin = false;

	/**
	 * 用户名
	 */
	@Column(name = "login_name", nullable = false)
	private String loginName;

	/**
	 * 手机
	 */
	@Column(name = "phone")
	private String phone;

	/**
	 * 用户密码,加密后
	 */
	@Column(name = "password")
	private String password;

	/**
	 * 真实名
	 */
	@Column(name = "realname")
	private String userName;

	/**
	 * 是否锁定
	 */
	@Column(name = "del_flag")
	private Integer delFlag;

	/**
	 * 邮箱
	 */
	@Column(name = "email")
	private String email;

	/**
	 * 性别
	 */
	@Column(name = "gender")
	private Integer gender;

	/**
	 * 生日
	 */
	@Column(name = "birth_date")
	private Date birthDate;

	/**
	 * idcard
	 */
	@Column(name = "id_card")
	private String idCard;

	/**
	 * 简介
	 */
	@Column(name = "remark")
	private String remark;

	/**
	 * 职位
	 */
	@Column(name = "position")
	private String position;

	/**
	 * 教育水平
	 */
	@Column(name = "edu_degree")
	private Integer eduDegree;

	/**
	 * 部门
	 */
	@Column(name = "orgName")
	private String orgName;

	/**
	 * 角色集合
	 */
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "sys_user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private Set<Role> roles = new HashSet<Role>();

	/**
	 * 权限
	 */
	@Transient
	@JSONField(serialize = false)
	private Set<String> permissions = new HashSet<>();

	/**
	 * 
	 */
	@Transient
	@JSONField(serialize = false)
	private Set<String> role = new HashSet<>();
	
	public Set<String> getRole() {
		return role;
	}

	public void setRole(Set<String> role) {
		this.role = role;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Integer getEduDegree() {
		return eduDegree;
	}

	public void setEduDegree(Integer eduDegree) {
		this.eduDegree = eduDegree;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<String> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<String> permissions) {
		this.permissions = permissions;
	}

}
