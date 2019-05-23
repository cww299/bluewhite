package com.bluewhite.production.finance.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.production.bacth.entity.Bacth;
import com.bluewhite.production.task.entity.Task;
import com.bluewhite.system.user.entity.User;

/**
 * 生产控制部  B工资实体
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_payb" ,indexes = {	@Index(columnList = "type"),
										@Index(columnList = "user_id"),
										@Index(columnList = "task_id"),
										@Index(columnList = "allot_time")})
public class PayB extends BaseEntity<Long>{
	
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
	
	
	/**
	 * 任务工资数额
	 */
	@Column(name = "pay_number")
	private Double payNumber;
	
	/**
	 * 加绩工资数额
	 */
	@Column(name = "performance_pay_number")
	private Double performancePayNumber;
	
	/**
	 * 是否工序加价选择(任务加绩选项)
	 */
	@Column(name = "performance")
	private String performance;
	
	/**
	 * 要添加的分价值（任务加绩基础比值）
	 */
	@Column(name = "performance_number")
	private Double performanceNumber;
	
	/**
	 * 任务id
	 */
	@Column(name = "task_id")
	private Long taskId;
	
	/**
	 * 任务
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "task_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Task task;
	
	/**
	 * 批次id(冗余字段，为查询显示方便)
	 */
	@Column(name = "bacth_id")
	private Long bacthId;
	
	/**
	 * 批次号(冗余字段，为查询显示方便)
	 */
	@Column(name = "bacth")
	private String bacth;
	
	/**
	 * 产品id(冗余字段，为查询显示方便)
	 */
	@Column(name = "product_id")
	private Long productId;
	
	/**
	 * 产品名称(冗余字段，为查询显示方便)
	 */
	@Column(name = "product_name")
	private String productName;
	
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
	 * 是否是返工工资标识符（0=不是，1=是）
	 */
	@Column(name = "flag")
	private Integer flag = 0;
	
	
	/**
	 * 查询字段 
	 */
	@Column(name = "group_id")
	private Long groupId;
	
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
	

	
	
	
	


	public String getPerformance() {
		return performance;
	}

	public void setPerformance(String performance) {
		this.performance = performance;
	}

	public Double getPerformanceNumber() {
		return performanceNumber;
	}

	public void setPerformanceNumber(Double performanceNumber) {
		this.performanceNumber = performanceNumber;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Double getPerformancePayNumber() {
		return performancePayNumber;
	}

	public void setPerformancePayNumber(Double performancePayNumber) {
		this.performancePayNumber = performancePayNumber;
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

	public Date getAllotTime() {
		return allotTime;
	}

	public void setAllotTime(Date allotTime) {
		this.allotTime = allotTime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Long getBacthId() {
		return bacthId;
	}

	public void setBacthId(Long bacthId) {
		this.bacthId = bacthId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
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

	public String getBacth() {
		return bacth;
	}

	public void setBacth(String bacth) {
		this.bacth = bacth;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

//	public User getUser() {
//		return user;
//	}
//
//	public void setUser(User user) {
//		this.user = user;
//	}
	
	
	
}
