package com.bluewhite.production.finance.entity;

import java.util.Date;

public class MonthlyProduction {
	/**
	 * 考勤人数
	 */
	private Integer peopleNumber;
	
	/**
	 * 考勤总时间  
	 */
	private Double  time;
	
	/**
	 * 当天产量  
	 */
	private Double  productNumber;
	
	/**
	 * 当天产值(外发单价乘以质检的个数)   
	 */
	private Double productPrice;
	
	/**
	 * 返工出勤人数   
	 */
	private Double  reworkNumber;
	
	/**
	 * 返工出勤时间   
	 */
	private Double  reworkTurnTime;
	
	/**
	 * 返工个数  
	 */
	private Double  rework;
	
	/**
	 * 返工时间     
	 */
	private Double  reworkTime;
	
	
	
	/**
	 * 查询字段开始时间
	 */
	private Date orderTimeBegin;
	/**
	 * 查询字段结束时间
	 */
	private Date orderTimeEnd;
	
	
	private Integer type;
	
	
	

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



	public Integer getPeopleNumber() {
		return peopleNumber;
	}

	public void setPeopleNumber(Integer peopleNumber) {
		this.peopleNumber = peopleNumber;
	}

	public Double getTime() {
		return time;
	}

	public void setTime(Double time) {
		this.time = time;
	}

	public Double getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(Double productNumber) {
		this.productNumber = productNumber;
	}

	public Double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Double productPrice) {
		this.productPrice = productPrice;
	}

	public Double getReworkNumber() {
		return reworkNumber;
	}

	public void setReworkNumber(Double reworkNumber) {
		this.reworkNumber = reworkNumber;
	}

	public Double getReworkTurnTime() {
		return reworkTurnTime;
	}

	public void setReworkTurnTime(Double reworkTurnTime) {
		this.reworkTurnTime = reworkTurnTime;
	}

	public Double getRework() {
		return rework;
	}

	public void setRework(Double rework) {
		this.rework = rework;
	}

	public Double getReworkTime() {
		return reworkTime;
	}

	public void setReworkTime(Double reworkTime) {
		this.reworkTime = reworkTime;
	}
	
	
	
}
