package com.bluewhite.finance.wage.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.system.user.entity.User;

/**
 * 电商在售商品
 * @author zhangliang
 *
 */
@Entity
@Table(name = " fin_wage")
public class Wage  extends BaseEntity<Long>{
	
	/**
	 * 员工id
	 * 
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
	 * 工资所属月份
	 */
	@Column(name = "time")
	private Date time;

	/**
	 * 工资金额
	 */
	@Column(name = "wage")
	private Double wage;
	

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

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Double getWage() {
		return wage;
	}

	public void setWage(Double wage) {
		this.wage = wage;
	}
	
	

}
