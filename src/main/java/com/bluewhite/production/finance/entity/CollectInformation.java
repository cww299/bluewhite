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
	private Double proportion=0.0;
	
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
	 * 考虑管理费，预留在手等。可调配资金
	 */
	private Double deployPrice;
	
	
	/**
	 * 模拟得出可调配资金
	 */
	private Double analogDeployPrice;
	
	
	
	/**
	 * 从A考勤开始日期以消费的房租
	 */
	private Double sumChummage;
	
	
	/**
	 * 从A考勤开始日期以消费的水电
	 */
	private Double sumHydropower;
	
	
	/**
	 * 从A考勤开始日期以消费的后勤
	 */
	private Double sumLogistics;
	
	
	
	/**
	 * 模拟当月非一线人员发货绩效
	 */
	private Double analogPerformance;
	
	
	
	/**
	 * 剩余净管理
	 */
	private Double surplusManage;
	
	
	/**
	 * 净管理费给付比→
	 */
	private Double manageProportion;
	
	
	/**
	 * 从开始日至今可发放管理费加绩比
	 */
	private Double managePerformanceProportion;
	
	
	
	/**
	 * 模拟当月非一线人员出勤小时
	 */
	private Double analogTime;
	
	
	
	/**
	 * 每小时可发放
	 */
	private Double grant;
	/**
	 * 给付后车间剩余
	 */
	private Double giveSurplus;
	
	/**
	 * 其中股东占比
	 */
	private Double shareholderProportion = 0.0;
	
	/**
	 * 其中股东收益
	 */
	private Double shareholder;
	
	/**
	 * 车间剩余 
	 */
	private Double workshopSurplus;
	
	/**
	 * 根据类型返回不同的汇总数据
	 * （0=生产成本数据汇总，1=员工成本数据汇总）
	 */
	private Integer status;
	
	
	
	
	public Double getGiveSurplus() {
		return giveSurplus;
	}

	public void setGiveSurplus(Double giveSurplus) {
		this.giveSurplus = giveSurplus;
	}

	public Double getShareholderProportion() {
		return shareholderProportion;
	}

	public void setShareholderProportion(Double shareholderProportion) {
		this.shareholderProportion = shareholderProportion;
	}

	public Double getShareholder() {
		return shareholder;
	}

	public void setShareholder(Double shareholder) {
		this.shareholder = shareholder;
	}

	public Double getWorkshopSurplus() {
		return workshopSurplus;
	}

	public void setWorkshopSurplus(Double workshopSurplus) {
		this.workshopSurplus = workshopSurplus;
	}

	public Double getDeployPrice() {
		return deployPrice;
	}

	public void setDeployPrice(Double deployPrice) {
		this.deployPrice = deployPrice;
	}

	public Double getAnalogDeployPrice() {
		return analogDeployPrice;
	}

	public void setAnalogDeployPrice(Double analogDeployPrice) {
		this.analogDeployPrice = analogDeployPrice;
	}

	public Double getSumChummage() {
		return sumChummage;
	}

	public void setSumChummage(Double sumChummage) {
		this.sumChummage = sumChummage;
	}

	public Double getSumHydropower() {
		return sumHydropower;
	}

	public void setSumHydropower(Double sumHydropower) {
		this.sumHydropower = sumHydropower;
	}

	public Double getSumLogistics() {
		return sumLogistics;
	}

	public void setSumLogistics(Double sumLogistics) {
		this.sumLogistics = sumLogistics;
	}

	public Double getAnalogPerformance() {
		return analogPerformance;
	}

	public void setAnalogPerformance(Double analogPerformance) {
		this.analogPerformance = analogPerformance;
	}

	public Double getSurplusManage() {
		return surplusManage;
	}

	public void setSurplusManage(Double surplusManage) {
		this.surplusManage = surplusManage;
	}

	public Double getManageProportion() {
		return manageProportion;
	}

	public void setManageProportion(Double manageProportion) {
		this.manageProportion = manageProportion;
	}

	public Double getManagePerformanceProportion() {
		return managePerformanceProportion;
	}

	public void setManagePerformanceProportion(Double managePerformanceProportion) {
		this.managePerformanceProportion = managePerformanceProportion;
	}

	public Double getAnalogTime() {
		return analogTime;
	}

	public void setAnalogTime(Double analogTime) {
		this.analogTime = analogTime;
	}

	public Double getGrant() {
		return grant;
	}

	public void setGrant(Double grant) {
		this.grant = grant;
	}

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
