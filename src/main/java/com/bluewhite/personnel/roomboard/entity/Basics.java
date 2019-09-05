package com.bluewhite.personnel.roomboard.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;

/**
 * 招聘汇总数据
 * 
 * @author qiyong
 *
 */
@Entity
@Table(name = "person_basics" )
public class Basics extends BaseEntity<Long> {
	
	/**
	 * 时间
	 */
	@Column(name = "time")
	private Date time;
	
	/**
	 * 日期内投入平台广告费
	 */
	@Transient
	private Double advertisementPrice;
	
	
	/**
	 * 日期内面试招聘人员费用/元填写→
	 */
	@Column(name = "recruit_User_Price")
	private Double recruitUserPrice;
	
	
	/**
	 * 当月面试应聘人员数量/人填写→
	 */
	@Transient
	private Integer number;
	
	
	/**
	 * 每人占到应聘费用
	 */
	@Transient
	private Double occupyPrice;
	
	
	/**
	 * 摊到的招聘费用
	 */
	@Transient
	private Double sharePrice;
	
	/**
	 * 培训费用
	 */
	@Transient
	private Double trainPrice;
	/**
	 *当月应聘被录取人员数量/人填写→
	 */
	@Transient
	private Double admissionNum;
	
	/**
	 * 当月计划人员数量
	 */
	@Transient
	private Double planNumber;
	
	/**
	 * 计划没人占到费用
	 */
	@Transient
	private Double planPrice;
	
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
	
	
	public Double getSharePrice() {
		return sharePrice;
	}
	public void setSharePrice(Double sharePrice) {
		this.sharePrice = sharePrice;
	}
	public Double getTrainPrice() {
		return trainPrice;
	}
	public void setTrainPrice(Double trainPrice) {
		this.trainPrice = trainPrice;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	
	public Double getAdvertisementPrice() {
		return advertisementPrice;
	}
	public void setAdvertisementPrice(Double advertisementPrice) {
		this.advertisementPrice = advertisementPrice;
	}
	public Double getRecruitUserPrice() {
		return recruitUserPrice;
	}
	public void setRecruitUserPrice(Double recruitUserPrice) {
		this.recruitUserPrice = recruitUserPrice;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Double getOccupyPrice() {
		return occupyPrice;
	}
	public void setOccupyPrice(Double occupyPrice) {
		this.occupyPrice = occupyPrice;
	}
	
	public Double getPlanNumber() {
		return planNumber;
	}
	public void setPlanNumber(Double planNumber) {
		this.planNumber = planNumber;
	}
	public Double getPlanPrice() {
		return planPrice;
	}
	public void setPlanPrice(Double planPrice) {
		this.planPrice = planPrice;
	}
	public Double getAdmissionNum() {
		return admissionNum;
	}
	public void setAdmissionNum(Double admissionNum) {
		this.admissionNum = admissionNum;
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
