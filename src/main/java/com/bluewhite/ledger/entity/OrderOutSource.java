package com.bluewhite.ledger.entity;

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
 * 生产计划部 外发单
 * 
 * @author zhangliang
 *
 */
@Entity
@Table(name = "ledger_order_outsource")
public class OrderOutSource extends BaseEntity<Long> {

	/**
	 * 工艺单内容填充，用于打印开单 1.填充样棉花
	 */
	@Column(name = "fill")
	private String fill;

	/**
	 * 工艺单内容填充，用于打印开单 1.填充样棉花备注
	 */

	@Column(name = "fill_remark")
	private String fillRemark;

	/**
	 * 任务工序
	 */
	@Column(name = "process")
	private String process;

	/**
	 * 任务数量
	 */
	@Column(name = "process_number")
	private Integer processNumber;

	/**
	 * 克重
	 */
	@Column(name = "gram_weight")
	private String gramWeight;

	/**
	 * 备注
	 */

	@Column(name = "remark")
	private String remark;

	/**
	 * 加工点id
	 * 
	 */
	@Column(name = "customer_id")
	private Long customerId;

	/**
	 * 加工点
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Customer customer;

	/**
	 * 跟单人id
	 * 
	 */
	@Column(name = "user_id")
	private Long userId;

	/**
	 * 跟单人
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
	private User user;

	/**
	 * 订单id
	 * 
	 */
	@Column(name = "order_id")
	private Long orderId;

	/**
	 * 订单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Order order;

	/**
	 * 开单时间
	 */
	@Column(name = "open_order_time")
	private Date openOrderTime;

	public String getFill() {
		return fill;
	}

	public void setFill(String fill) {
		this.fill = fill;
	}

	public String getFillRemark() {
		return fillRemark;
	}

	public void setFillRemark(String fillRemark) {
		this.fillRemark = fillRemark;
	}

	public String getGramWeight() {
		return gramWeight;
	}

	public void setGramWeight(String gramWeight) {
		this.gramWeight = gramWeight;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Integer getProcessNumber() {
		return processNumber;
	}

	public void setProcessNumber(Integer processNumber) {
		this.processNumber = processNumber;
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

	public Date getOpenOrderTime() {
		return openOrderTime;
	}

	public void setOpenOrderTime(Date openOrderTime) {
		this.openOrderTime = openOrderTime;
	}

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

}
