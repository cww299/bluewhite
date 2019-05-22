package com.bluewhite.finance.consumption.entity;

import java.util.Date;

import javax.persistence.Column;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

public class ConsumptionPoi extends BaseRowModel{
	
	/**
	 * 订单批次号
	 */
	 @ExcelProperty(index = 0)
    private String batchNumber;
	
	/**
	 * 消费内容
	 */
	 @ExcelProperty(index = 1)
	private String content;


	/**
	 * 消费对象
	 */
	 @ExcelProperty(index = 2)
	private String customerName;
	
	 
	 /**
	  * 报销人姓名
	  */
	 @ExcelProperty(index = 3)
	 private String username;
	
	/**
	 * (申请人申请时)金额
	 */
	 @ExcelProperty(index = 4)
	private Double money;

	/**
	 * (申请人申请时)申请日期
	 */
	 @ExcelProperty(index = 5)
	private Date expenseDate;
	 
	/**
	 * 到货日期
	 */
	 @ExcelProperty(index = 6)
	private Date logisticsDate; 
	

	public Date getLogisticsDate() {
		return logisticsDate;
	}

	public void setLogisticsDate(Date logisticsDate) {
		this.logisticsDate = logisticsDate;
	}

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Date getExpenseDate() {
		return expenseDate;
	}

	public void setExpenseDate(Date expenseDate) {
		this.expenseDate = expenseDate;
	}
	
	
}
