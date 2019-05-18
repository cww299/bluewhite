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
 * 报餐记录
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
	 *(1.早餐次数 2.中餐次数 3.晚餐次数) 
	 */
	@Transient
	private Integer modeOne;
	
	/**
	 *(2.中餐次数) 
	 */
	@Transient
	private Integer modeTwo;
	
	/**
	 *(3.晚餐次数) 
	 */
	@Transient
	private Integer modeThree;
	
	/**
	 * 汇总餐价
	 */
	@Transient
	private Double summaryPrice ;
	
	/**
	 * 时间传参
	 */
	@Transient
	private String time;
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
	 * 部门Id
	 */
	private Long orgNameId;
	
	/**
	 * 查询字段（部门）
	 */
	@Transient
	private String orgName;

	

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

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

	public Integer getModeOne() {
		return modeOne;
	}

	public void setModeOne(Integer modeOne) {
		this.modeOne = modeOne;
	}

	public Integer getModeTwo() {
		return modeTwo;
	}

	public void setModeTwo(Integer modeTwo) {
		this.modeTwo = modeTwo;
	}

	public Integer getModeThree() {
		return modeThree;
	}

	public void setModeThree(Integer modeThree) {
		this.modeThree = modeThree;
	}

	public Double getSummaryPrice() {
		return summaryPrice;
	}

	public void setSummaryPrice(Double summaryPrice) {
		this.summaryPrice = summaryPrice;
	}
	

}
