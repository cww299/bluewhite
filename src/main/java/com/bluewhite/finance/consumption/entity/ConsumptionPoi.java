package com.bluewhite.finance.consumption.entity;

import java.util.Date;

import com.alibaba.excel.annotation.ExcelProperty;

public class ConsumptionPoi {

	/**
	 * 消费内容
	 */
	@ExcelProperty(index = 0)
	private String content;

	/**
	 * 消费对象
	 */
	@ExcelProperty(index = 1)
	private String customerName;

	/**
	 * (申请人申请时)金额
	 */
	@ExcelProperty(index = 2)
	private Double money;

	/**
	 * (申请人申请时)申请日期
	 */
	@ExcelProperty(index = 3)
	private Date expenseDate;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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
