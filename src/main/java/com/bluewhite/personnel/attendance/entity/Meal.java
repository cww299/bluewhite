package com.bluewhite.personnel.attendance.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.system.user.entity.User;

/**
 * 签到记录 
 * 
 * @author qiyong
 *
 */
@Entity
@Table(name = "person_application_meal" )
public class Meal extends BaseEntity<Long> {
	
	/**
	 * 员工id
	 */
	@Column(name = "user_id")
	private Long userId;
	
	/**
	 * 员工
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
	private User user;
	
	/**
	 *(1.早餐 2.中餐 3.晚餐) 
	 */
	@Column(name = "mode")
	private Integer mode;
	
	/**
	 * 吃饭日期
	 */
	@Column(name = "tradeDays_time")
	private Date tradeDaysTime ;
	
	/**
	 * 餐价
	 */
	@Column(name = "price")
	private Double price ;
	
	/**
	 * 员工姓名
	 */
	@Transient
	private String userName;
	
	/**
	 * 查询字段
	 */
	@Transient
	private Date orderTimeBegin;
	/**
	 * 查询字段
	 */
	@Transient
	private Date orderTimeEnd;
	
	/**
	 * 查询字段（部门）
	 */
	@Transient
	private Long orgNameId;
	
	/**
	 * 查询字段（部门）
	 */
	@Transient
	private String orgName;

	

	public Integer getMode() {
		return mode;
	}

	public void setMode(Integer mode) {
		this.mode = mode;
	}

	public Date getTradeDaysTime() {
		return tradeDaysTime;
	}

	public void setTradeDaysTime(Date tradeDaysTime) {
		this.tradeDaysTime = tradeDaysTime;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}


	public Long getOrgNameId() {
		return orgNameId;
	}

	public void setOrgNameId(Long orgNameId) {
		this.orgNameId = orgNameId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getOrderTimeBegin() {
		return orderTimeBegin;
	}

	public void setOrderTimeBegin(Date orderTimeBegin) {
		this.orderTimeBegin = orderTimeBegin;
	}

	public Date getOrderTimeEnd() {
		return orderTimeEnd;
	}

	public void setOrderTimeEnd(Date orderTimeEnd) {
		this.orderTimeEnd = orderTimeEnd;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	

}
