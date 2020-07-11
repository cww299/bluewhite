package com.bluewhite.ledger.entity.poi;

import java.util.Date;

import com.alibaba.excel.annotation.ExcelProperty;

public class SalePoi {

	/**
	 * 发货日期
	 */
	@ExcelProperty(index = 0)
	private Date sendDate;

	/**
	 * 批次号
	 */
	@ExcelProperty(index = 1)
	private String bacthNumber;

	/**
	 * 产品名称
	 */
	@ExcelProperty(index = 2)
	private String productName;

	/**
	 * 实际数量
	 */
	@ExcelProperty(index = 3)
	private Integer count;
	
	/**
	 * 客户名称
	 */
	@ExcelProperty(index = 4)
	private String customerName;

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getBacthNumber() {
		return bacthNumber;
	}

	public void setBacthNumber(String bacthNumber) {
		this.bacthNumber = bacthNumber;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
}

