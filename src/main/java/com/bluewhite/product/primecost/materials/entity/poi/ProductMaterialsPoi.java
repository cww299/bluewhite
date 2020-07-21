package com.bluewhite.product.primecost.materials.entity.poi;

import com.alibaba.excel.annotation.ExcelProperty;

public class ProductMaterialsPoi {

	/**
	 * 物料编号
	 */
	@ExcelProperty(index = 0)
	private String materialNumber;
	
	/**
	 * 单位
	 */
	@ExcelProperty(index = 1)
	private String unitName;
	
	/**
	 * 当片用料
	 */
	@ExcelProperty(index = 2)
	private Double oneMaterial;
	
	/**
	 * 手动损耗选择
	 */
	@ExcelProperty(index = 3)
    private Double manualLoss;
	
	/**
	 * 压货环节。默认裁剪
	 */
	private Long overstockId = 79l;


	public String getMaterialNumber() {
		return materialNumber;
	}

	public void setMaterialNumber(String materialNumber) {
		this.materialNumber = materialNumber;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Double getOneMaterial() {
		return oneMaterial;
	}

	public void setOneMaterial(Double oneMaterial) {
		this.oneMaterial = oneMaterial;
	}

	public Double getManualLoss() {
		return manualLoss;
	}

	public void setManualLoss(Double manualLoss) {
		this.manualLoss = manualLoss;
	}

	public Long getOverstockId() {
		return overstockId;
	}

	public void setOverstockId(Long overstockId) {
		this.overstockId = overstockId;
	}
	
}
