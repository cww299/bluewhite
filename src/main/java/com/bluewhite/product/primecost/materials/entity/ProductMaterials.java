package com.bluewhite.product.primecost.materials.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
/**
 * dd除裁片以外的所有生产用料填写
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_product_materials")
public class ProductMaterials extends BaseEntity<Long>{
	
	/**
	 * 产品id
	 */
	@Column(name = "product_id")
    private Long productId;
	
	/**
	 * 批量产品数量或模拟批量数
	 */
	@Column(name = "number")
	private Integer number;
	
	/**
	 * 物料名
	 */
	@Column(name = "materials_name")
    private String materialsName;
	
	
	/**
	 * 填写单片用料（单片的用料）
	 */
	@Column(name = "one_material")
    private Double oneMaterial;
	
	
	/**
	 * 单位填写选择
	 */
	@Column(name = "unit")
    private String unit;
	
	
	/**
	 * 按选定单位产品单价
	 */
	@Column(name = "unit_cost")
    private Double unitCost;
	
	
	/**
	 * 手动损耗选择
	 */
	@Column(name = "manual_loss")
    private Double manualLoss;
	
	/**
	 * 产品单价
	 */
	@Column(name = "product_cost")
    private Double productCost;
	
	/**
	 * 产品单位
	 */
	@Column(name = "product_unit")
    private String productUnit;
	
	
	/**
	 * 当批当品种用量(手选单位）
	 */
	@Column(name = "batch_material")
    private Double batchMaterial;
	
	/**
	 * 当批当品种价格
	 */
	@Column(name = "batch_material_price")
    private Double batchMaterialPrice;
	
	
	

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getMaterialsName() {
		return materialsName;
	}

	public void setMaterialsName(String materialsName) {
		this.materialsName = materialsName;
	}

	public Double getOneMaterial() {
		return oneMaterial;
	}

	public void setOneMaterial(Double oneMaterial) {
		this.oneMaterial = oneMaterial;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Double getUnitCost() {
		return unitCost;
	}

	public void setUnitCost(Double unitCost) {
		this.unitCost = unitCost;
	}

	public Double getManualLoss() {
		return manualLoss;
	}

	public void setManualLoss(Double manualLoss) {
		this.manualLoss = manualLoss;
	}

	public Double getProductCost() {
		return productCost;
	}

	public void setProductCost(Double productCost) {
		this.productCost = productCost;
	}


	public String getProductUnit() {
		return productUnit;
	}

	public void setProductUnit(String productUnit) {
		this.productUnit = productUnit;
	}

	public Double getBatchMaterial() {
		return batchMaterial;
	}

	public void setBatchMaterial(Double batchMaterial) {
		this.batchMaterial = batchMaterial;
	}

	public Double getBatchMaterialPrice() {
		return batchMaterialPrice;
	}

	public void setBatchMaterialPrice(Double batchMaterialPrice) {
		this.batchMaterialPrice = batchMaterialPrice;
	}
	
	
	

}
