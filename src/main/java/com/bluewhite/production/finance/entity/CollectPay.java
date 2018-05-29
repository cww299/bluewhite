package com.bluewhite.production.finance.entity;

import java.util.Date;
/**
 * 汇总类
 * @author zhangliang
 *
 */
public class CollectPay {
	
	/**
	 * 员工姓名
	 */
    private String userName;
	
	/**
	 * 员工id
	 */
    private Long userId;
    
	/**
	 * 考勤时间
	 */
	private Double time;
    
	/**
	 * A工资数额
	 */
	private Double payA;
	
	/**
	 * B工资数额
	 */
	private Double payB;
	
	/**
	 * 上浮后的B工资
	 */
	private Double addPayB;
	
	/**
	 * 考虑个人调节上浮后的B
	 */
	private Double addSelfPayB;
	
	/**
	 * 个人调节发放比例
	 */
	private Double addSelfNumber;
	
	/**
	 * 整体上浮下调比例
	 */
	private Double addNumber;
	
	/**
	 * 上浮后的加绩工资
	 */
	private Double addPerformancePay;
	
	/**
	 * 无加绩的配合奖励
	 */
	private Double noPerformanceNumber;
	
	/**
	 * 上浮后无加绩固定给予工资
	 */
	private Double noPerformancePay;
	
	/**
	 * 无绩效小时工资
	 */
	private Double noTimePay;
	
	/**
	 * 有绩效小时工资
	 */
	private Double timePay;
	
	
	/**
	 * 工序所属部门类型 (1=一楼质检，2=一楼包装，3=二楼针工)
	 */
	private Integer type;
	
	
	/**
	 * 查询字段开始时间
	 */
	private Date orderTimeBegin;
	/**
	 * 查询字段结束时间
	 */
	private Date orderTimeEnd;
	
	
	public Double getTime() {
		return time;
	}
	public void setTime(Double time) {
		this.time = time;
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
	public Double getPayA() {
		return payA;
	}
	public void setPayA(Double payA) {
		this.payA = payA;
	}
	public Double getPayB() {
		return payB;
	}
	public void setPayB(Double payB) {
		this.payB = payB;
	}
	public Double getAddPayB() {
		return addPayB;
	}
	public void setAddPayB(Double addPayB) {
		this.addPayB = addPayB;
	}
	public Double getAddSelfPayB() {
		return addSelfPayB;
	}
	public void setAddSelfPayB(Double addSelfPayB) {
		this.addSelfPayB = addSelfPayB;
	}
	public Double getAddSelfNumber() {
		return addSelfNumber;
	}
	public void setAddSelfNumber(Double addSelfNumber) {
		this.addSelfNumber = addSelfNumber;
	}
	public Double getAddNumber() {
		return addNumber;
	}
	public void setAddNumber(Double addNumber) {
		this.addNumber = addNumber;
	}
	public Double getAddPerformancePay() {
		return addPerformancePay;
	}
	public void setAddPerformancePay(Double addPerformancePay) {
		this.addPerformancePay = addPerformancePay;
	}
	public Double getNoPerformanceNumber() {
		return noPerformanceNumber;
	}
	public void setNoPerformanceNumber(Double noPerformanceNumber) {
		this.noPerformanceNumber = noPerformanceNumber;
	}
	public Double getNoPerformancePay() {
		return noPerformancePay;
	}
	public void setNoPerformancePay(Double noPerformancePay) {
		this.noPerformancePay = noPerformancePay;
	}
	public Double getNoTimePay() {
		return noTimePay;
	}
	public void setNoTimePay(Double noTimePay) {
		this.noTimePay = noTimePay;
	}
	public Double getTimePay() {
		return timePay;
	}
	public void setTimePay(Double timePay) {
		this.timePay = timePay;
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
	
	
	

}
