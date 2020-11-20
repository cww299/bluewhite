package com.bluewhite.production.work.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;

import cn.hutool.core.util.NumberUtil;

/**
 * 任务实体
 * @author cww299
 * @date 2020/11/17
 */
@Entity
@Table(name = "pro_tasks")
public class TaskWork extends BaseEntity<Long> {

	/**
	 * 任务编号
	 */
	@Column(name = "task_number")
	private String taskNumber;
	
	/**
	 * 任务类型 0.按产品 1.按工序
	 */
	@Column(name = "type")
	private Integer type;
	
	/**
	 * 产品id
	 */
	@Column(name = "product_id")
	private Long productId;
	
	/**
	 * 产品名称
	 */
	@Column(name = "product_name")
	private String productName;
	
	/**
	 * 工序id
	 */
	@Column(name = "processes_id")
	private Long processesId;
	
	/**
	 * 工序名称
	 */
	@Column(name = "processes_name")
	private String processesName;
	
	/**
	 * 单个预计耗时 /秒
	 */
	@Column(name = "time_second")
    private Double timeSecond = 0.0;
	
	/**
	 * 当前耗时 /分钟
	 */
	@Column(name = "curr_time_min")
    private Integer currTimeMin = 0;
	
	/**
	 * 任务数量
	 */
	@Column(name = "number")
	private Integer number = 0;
	
	/**
	 * 完成数量
	 */
	@Column(name = "finish_number")
	private Integer finishNumber = 0;
	
	/**
	 * 分配数量
	 */
	@Column(name = "allocation_number")
	private Integer allocationNumber = 0;
	
	/**
	 * 任务状态 0.未分配 1.进行中 2.完成
	 */
	@Column(name = "status")
	private Integer status;
	
	/**
	 * 备注
	 */
	@Column(name = "remark")
	private String remark;
	
	/**
	 * 预计耗时 /分钟  = 单个预计耗时（秒） * 总数量  / 60
	 */
	@Transient
	private Integer timeMin = 0;
	/**
	 * 当前剩余未分配数量 = 数量 - 已分配数量
	 */
	@Transient
	private Integer surplusNumber = 0;
	
	
	/**
	 * 查询字段
	 */
	@Transient
	private Date orderTimeBegin;
	@Transient
	private Date orderTimeEnd;
	
	/**
	 * 新增任务的工序id，名称，耗时集合。用于批量新增
	 */
	@Transient
	private String processesIds;
	@Transient
	private String processesNames;
	@Transient
	private String timeSeconds;
	
	public String getTaskNumber() {
		return taskNumber;
	}
	public void setTaskNumber(String taskNumber) {
		this.taskNumber = taskNumber;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Long getProcessesId() {
		return processesId;
	}
	public void setProcessesId(Long processesId) {
		this.processesId = processesId;
	}
	public String getProcessesName() {
		return processesName;
	}
	public void setProcessesName(String processesName) {
		this.processesName = processesName;
	}
	
	public Double getTimeSecond() {
		return timeSecond;
	}
	public void setTimeSecond(Double timeSecond) {
		this.timeSecond = timeSecond;
	}
	public Integer getTimeMin() {
		double allSecond = NumberUtil.mul(timeSecond, number).intValue();
		timeMin = (int) NumberUtil.div(allSecond, 60);
		return timeMin;
	}
	public void setTimeMin(Integer timeMin) {
		this.timeMin = timeMin;
	}
	public Integer getCurrTimeMin() {
		return currTimeMin;
	}
	public void setCurrTimeMin(Integer currTimeMin) {
		this.currTimeMin = currTimeMin;
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
	public Integer getAllocationNumber() {
		return allocationNumber;
	}
	public void setAllocationNumber(Integer allocationNumber) {
		this.allocationNumber = allocationNumber;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getSurplusNumber() {
		surplusNumber = number - allocationNumber; 
		return surplusNumber;
	}
	public void setSurplusNumber(Integer surplusNumber) {
		this.surplusNumber = surplusNumber;
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
	public String getProcessesIds() {
		return processesIds;
	}
	public void setProcessesIds(String processesIds) {
		this.processesIds = processesIds;
	}
	public String getProcessesNames() {
		return processesNames;
	}
	public void setProcessesNames(String processesNames) {
		this.processesNames = processesNames;
	}
	public String getTimeSeconds() {
		return timeSeconds;
	}
	public void setTimeSeconds(String timeSeconds) {
		this.timeSeconds = timeSeconds;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
