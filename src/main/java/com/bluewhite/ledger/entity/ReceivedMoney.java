package com.bluewhite.ledger.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;

/**
 * 财务 收款
 * 
 * @author zhangliang
 *
 */
@Entity
@Table(name = "ledger_received_money")
public class ReceivedMoney extends BaseEntity<Long>{
	
	/**
	 * 客户id
	 * 
	 */
	@Column(name = "customer_id")
	private Long customerId;

	/**
	 * 客户
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Customer customer;
	
	/**
	 * 收款日期
	 */
	@Column(name = "received_money_date")
	private Date receivedMoneyDate;
	
	
	/**
	 * 收款金额
	 */
	@Column(name = "received_money")
	private Double receivedMoney;
	
	
	/**
	 * 收款备注
	 */
	@Column(name = "received_remark")
	private String receivedRemark;

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
	
	

	
	public String getReceivedRemark() {
		return receivedRemark;
	}


	public void setReceivedRemark(String receivedRemark) {
		this.receivedRemark = receivedRemark;
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


	public Long getCustomerId() {
		return customerId;
	}


	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}


	public Customer getCustomer() {
		return customer;
	}


	public void setCustomer(Customer customer) {
		this.customer = customer;
	}


	public Date getReceivedMoneyDate() {
		return receivedMoneyDate;
	}


	public void setReceivedMoneyDate(Date receivedMoneyDate) {
		this.receivedMoneyDate = receivedMoneyDate;
	}


	public Double getReceivedMoney() {
		return receivedMoney;
	}


	public void setReceivedMoney(Double receivedMoney) {
		this.receivedMoney = receivedMoney;
	}


}
