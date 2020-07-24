package com.bluewhite.product.primecost.cutparts.entity.poi;

import com.alibaba.excel.annotation.ExcelProperty;

public class CutPartsPoi {

	/**
	 * 裁片名称
	 */
	@ExcelProperty(index = 0)
	private String cutPartsName;
	
	/**
	 * 手填单个使用片数，不填默认1片
	 */
	@ExcelProperty(index = 1)
	private Integer cutPartsNumber;
	
	/**
	 * 单片周长
	 */
	@ExcelProperty(index = 2)
	private Double perimeter;
	
	/**
	 * 物料编号
	 */
	@ExcelProperty(index = 3)
	private String materialNumber;
	
	/**
	 * 当片用料
	 */
	@ExcelProperty(index = 4)
	private Double oneMaterial;
	
	/**
	 * 单位
	 */
	@ExcelProperty(index = 5)
	private String unitName;
	
	/**
	 * 手动损耗
	 */
	@ExcelProperty(index = 6)
	private Double manualLoss;
	
	/**
	 * 复合物编号
	 */
	@ExcelProperty(index = 7)
	private String complexMaterielNumber;
	
	/**
	 * 是否双层复合，中文
	 */
	@ExcelProperty(index = 8)
	private String doubleComposite = "否";
	
	/**
	 * 复合物手动损耗选择
	 */
	@ExcelProperty(index = 9)
	private Double compositeManualLoss = 0.03;
	
	/**
	 * 压货环节。默认裁剪
	 */
	private Long overstockId = 79l;

	public String getCutPartsName() {
		return cutPartsName;
	}

	public void setCutPartsName(String cutPartsName) {
		this.cutPartsName = cutPartsName;
	}

	public Integer getCutPartsNumber() {
		return cutPartsNumber;
	}

	public void setCutPartsNumber(Integer cutPartsNumber) {
		this.cutPartsNumber = cutPartsNumber;
	}

	public Double getPerimeter() {
		return perimeter;
	}

	public void setPerimeter(Double perimeter) {
		this.perimeter = perimeter;
	}

	public String getMaterialNumber() {
		return materialNumber;
	}

	public void setMaterialNumber(String materialNumber) {
		this.materialNumber = materialNumber;
	}

	public Double getOneMaterial() {
		return oneMaterial;
	}

	public void setOneMaterial(Double oneMaterial) {
		this.oneMaterial = oneMaterial;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Double getManualLoss() {
		return manualLoss;
	}

	public void setManualLoss(Double manualLoss) {
		this.manualLoss = manualLoss;
	}

	public String getComplexMaterielNumber() {
		return complexMaterielNumber;
	}

	public void setComplexMaterielNumber(String complexMaterielNumber) {
		this.complexMaterielNumber = complexMaterielNumber;
	}

	public String getDoubleComposite() {
		return doubleComposite;
	}

	public void setDoubleComposite(String doubleComposite) {
		this.doubleComposite = doubleComposite;
	}

	public Double getCompositeManualLoss() {
		return compositeManualLoss;
	}

	public void setCompositeManualLoss(Double compositeManualLoss) {
		this.compositeManualLoss = compositeManualLoss;
	}

	public Long getOverstockId() {
		return overstockId;
	}

	public void setOverstockId(Long overstockId) {
		this.overstockId = overstockId;
	}
	
	
}
