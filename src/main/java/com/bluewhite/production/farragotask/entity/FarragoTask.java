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
	 * 杂工所属部门类型 (1=一楼质检，2=一楼包装，3=二楼针工)
	 */
	@Column(name = "type")
	private Integer type;
	
	
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
	 * 杂工实际工作时间
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
