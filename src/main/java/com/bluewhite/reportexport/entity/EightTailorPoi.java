package com.bluewhite.reportexport.entity;

import com.bluewhite.common.utils.excel.Poi;

public class EightTailorPoi {
	
	/**
	 * 工序名称
	 */
	@Poi(name = "", column = "A")
	private String name;
	
	/**
	 * 裁剪数量
	 */
	@Poi(name = "", column = "B")
	private Integer number;
	
	
	/**
	 * 总片数
	 */
	@Poi(name = "", column = "C")
	private Integer allNumber;
	
	/**
	 * 拉布时间
	 */
	@Poi(name = "", column = "N")
	private Integer clothTime;
	
	
	/**
	 * 单片激光放快手时间
	 */
	@Poi(name = "", column = "P")
	private Integer laserTime;

	/**
	 * 裁片周长
	 */
	@Poi(name = "", column = "H")
	private Double perimeter;

	
	
	
	
	public Integer getClothTime() {
		return clothTime;
	}

	public void setClothTime(Integer clothTime) {
		this.clothTime = clothTime;
	}

	public Integer getLaserTime() {
		return laserTime;
	}

	public void setLaserTime(Integer laserTime) {
		this.laserTime = laserTime;
	}

	public Integer getAllNumber() {
		return allNumber;
	}

	public void setAllNumber(Integer allNumber) {
		this.allNumber = allNumber;
	}

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

	public Double getPerimeter() {
		return perimeter;
	}

	public void setPerimeter(Double perimeter) {
		this.perimeter = perimeter;
	}
	
	
	

}
