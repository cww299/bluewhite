package com.bluewhite.production.finance.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;

/**
 * 数据汇总表
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_collect_information")
public class CollectInformation  extends BaseEntity<Long>{
	
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
	@Column(name = "type")
	private Integer type;

	/**
	 * 是否是返工标识符（0=不是，1=是）
	 */
	private Integer flag;
	
	/**
	 * 各批次地区差价汇总(不予给付汇总)
	 */
	@Column(name = "regionalPrice")
	private Double regionalPrice;
	
	/**
	 * 全表加工费  汇总
	 */
	@Column(name = "sumTask")
	private Double sumTask;

	/**
	 * 返工费 汇总
	 */
	@Column(name = "sumTaskFlag")
	private Double sumTaskFlag;
	
	/**
	 * 杂工费 汇总
	 */
	@Column(name = "sumFarragoTask")
	private Double sumFarragoTask;
	
	/**
	 * 全表加工费,返工费和杂工费汇总
	 */
	@Column(name = "priceCollect")
	private Double priceCollect;
	
	/**
	 * 不予给付汇总占比
	 */
	@Column(name = "proportion")
	private Double proportion=0.0;
	
	/**
	 * 预算多余在手部分
	 */
	@Column(name = "overtop")
	private Double overtop;
	
	
	/**
	 * 打算给予A汇总
	 * @return
	 */
	@Column(name = "sumAttendancePay")
	private Double sumAttendancePay;
	
	/**
	 * 我们可以给予一线的
	 */
	@Column(name = "giveThread")
	private Double giveThread;
	
	/**
	 * 一线剩余给我们
	 */
	@Column(name = "surplusThread")
	private Double surplusThread;
	
	/**
	 * 考虑管理费，预留在手等。可调配资金
	 */
	@Column(name = "deployPrice")
	private Double deployPrice;
	
	/**
	 * 考虑管理费，预留在手等。可调配资金
	 */
	@Column(name = "manage")
	private Double manage;
	
	/**
	 * 模拟得出可调配资金
	 */
	@Column(name = "analogDeployPrice")
	private Double analogDeployPrice;
	
	
	
	/**
	 * 从A考勤开始日期以消费的房租
	 */
	@Column(name = "sumChummage")
	private Double sumChummage;
	
	
	/**
	 * 从A考勤开始日期以消费的水电
	 */
	@Column(name = "sumHydropower")
	private Double sumHydropower;
	
	
	/**
	 * 从A考勤开始日期以消费的后勤
	 */
	@Column(name = "sumLogistics")
	private Double sumLogistics;
	
	
	
	/**
	 * 模拟当月非一线人员发货绩效
	 */
	@Column(name = "analogPerformance")
	private Double analogPerformance;
	
	
	
	/**
	 * 剩余净管理
	 */
	@Column(name = "surplusManage")
	private Double surplusManage;
	
	
	/**
	 * 净管理费给付比→
	 */
	@Column(name = "manageProportion")
	private Double manageProportion;
	
	
	/**
	 * 从开始日至今可发放管理费加绩比
	 */
	@Column(name = "managePerformanceProportion")
	private Double managePerformanceProportion;
	
	
	
	/**
	 * 模拟当月非一线人员出勤小时
	 */
	@Column(name = "analogTime")
	private Double analogTime;
	
	
	
	/**
	 * 每小时可发放
	 */
	@Column(name = "grant")
	private Double grant;
	
	/**
	 * 给付后车间剩余
	 */
	@Column(name = "giveSurplus")
	private Double giveSurplus;
	
	/**
	 * 其中股东占比
	 */
	@Column(name = "shareholderProportion")
	private Double shareholderProportion = 0.0;
	
	/**
	 * 其中股东收益
	 */
	@Column(name = "shareholder")
	private Double shareholder;
	
	/**
	 * 车间剩余 
	 */
	@Column(name = "workshopSurplus")
	private Double workshopSurplus;
	
	/**
	 * 部门支出
	 */
	@Column(name = "departmental_expenditure")
	private String departmentalExpenditure;
	
	
	
	
	


	public String getDepartmentalExpenditure() {
		return departmentalExpenditure;
	}

	public void setDepartmentalExpenditure(String departmentalExpenditure) {
		this.departmentalExpenditure = departmentalExpenditure;
	}

	public Double getManage() {
		return manage;
	}

	public void setManage(Double manage) {
		this.manage = manage;
	}

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
