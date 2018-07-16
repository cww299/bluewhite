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
	private Double number;
	
	
	/**
	 * 总片数
	 */
	@Poi(name = "", column = "C")
	private Double allNumber;
	
	/**
	 * 拉布时间
	 */
	@Poi(name = "", column = "N")
	private Double clothTime;
	
	
	/**
	 * 单片激光放快手时间
	 */
	@Poi(name = "", column = "P")
	private Double laserTime;

	/**
	 * 裁片周长
	 */
	@Poi(name = "", column = "H")
	private Double perimeter;

	/**
	 * 叠布秒数（含快手)
	 */
	@Poi(name = "", column = "L")
	private Double overlay;
	
	/**
	 * 冲压秒数（含快手)
	 */
	@Poi(name = "", column = "M")
	private Double stamping;
	
	

	public Double getOverlay() {
		return overlay;
	}

	public void setOverlay(Double overlay) {
		this.overlay = overlay;
	}

	public Double getStamping() {
		return stamping;
	}

	public void setStamping(Double stamping) {
		this.stamping = stamping;
	}

	public Double getClothTime() {
		return clothTime;
	}

	public void setClothTime(Double clothTime) {
		this.clothTime = clothTime;
	}

	public Double getLaserTime() {
		return laserTime;
	}

	public void setLaserTime(Double laserTime) {
		this.laserTime = laserTime;
	}

	

	public Double getAllNumber() {
		return allNumber;
	}

	public void setAllNumber(Double allNumber) {
		this.allNumber = allNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	public Double getNumber() {
		return number;
	}

	public void setNumber(Double number) {
		this.number = number;
	}

	public Double getPerimeter() {
		return perimeter;
	}

	public void setPerimeter(Double perimeter) {
		this.perimeter = perimeter;
	}
	
	
	

}
