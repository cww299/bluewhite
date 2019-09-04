package com.bluewhite.personnel.roomboard.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.basedata.entity.BaseData;

/**
 * 招聘记录
 * 
 * @author qiyong
 *
 */
@Entity
@Table(name = "person_plan" )
public class Plan extends BaseEntity<Long> {
	
	/**
	 * 时间
	 */
	@Column(name = "time")
	private Date time;
	
	/**
	 * 职位id
	 */
	@Column(name = "position_id")
	private Long positionId;
	
	/**
	 * 职位
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "position_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseData position;
	
	/**
	 * 部门id
	 */
	@Column(name = "orgName_id")
	private Long orgNameId;
	
	/**
	 * 部门
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orgName_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseData orgName;
	
	/**
	 * 人数
	 */
	@Column(name = "number")
	private Integer number;
	
	/**
	 * 预计到岗时间
	 */
	@Column(name = "estimate")
	private String estimate;
	
	/**
	 * 目标人数
	 */
	@Column(name = "target")
	private Integer target;
	
	/**
	 * 系数
	 */
	@Column(name = "coefficient")
	private Double coefficient;
	
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
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public Long getPositionId() {
		return positionId;
	}
	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}
	public BaseData getPosition() {
		return position;
	}
	public void setPosition(BaseData position) {
		this.position = position;
	}
	public Long getOrgNameId() {
		return orgNameId;
	}
	public void setOrgNameId(Long orgNameId) {
		this.orgNameId = orgNameId;
	}
	public BaseData getOrgName() {
		return orgName;
	}
	public void setOrgName(BaseData orgName) {
		this.orgName = orgName;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getEstimate() {
		return estimate;
	}
	public void setEstimate(String estimate) {
		this.estimate = estimate;
	}
	public Integer getTarget() {
		return target;
	}
	public void setTarget(Integer target) {
		this.target = target;
	}
	public Double getCoefficient() {
		return coefficient;
	}
	public void setCoefficient(Double coefficient) {
		this.coefficient = coefficient;
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
