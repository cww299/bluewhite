package com.bluewhite.production.farragotask.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;

/**
 * 
 * 杂工（混杂工作，一些除正常之外的工作）
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_farrago_task")
public class FarragoTask  extends BaseEntity<Long>{
	
	
	/**
	 * 杂工批次
	 */
	@Column(name = "bacth")
	private String bacth;
	
	/**
	 * 杂工所属部门类型 (1=一楼质检，2=一楼包装，3=二楼针工)
	 */
	@Column(name = "type")
	private Integer type;
	
    /**
     * 杂工任务数量
     */
	@Column(name = "number")
    private Integer number;
	
    /**
     * 杂工工序时间
     */
	@Column(name = "procedure_time")
    private Double procedureTime;
	
	/**
	 * 杂工名称
	 */
	@Column(name = "name")
	private String name;
	
	/**
	 * 杂工实际成本费用
	 */
	@Column(name = "price")
	private Double price;
	
	/**
	 * 杂工实际工作时间(基础裸工时/分（无管理）)
	 */
	@Column(name = "time")
	private Double time;
	
	/**
	 * 分配时间（默认当前时间前一天）
	 */
	@Column(name = "allot_time")
	private Date allotTime;
	
	/**
	 * 领取任务人员ids(任务和员工多对多关系)
	 */
	@Column(name = "userIds")
	private  String userIds;
	
	/**
	 * 是否工序加价选择(杂工加绩选项)
	 */
	@Column(name = "performance")
	private String performance;
	
	/**
	 * 要添加的分价值（杂工加绩基础比值）
	 */
	@Column(name = "performance_number")
	private Double performanceNumber;
	
	/**
	 * 要添加的工价（杂工加绩具体工资数值）
	 */
	@Column(name = "performance_price")
	private Double performancePrice;
	
	
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
	 * 领取任务人员ids
	 */
	@Transient
	private  String[] usersIds;
	
	
	
	
	
	
	public Double getProcedureTime() {
		return procedureTime;
	}

	public void setProcedureTime(Double procedureTime) {
		this.procedureTime = procedureTime;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getBacth() {
		return bacth;
	}

	public void setBacth(String bacth) {
		this.bacth = bacth;
	}

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

	public Double getPerformancePrice() {
		return performancePrice;
	}

	public void setPerformancePrice(Double performancePrice) {
		this.performancePrice = performancePrice;
	}


	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Double getPrice() {
		return price;
	}


	public void setPrice(Double price) {
		this.price = price;
	}


	public Double getTime() {
		return time;
	}


	public void setTime(Double time) {
		this.time = time;
	}
	
	
	
	

}
