package com.bluewhite.production.finance.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.system.user.entity.User;

/**
 * 生产控制部  杂工工资实体
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_farrago_task_pay")
public class FarragoTaskPay extends BaseEntity<Long> {
	
	/**
	 * 员工姓名
	 */
	@Column(name = "user_name")
    private String userName;
	
	/**
	 * 员工id
	 */
	@Column(name = "user_id")
    private Long userId;
	
//	/**
//	 * 员工
//	 */
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
//	private User user;
	
	/**
	 * 杂工工资数额
	 */
	@Column(name = "pay_number")
	private Double payNumber;
	
	/**
	 * 是否工序加价选择(杂工加绩选项)
	 */
	@Column(name = "performance")
	private String performance;
	
	/**
	 * 杂工加绩工资数额
	 */
	@Column(name = "performance_pay_number")
	private Double performancePayNumber;
	
	/**
	 * 任务id
	 */
	@Column(name = "task_id")
	private Long taskId;
	
	/**
	 * 任务名称
	 */
	@Column(name = "task_name")
	private String taskName;
	
	/**
	 * 工序所属部门类型 (1=一楼质检，2=一楼包装，3=二楼针工)
	 */
	@Column(name = "type")
	private Integer type;
	
	/**
	 * 任务分配时间（默认当前时间前一天）
	 */
	@Column(name = "allot_time")
	private Date allotTime;
	
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
	
	/**
	 * 查询字段 
	 */
	@Transient
	private Long groupId;
	
	
	
	
	public String getPerformance() {
		return performance;
	}
	public void setPerformance(String performance) {
		this.performance = performance;
	}
	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public Double getPerformancePayNumber() {
		return performancePayNumber;
	}
	public void setPerformancePayNumber(Double performancePayNumber) {
		this.performancePayNumber = performancePayNumber;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Double getPayNumber() {
		return payNumber;
	}
	public void setPayNumber(Double payNumber) {
		this.payNumber = payNumber;
	}
	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Date getAllotTime() {
		return allotTime;
	}
	public void setAllotTime(Date allotTime) {
		this.allotTime = allotTime;
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
//	public User getUser() {
//		return user;
//	}
//	public void setUser(User user) {
//		this.user = user;
//	}
	
	
	
	
	

}
