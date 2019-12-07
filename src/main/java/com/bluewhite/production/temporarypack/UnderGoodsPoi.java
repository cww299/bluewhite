package com.bluewhite.production.temporarypack;

import java.util.Date;

import javax.persistence.Column;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

public class UnderGoodsPoi extends BaseRowModel{
	
	/**
	 * 产品名称
	 */
	@ExcelProperty(index = 0)
	private String name;
	
	/**
	 * 批次号
	 */
	@ExcelProperty(index = 1)
	private String bacthNumber;
	
	/**
	 * 数量
	 * 
	 */
	@ExcelProperty(index = 2)
	private Integer number;

	/**
	 * 下单时间
	 */
	@ExcelProperty(index = 3)
	private Date allotTime;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBacthNumber() {
		return bacthNumber;
	}

	public void setBacthNumber(String bacthNumber) {
		this.bacthNumber = bacthNumber;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Date getAllotTime() {
		return allotTime;
	}

	public void setAllotTime(Date allotTime) {
		this.allotTime = allotTime;
	}
	
	


}
