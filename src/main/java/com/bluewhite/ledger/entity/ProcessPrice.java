package com.bluewhite.ledger.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.basedata.entity.BaseData;

/**
 * 加工单工序对应的价格
 * 
 * @author zhangliang
 *
 */
@Entity
@Table(name = "ledger_process_price")
public class ProcessPrice extends BaseEntity<Long> {

	/**
	 * 工序价格
	 * 
	 */
	@Column(name = "price")
	private Double price;

	/**
	 * 工序任务id
	 * 
	 */
	@Column(name = "process_task_id")
	private Long processTaskId;

	/**
	 * 工序任务
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "process_task_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseData processTask;

	/**
	 * 加工单id
	 * 
	 */
	@Column(name = "order_outSource_id")
	private Long orderOutSourceId;

	/**
	 * 加工单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_outSource_id", referencedColumnName = "id", insertable = false, updatable = false)
	private OrderOutSource orderOutSource;

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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long getProcessTaskId() {
		return processTaskId;
	}

	public void setProcessTaskId(Long processTaskId) {
		this.processTaskId = processTaskId;
	}

	public BaseData getProcessTask() {
		return processTask;
	}

	public void setProcessTask(BaseData processTask) {
		this.processTask = processTask;
	}

	public Long getOrderOutSourceId() {
		return orderOutSourceId;
	}

	public void setOrderOutSourceId(Long orderOutSourceId) {
		this.orderOutSourceId = orderOutSourceId;
	}

	public OrderOutSource getOrderOutSource() {
		return orderOutSource;
	}

	public void setOrderOutSource(OrderOutSource orderOutSource) {
		this.orderOutSource = orderOutSource;
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
	
	
	

}
