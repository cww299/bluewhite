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
 * 财务 杂项支出
 * 
 * @author zhangliang
 *
 */
@Entity
@Table(name = "ledger_mixed")
public class Mixed extends BaseEntity<Long> {


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
	 * 来往日期
	 */
	@Column(name = "mix_time")
	private Date mixtTime;

	/**
	 * 往來明细
	 */
	@Column(name = "mix_detailed")
	private String mixDetailed;

	/**
	 * 来往金额
	 */
	@Column(name = "mix_price")
	private Double mixPrice;
	
	/**
	 * 客户name
	 * 
	 */
	@Transient
	private String customerName;
	
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

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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
	
	public Date getMixtTime() {
		return mixtTime;
	}

	public void setMixtTime(Date mixtTime) {
		this.mixtTime = mixtTime;
	}

	public String getMixDetailed() {
		return mixDetailed;
	}

	public void setMixDetailed(String mixDetailed) {
		this.mixDetailed = mixDetailed;
	}

	public Double getMixPrice() {
		return mixPrice;
	}

	public void setMixPrice(Double mixPrice) {
		this.mixPrice = mixPrice;
	}

}
