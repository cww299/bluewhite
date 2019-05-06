package com.bluewhite.onlineretailers.inventory.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;

/**
 * 电商客户
 * @author zhangliang
 *
 */
@Entity
@Table(name = "online_customer")
public class OnlineCustomer extends BaseEntity<Long>{
	
	/**
	 * 客户名称
	 * 
	 */
	@Column(name = "name")
	private String name;
	
	/**
	 * 收件人
	 * 
	 */
	@Column(name = "addressee")
	private String addressee;
	
	/**
	 * 客户等级
	 * 
	 */
	@Column(name = "grade")
	private Integer grade;
	
	
	/**
	 * 客户类型
	 * 
	 */
	@Column(name = "type")
	private Integer type;
	
	
	/**
	 * 收货地址
	 * 
	 */
	@Column(name = "address")
	private String address;
	
	/**
	 * 手机号
	 * 
	 */
	@Column(name = "phone")
	private String phone;
	
	

	

	public String getAddressee() {
		return addressee;
	}


	public void setAddressee(String addressee) {
		this.addressee = addressee;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Integer getGrade() {
		return grade;
	}


	public void setGrade(Integer grade) {
		this.grade = grade;
	}


	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}
	
	
	
	
	

}
