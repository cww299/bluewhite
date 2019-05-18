package com.bluewhite.personnel.attendance.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.system.user.entity.User;
/**
 * 住宿
 * @author zhangliang
 *
 */
@Entity
@Table(name = "person_hostel")
public class Hostel  extends BaseEntity<Long>{
	/**
	 * 宿舍名
	 */
	@Column(name = "name")
	private String name;
	
	/**
	 * 分组人员
	 */
	@OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
	@JoinColumn(name = "hostel_id")
	private Set<User> users = new HashSet<User>();

	/**
	 * 结束json数据格式
	 */
	@Transient
	private String jsonName;
	
	
	public String getJsonName() {
		return jsonName;
	}

	public void setJsonName(String jsonName) {
		this.jsonName = jsonName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}
	
	

}
