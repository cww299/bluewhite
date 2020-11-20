package com.bluewhite.production.work.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;

/**
 * 任务进度实体
 * @author cww299
 * @date 2020/11/17
 */
@Entity
@Table(name = "pro_tasks_process")
public class TaskProcess extends BaseEntity<Long> {

	/**
	 * 任务id
	 */
	@Column(name = "task_id")
	private Long taskId;
	
	/**
	 * 任务分配id
	 */
	@Column(name = "task_allocation_id")
	private Long taskAllocationId;
	
	/**
	 * 进度类型
	 * 0.交付      此进度类型为任务实体独有
	 * 1.分配 2.完成 3.退回    此进度类型为任务实体与任务分配实体共有
	 * 4.开始 5.暂停 6.结束    此进度类型为任务分配实体独有
	 */
	@Column(name = "type")
	private Integer type;
	
	/**
	 * 人员
	 */
	@Column(name = "user_names")
	private String userNames;
	
	/**
	 * 数量
	 */
	@Column(name = "number")
	private Integer number;
	
	/**
	 * 耗时 /分钟
	 */
	@Column(name = "time_min")
	private Integer timeMin = 0;
	
	/**
	 * 备注
	 */
	@Column(name = "remark")
	private String remark;
	
	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Long getTaskAllocationId() {
		return taskAllocationId;
	}

	public void setTaskAllocationId(Long taskAllocationId) {
		this.taskAllocationId = taskAllocationId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getTimeMin() {
		return timeMin;
	}

	public void setTimeMin(Integer timeMin) {
		this.timeMin = timeMin;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUserNames() {
		return userNames;
	}

	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}

}
