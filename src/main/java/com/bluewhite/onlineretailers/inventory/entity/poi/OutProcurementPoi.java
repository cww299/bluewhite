package com.bluewhite.onlineretailers.inventory.entity.poi;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

public class OutProcurementPoi extends BaseRowModel{
	
	/**
	 * 产品名称
	 */
	@ExcelProperty(index = 0)
	private String name;
	
	/**
	 * 数量
	 * 
	 */
	@ExcelProperty(index = 1)
	private Integer number;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
	
	

}
