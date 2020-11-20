package com.bluewhite.production.work.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;

/**
 * 任务交付计划实体
 * @author cww299
 * @date 2020/11/19
 */
@Entity
@Table(name = "pro_tasks_deliver")
public class TaskDeliver extends BaseEntity<Long> {

	/**
	 * 交付日期
	 */
	@Column(name = "deliver_date", nullable = false)
	private Date deliverDate;
	
	/**
	 * 任务id 
	 */
	@Column(name = "task_id")
	private Long taskId;
	
	/**
	 * 产品名称 含工序  （产品名 -- 工序名）
	 */
	@Column(name = "product_name")
	private String productName;
	
	/**
	 * 任务编号
	 */
	@Column(name = "task_number")
	private String taskNumber;
	
	/**
	 * 交付数量
	 */
	@Column(name = "number")
	private Integer number = 0;
	
	/**
	 * 备注
	 */
	@Column(name = "remark")
	private String remark;
	
	/**
	 * 查询字段
	 */
	@Transient
	private Date orderTimeBegin;
	@Transient
	private Date orderTimeEnd;
	public Date getDeliverDate() {
		return deliverDate;
	}
	public void setDeliverDate(Date deliverDate) {
		this.deliverDate = deliverDate;
	}
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getTaskNumber() {
		return taskNumber;
	}
	public void setTaskNumber(String taskNumber) {
		this.taskNumber = taskNumber;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
}
