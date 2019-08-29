package com.bluewhite.product.primecost.materials.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.product.primecostbasedata.entity.BaseOne;
import com.bluewhite.product.primecostbasedata.entity.Materiel;
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
	 * 物料名id
	 */
	@Column(name = "materiel_id")
    private Long materielId;
	
	/**
	 *  物料
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "materiel_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Materiel materiel;
	
	/**
	 * 压货环节id
	 */
	@Column(name = "overstock_id")
	private Long overstockId;
	
	/**
	 * 填写单片用料（单片的用料）
	 */
	@Column(name = "one_material")
    private Double oneMaterial;
	
	/**
	 * 单位填写选择id
	 */
	@Column(name = "unit_id")
    private Long unitId;

	/**
	 * 单位
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "unit_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseOne unit;
	
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
	 * 当批当品种用量(手选单位）
	 */
	@Column(name = "batch_material")
    private Double batchMaterial;
	
	/**
	 * 当批当品种价格
	 */
	@Column(name = "batch_material_price")
    private Double batchMaterialPrice;
	
	/**
	 * 单只除面料以外的其他物料价格
	 */
	@Transient
    private Double oneOtherCutPartsPrice;
	
	
	
	
	public Materiel getMateriel() {
		return materiel;
	}

	public void setMateriel(Materiel materiel) {
		this.materiel = materiel;
	}

	public BaseOne getUnit() {
		return unit;
	}

	public void setUnit(BaseOne unit) {
		this.unit = unit;
	}

	public Double getOneOtherCutPartsPrice() {
		return oneOtherCutPartsPrice;
	}

	public void setOneOtherCutPartsPrice(Double oneOtherCutPartsPrice) {
		this.oneOtherCutPartsPrice = oneOtherCutPartsPrice;
	}

	public Long getMaterielId() {
		return materielId;
	}

	public void setMaterielId(Long materielId) {
		this.materielId = materielId;
	}

	public Long getOverstockId() {
		return overstockId;
	}

	public void setOverstockId(Long overstockId) {
		this.overstockId = overstockId;
	}

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

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

	public Double getOneMaterial() {
		return oneMaterial;
	}

	public void setOneMaterial(Double oneMaterial) {
		this.oneMaterial = oneMaterial;
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
