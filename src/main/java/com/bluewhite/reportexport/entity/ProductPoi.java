package com.bluewhite.reportexport.entity;

import com.bluewhite.common.utils.excel.Poi;

public class ProductPoi {
	
	/**
	 * 产品编号
	 */
	@Poi(name = "", column = "A")
	private String number;
	
	/**
	 * 产品名称
	 */
	@Poi(name = "", column = "B")
	private String name;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

	
	
	

}
