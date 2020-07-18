package com.bluewhite.ledger.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.system.user.entity.User;

/**
 * 订单（下单合同）子单，多个子单对应一个主单，主要是为了记录多客户和多下单人
 * @author zhangliang
 *
 */
@Entity
@Table(name = "ledger_order_child")
public class OrderChild extends BaseEntity<Long> {
	
	/**
	 * 客户id
	 * 
	 */
	@Column(name = "order_id")
	private Long orderId;

	/**
	 * 客户
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Order order;

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
	 * 下单人id
	 * 
	 */
	@Column(name = "user_id")
	private Long userId;

	/**
	 * 下单人
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
	private User user;

	/**
	 * 子单数量
	 * 
	 */
	@Column(name = "child_number")
	private Integer childNumber;
	
	/**
	 * 子单放数数量
	 * 
	 */
	@Column(name = "child_put_number")
	private Integer childPutNumber;

	/**
	 * 子单备注
	 * 
	 */
	@Column(name = "child_remark")
	private String childRemark;

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

	public Integer getChildNumber() {
		return childNumber;
	}

	public void setChildNumber(Integer childNumber) {
		this.childNumber = childNumber;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public String getChildRemark() {
		return childRemark;
	}

	public void setChildRemark(String childRemark) {
		this.childRemark = childRemark;
	}

	public Integer getChildPutNumber() {
		return childPutNumber;
	}

	public void setChildPutNumber(Integer childPutNumber) {
		this.childPutNumber = childPutNumber;
	}
	
}
