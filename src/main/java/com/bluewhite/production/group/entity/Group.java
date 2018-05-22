package com.bluewhite.production.group.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.system.user.entity.User;
/**
 * 分组表，用于记录生产部分组
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_group")
public class Group  extends BaseEntity<Long>{
	/**
	 * 组名
	 */
	@Column(name = "name")
	private String name;
	
	/**
	 * 到岗小时预计收入
	 */
	@Column(name = "price")
	private Double price;
	
	/**
	 * 分组所属部门类型 (1=一楼质检，2=一楼包装，3=二楼针工)
	 */
	private Integer type;
	/**
	 * 分组人员
	 */
	@OneToMany(mappedBy = "group")
	private Set<User> users = new HashSet<User>();
	
	
	
	

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	
	
	
	

}
