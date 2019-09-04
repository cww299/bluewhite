package com.bluewhite.product.primecost.cutparts.entity;

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
 * cc裁片填写
 * 
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_product_cutparts")
public class CutParts extends BaseEntity<Long> {

	/**
	 * 产品id
	 */
	@Column(name = "product_id")
	private Long productId;

	/**
	 * 批量产品数量或模拟批量数(默认2000)
	 */
	@Column(name = "number")
	private Integer number = 2000;

	/**
	 * 裁剪页面id
	 */
	@Column(name = "tailor_id")
	private Long tailorId;

	/**
	 * 压货环节id
	 */
	@Column(name = "overstock_id")
	private Long overstockId;

	/**
	 * 压货环节
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "overstock_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseOne overstock;
	
	/**
	 * 面料产品id
	 */
	@Column(name = "materiel_id")
	private Long materielId;

	/**
	 * 面料
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "materiel_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Materiel materiel;

	/**
	 * 选择该样品的裁片名字
	 */
	@Column(name = "cutparts_name")
	private String cutPartsName;

	/**
	 * 面料损耗默认值
	 */
	@Column(name = "loss")
	private Double loss;

	/**
	 * 手填单个使用片数，不填默认1片
	 */
	@Column(name = "cutparts_number")
	private Integer cutPartsNumber = 1;

	/**
	 * 单片周长
	 */
	@Column(name = "perimeter")
	private Double perimeter;

	/**
	 * 使用片数总周长
	 */
	@Column(name = "all_perimeter")
	private Double allPerimeter;

	/**
	 * 填写单片用料（单片的用料）
	 */
	@Column(name = "one_material")
	private Double oneMaterial;

	/**
	 * 单位id
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
	 * 各单片比全套用料
	 */
	@Column(name = "scale_material")
	private Double scaleMaterial;
	
	/**
	 * 该片在这个货中的单只用料（累加处）
	 */
	@Column(name = "add_material")
	private Double addMaterial;

	/**
	 * 手动损耗选择
	 */
	@Column(name = "manual_loss")
	private Double manualLoss;
	
	/**
	 * 需要复合选(0=否，1=是)
	 */
	@Column(name = "composite")
	private Integer composite;

	/**
	 * 选择是否双层对复(0=否，1=是)
	 */
	@Column(name = "double_composite")
	private Integer doubleComposite;

	/**
	 * 复合面料id
	 */
	@Column(name = "complex_materiel_id")
	private Long complexMaterielId;

	/**
	 * 复合面料
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "complex_materiel_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Materiel complexMateriel;

	/**
	 * 复合物手动损耗选择
	 */
	@Column(name = "composite_manual_loss")
	private Double compositeManualLoss;

	/**
	 * 当批各单片用料
	 */
	@Column(name = "batch_material")
	private Double batchMaterial;

	/**
	 * 当批各单片价格
	 */
	@Column(name = "batch_material_price")
	private Double batchMaterialPrice;

	/**
	 * 当批复合物用料
	 */
	@Column(name = "complex_batch_material")
	private Double complexBatchMaterial;

	/**
	 * 当批复合物各单片价格
	 * 
	 */
	@Column(name = "batch_complex_material_price")
	private Double batchComplexMaterialPrice;

	/**
	 * 当批复合物加加工费价格
	 * 
	 */
	@Column(name = "batch_complex_add_price")
	private Double batchComplexAddPrice;

	/**
	 * 面料价格(含复合物料和加工费）
	 */
	@Transient
	private Double oneCutPartsPrice;
	
	
	public BaseOne getOverstock() {
		return overstock;
	}

	public void setOverstock(BaseOne overstock) {
		this.overstock = overstock;
	}

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

	public Long getComplexMaterielId() {
		return complexMaterielId;
	}

	public void setComplexMaterielId(Long complexMaterielId) {
		this.complexMaterielId = complexMaterielId;
	}

	public Materiel getComplexMateriel() {
		return complexMateriel;
	}

	public void setComplexMateriel(Materiel complexMateriel) {
		this.complexMateriel = complexMateriel;
	}

	public Long getMaterielId() {
		return materielId;
	}

	public void setMaterielId(Long materielId) {
		this.materielId = materielId;
	}

	public Double getOneCutPartsPrice() {
		return oneCutPartsPrice;
	}

	public void setOneCutPartsPrice(Double oneCutPartsPrice) {
		this.oneCutPartsPrice = oneCutPartsPrice;
	}

	public Long getOverstockId() {
		return overstockId;
	}

	public void setOverstockId(Long overstockId) {
		this.overstockId = overstockId;
	}

	public Long getTailorId() {
		return tailorId;
	}

	public void setTailorId(Long tailorId) {
		this.tailorId = tailorId;
	}

	public Double getCompositeManualLoss() {
		return compositeManualLoss;
	}

	public void setCompositeManualLoss(Double compositeManualLoss) {
		this.compositeManualLoss = compositeManualLoss;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Double getLoss() {
		return loss;
	}

	public void setLoss(Double loss) {
		this.loss = loss;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Integer getDoubleComposite() {
		return doubleComposite;
	}

	public void setDoubleComposite(Integer doubleComposite) {
		this.doubleComposite = doubleComposite;
	}

	public Double getComplexBatchMaterial() {
		return complexBatchMaterial;
	}

	public void setComplexBatchMaterial(Double complexBatchMaterial) {
		this.complexBatchMaterial = complexBatchMaterial;
	}

	public Double getBatchComplexMaterialPrice() {
		return batchComplexMaterialPrice;
	}

	public void setBatchComplexMaterialPrice(Double batchComplexMaterialPrice) {
		this.batchComplexMaterialPrice = batchComplexMaterialPrice;
	}

	public Double getBatchComplexAddPrice() {
		return batchComplexAddPrice;
	}

	public void setBatchComplexAddPrice(Double batchComplexAddPrice) {
		this.batchComplexAddPrice = batchComplexAddPrice;
	}

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

	public Double getAllPerimeter() {
		return allPerimeter;
	}

	public void setAllPerimeter(Double allPerimeter) {
		this.allPerimeter = allPerimeter;
	}

	public Double getPerimeter() {
		return perimeter;
	}

	public void setPerimeter(Double perimeter) {
		this.perimeter = perimeter;
	}

	public Integer getComposite() {
		return composite;
	}

	public void setComposite(Integer composite) {
		this.composite = composite;
	}

	public Double getOneMaterial() {
		return oneMaterial;
	}

	public void setOneMaterial(Double oneMaterial) {
		this.oneMaterial = oneMaterial;
	}

	public Double getScaleMaterial() {
		return scaleMaterial;
	}

	public void setScaleMaterial(Double scaleMaterial) {
		this.scaleMaterial = scaleMaterial;
	}

	public Double getAddMaterial() {
		return addMaterial;
	}

	public void setAddMaterial(Double addMaterial) {
		this.addMaterial = addMaterial;
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

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

}
