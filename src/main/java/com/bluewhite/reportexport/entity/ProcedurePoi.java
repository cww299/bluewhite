package com.bluewhite.reportexport.entity;

import com.bluewhite.common.utils.excel.Poi;

public class ProcedurePoi {
	
	/**
	 * 工序名称
	 */
	@Poi(name = "", column = "A")
	private String name;
	
	/**
	 * 工序时间
	 */
	@Poi(name = "", column = "C")
	private Double workingTime;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getWorkingTime() {
		return workingTime;
	}

	public void setWorkingTime(Double workingTime) {
		this.workingTime = workingTime;
	}
	

	
	

	

}
