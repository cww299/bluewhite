package com.bluewhite.reportexport.entity;

import com.bluewhite.common.utils.excel.Poi;

public class MachinistProcedurePoi {
	
	/**
	 * 工艺名称
	 */
	@Poi(name = "", column = "A")
	private String name;
	
	/**
	 * 工序时间
	 */
	@Poi(name = "", column = "B")
	private Double workingTime;

	/**
	 * 数量（只）
	 */
	@Poi(name = "", column = "C")
	private String number;
	
	/**
	 * 单个加损耗时间
	 */
	@Poi(name = "", column = "D")
	private Double lossTime;
	
	/**
	 * 单个生产时间
	 */
	@Poi(name = "", column = "E")
	private Double oneTime;
	
	/**
	 * 揣剪时间（12只）
	 */
	@Poi(name = "", column = "F")
	private Double scissorsTime;

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



	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Double getLossTime() {
		return lossTime;
	}

	public void setLossTime(Double lossTime) {
		this.lossTime = lossTime;
	}

	public Double getOneTime() {
		return oneTime;
	}

	public void setOneTime(Double oneTime) {
		this.oneTime = oneTime;
	}

	public Double getScissorsTime() {
		return scissorsTime;
	}

	public void setScissorsTime(Double scissorsTime) {
		this.scissorsTime = scissorsTime;
	}
	
	
	
}
