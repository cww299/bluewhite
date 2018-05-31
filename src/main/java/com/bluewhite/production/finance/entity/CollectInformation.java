package com.bluewhite.production.finance.entity;

import java.util.Date;

public class CollectInformation {
	
	/**
	 * 查询字段开始时间
	 */
	private Date orderTimeBegin;
	/**
	 * 查询字段结束时间
	 */
	private Date orderTimeEnd;
	
	/**
	 * 工序所属部门类型 (1=一楼质检，2=一楼包装，3=二楼针工)
	 */
	private Integer type;

	/**
	 * 是否是返工标识符（0=不是，1=是）
	 */
	private Integer flag;
	
	/**
	 * 各批次地区差价汇总(不予给付汇总)
	 */
	private Double regionalPrice;
	
	/**
	 * 全表加工费  汇总
	 */
	private Double sumTask;

	/**
	 * 返工费 汇总
	 */
	private Double sumTaskFlag;
	
	/**
	 * 杂工费 汇总
	 */
	private Double sumFarragoTask;
	
	/**
	 * 全表加工费,返工费和杂工费汇总
	 */
	private Double priceCollect;
	
	/**
	 * 不予给付汇总占比
	 */
	private Double proportion;
	
	/**
	 * 预算多余在手部分
	 */
	private Double overtop;
	
	
	/**
	 * 打算给予A汇总
	 * @return
	 */
	private Double sumAttendancePay;
	
	/**
	 * 我们可以给予一线的
	 */
	private Double giveThread;
	/**
	 * 一线剩余给我们
	 */
	private Double surplusThread;
	
	
	/**
	 * 根据类型返回不同的汇总数据
	 * （0=生产成本数据汇总，1=员工成本数据汇总）
	 */
	private Integer status;
	
	
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Double getSumAttendancePay() {
		return sumAttendancePay;
	}

	public void setSumAttendancePay(Double sumAttendancePay) {
		this.sumAttendancePay = sumAttendancePay;
	}

	public Double getGiveThread() {
		return giveThread;
	}

	public void setGiveThread(Double giveThread) {
		this.giveThread = giveThread;
	}

	public Double getSurplusThread() {
		return surplusThread;
	}

	public void setSurplusThread(Double surplusThread) {
		this.surplusThread = surplusThread;
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Double getRegionalPrice() {
		return regionalPrice;
	}

	public void setRegionalPrice(Double regionalPrice) {
		this.regionalPrice = regionalPrice;
	}

	public Double getSumTask() {
		return sumTask;
	}

	public void setSumTask(Double sumTask) {
		this.sumTask = sumTask;
	}

	public Double getSumTaskFlag() {
		return sumTaskFlag;
	}

	public void setSumTaskFlag(Double sumTaskFlag) {
		this.sumTaskFlag = sumTaskFlag;
	}

	public Double getSumFarragoTask() {
		return sumFarragoTask;
	}

	public void setSumFarragoTask(Double sumFarragoTask) {
		this.sumFarragoTask = sumFarragoTask;
	}

	public Double getPriceCollect() {
		return priceCollect;
	}

	public void setPriceCollect(Double priceCollect) {
		this.priceCollect = priceCollect;
	}

	public Double getProportion() {
		return proportion;
	}

	public void setProportion(Double proportion) {
		this.proportion = proportion;
	}

	public Double getOvertop() {
		return overtop;
	}

	public void setOvertop(Double overtop) {
		this.overtop = overtop;
	}
	
	
	

}
