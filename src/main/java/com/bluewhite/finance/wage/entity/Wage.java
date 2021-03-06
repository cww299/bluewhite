package com.bluewhite.finance.wage.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.system.user.entity.User;

/**
 * 工资
 * @author zhangliang
 *
 */
@Entity
@Table(name = "fin_wage")
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
	
	/**
	 * 类型
	 */
	@Column(name = "type")
	private Long type;

	/**
	 * 工资类型
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseData wages;
	
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

	

	public Long getType() {
		return type;
	}

	public void setType(Long type) {
		this.type = type;
	}

	public BaseData getWages() {
		return wages;
	}

	public void setWages(BaseData wages) {
		this.wages = wages;
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
