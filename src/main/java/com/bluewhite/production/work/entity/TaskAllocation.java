package com.bluewhite.production.work.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;

/**
 * 任务分配实体
 * @author cww299
 * @date 2020/11/17
 */
@Entity
@Table(name = "pro_tasks_allocation")
public class TaskAllocation extends BaseEntity<Long> {
	
	/**
	 * 任务id
	 */
	@Column(name = "task_id")
	private Long taskId;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id", referencedColumnName = "id", insertable = false, updatable = false)
	private TaskWork task;
	
	
	/**
	 * 员工 ids , 隔开
	 */
	@Column(name = "user_ids")
	private String userIds;
	
	/**
	 * 员工姓名  / 隔开
	 */
	@Column(name = "user_names")
	private String userNames;
	
	/**
	 * 分配数量
	 */
	@Column(name = "number")
	private Integer number;
	
	/**
	 * 已完成数量
	 */
	@Column(name = "finish_number")
	private Integer finishNumber = 0;
	
	/**
	 * 退回数量
	 */
	@Column(name = "return_number")
	private Integer returnNumber = 0;
	
	/**
	 * 任务状态 0.进行中 1.暂停 2.结束
	 */
	@Column(name = "status")
	private Integer status;
	
	/**
	 * 当前剩余数量 = 分配数量 - 已完成数量 - 退回数量 number - finishNumber - returnNumber
	 */
	@Transient
	private Integer surplusNumber = 0;
	
	/**
	 * 搜索字段
	 */
	@Transient
	private String productName;
	@Transient
	private String processesName;
	@Transient
	private String taskNumber;

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public String getUserNames() {
		return userNames;
	}

	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getFinishNumber() {
		return finishNumber;
	}

	public void setFinishNumber(Integer finishNumber) {
		this.finishNumber = finishNumber;
	}

	public Integer getReturnNumber() {
		return returnNumber;
	}

	public void setReturnNumber(Integer returnNumber) {
		this.returnNumber = returnNumber;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSurplusNumber() {
		surplusNumber = number - finishNumber - returnNumber;
		return surplusNumber;
	}

	public void setSurplusNumber(Integer surplusNumber) {
		this.surplusNumber = surplusNumber;
	}

	public TaskWork getTask() {
		return task;
	}

	public void setTask(TaskWork task) {
		this.task = task;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProcessesName() {
		return processesName;
	}

	public void setProcessesName(String processesName) {
		this.processesName = processesName;
	}

	public String getTaskNumber() {
		return taskNumber;
	}

	public void setTaskNumber(String taskNumber) {
		this.taskNumber = taskNumber;
	}
	
}
