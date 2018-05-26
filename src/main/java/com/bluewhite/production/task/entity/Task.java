package com.bluewhite.production.task.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.product.entity.Product;
import com.bluewhite.production.bacth.entity.Bacth;
import com.bluewhite.production.procedure.entity.Procedure;
import com.bluewhite.system.user.entity.User;
/**
 * 产品批次任务
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_task")
public class Task  extends BaseEntity<Long>{
	
	/**
	 * 批次id  
	 */
	@Column(name = "bacth_id")
	private Long bacthId;
	
	/**
	 * 批次号 (冗余字段，用于前台的展示)
	 */
	@Column(name = "bacth_number")
	private String bacthNumber;
	
	/**
	 * 批次   任务多对一批次
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bacth_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Bacth bacth;
	
	
	/**
	 * 领取任务人员ids(任务和员工多对多关系)
	 */
	@Column(name = "userIds")
	private  String userIds;
	
	/**
	 * 领取任务人员姓名集合
	 */
	@Column(name = "user_names")
	private  String userNames;
	
	
	/**
	 * 产品名称
	 */
	@Column(name = "product_name")
	private String productName;
	
	
	/**
	 * 领取任务人员ids
	 */
	@Transient
	private  String[] usersIds;
	
	/**
	 * 工序ids
	 */
	@Transient
	private  String[] procedureIds;
	
	/**
	 * 工序id
	 */
	@Column(name = "procedure_id")
	private Long procedureId;
	
	/**
	 * 工序
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "procedure_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Procedure procedure;
	
	
	/**
	 * 工序名称
	 */
	@Column(name = "procedure_name")
	private String procedureName;
	
    /**
     * 任务数量
     */
	@Column(name = "number")
    private Integer number;
 
    /**
     * 任务状态(0=开始 ，1=结束)
     */
	@Column(name = "status")
    private Integer  status;
    
	
    /**
     * 预计完成时间
     */
	@Column(name = "expect_time")
    private Double expectTime;
	
    /**
     * 实际任务完成时间
     */
	@Column(name = "task_time")
    private Double taskTime;
	
	/**
	 * 任务价值(预计成本费用)
	 */
	@Column(name = "expect_task_price")
	private Double expectTaskPrice;
	
	
	/**
	 * 任务价值(实际成本费用)
	 */
	@Column(name = "task_price")
	private Double taskPrice;
	
	/**
	 * b工资净值
	 */
	@Column(name = "pay_b")
	private Double payB;
	
	/**
	 * 分配时间（默认当前时间前一天）
	 */
	@Column(name = "allot_time")
	private Date allotTime;
	
	/**
	 * 工序所属部门类型 (1=一楼质检，2=一楼包装，3=二楼针工)
	 */
	@Column(name = "type")
	private Integer type;
	
    /**
     * 备注
     */
	@Column(name = "remarks")
    private String remarks;
	
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
	 * 查询字段 ,工序类型id
	 */
	@Transient
	private Long procedureTypeId;
	
	
	
	public Long getProcedureTypeId() {
		return procedureTypeId;
	}
	public void setProcedureTypeId(Long procedureTypeId) {
		this.procedureTypeId = procedureTypeId;
	}
	public Procedure getProcedure() {
		return procedure;
	}
	public void setProcedure(Procedure procedure) {
		this.procedure = procedure;
	}
	public Date getAllotTime() {
		return allotTime;
	}
	public void setAllotTime(Date allotTime) {
		this.allotTime = allotTime;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getUserNames() {
		return userNames;
	}
	public void setUserNames(String userNames) {
		this.userNames = userNames;
	}
	public String[] getProcedureIds() {
		return procedureIds;
	}
	public void setProcedureIds(String[] procedureIds) {
		this.procedureIds = procedureIds;
	}
	public Long getBacthId() {
		return bacthId;
	}
	public void setBacthId(Long bacthId) {
		this.bacthId = bacthId;
	}
	public Bacth getBacth() {
		return bacth;
	}
	public void setBacth(Bacth bacth) {
		this.bacth = bacth;
	}

	public String getUserIds() {
		return userIds;
	}
	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}
	public String[] getUsersIds() {
		return usersIds;
	}
	public void setUsersIds(String[] usersIds) {
		this.usersIds = usersIds;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Long getProcedureId() {
		return procedureId;
	}
	public void setProcedureId(Long procedureId) {
		this.procedureId = procedureId;
	}
	public String getProcedureName() {
		return procedureName;
	}
	public void setProcedureName(String procedureName) {
		this.procedureName = procedureName;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Double getExpectTime() {
		return expectTime;
	}
	public void setExpectTime(Double expectTime) {
		this.expectTime = expectTime;
	}
	public Double getTaskTime() {
		return taskTime;
	}
	public void setTaskTime(Double taskTime) {
		this.taskTime = taskTime;
	}
	public Double getTaskPrice() {
		return taskPrice;
	}
	public void setTaskPrice(Double taskPrice) {
		this.taskPrice = taskPrice;
	}

	
	public Double getPayB() {
		return payB;
	}
	public void setPayB(Double payB) {
		this.payB = payB;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
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
	public String getBacthNumber() {
		return bacthNumber;
	}
	public void setBacthNumber(String bacthNumber) {
		this.bacthNumber = bacthNumber;
	}
	public Double getExpectTaskPrice() {
		return expectTaskPrice;
	}
	public void setExpectTaskPrice(Double expectTaskPrice) {
		this.expectTaskPrice = expectTaskPrice;
	}

	
	
	
	
	

    
    
    
    
    
    


}
