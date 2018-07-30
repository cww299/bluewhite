package com.bluewhite.product.primecost.cutparts.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
/**
 * cc裁片填写
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_cutparts")
public class CutParts extends BaseEntity<Long>{
	
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
	 * 面料损耗默认值
	 */
	@Column(name = "loss")
	private Double loss;
	
	/**
	 * 选择该样品的裁片名字
	 */
	@Column(name = "cutparts_name")
    private String cutPartsName;
	
	/**
	 * 手填单个使用片数，不填默认1片
	 */
	@Column(name = "cutparts_number")
    private Integer cutPartsNumber;
	
	
	/**
	 * 使用片数周长
	 */
	@Column(name = "all_perimeter")
    private Double allPerimeter;
	
	/**
	 * 单片周长
	 */
	@Column(name = "perimeter")
    private Double perimeter;
	
	
	/**
	 * 面料产品编号
	 */
	@Column(name = "materiel_number")
    private String materielNumber;
	
	/**
	 * 面料产品名字
	 */
	@Column(name = "materiel_name")
    private String materielName;
	
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
	 * 复合面料产品编号
	 */
	@Column(name = "complex_materiel_number")
    private String complexMaterielNumber;
	
	/**
	 * 复合面料产品名字
	 */
	@Column(name = "complex_materiel_name")
    private String complexMaterielName;
	
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
	 * 复合物手动损耗选择
	 */
	@Column(name = "composite_manual_loss")
    private Double compositeManualLoss;
	
	/**
	 * 产品单价
	 */
	@Column(name = "product_cost")
    private Double productCost;
	
	/**
	 * 产品备注
	 */
	@Column(name = "product_remark")
    private String productRemark;
	
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
	 * 选择是否双层对复
	 */
	@Column(name = "bilayer")
	private Integer bilayer;
	
	/**
	 * 该复合物单价
	 */
	@Column(name = "complex_product_cost")
    private Double complexProductCost;
	
	/**
	 * 复合物产品备注
	 */
	@Column(name = "complex_product_remark")
    private String complexProductRemark;
	
	
	/**
	 * 当批复合物用料

	 */
	@Column(name = "complex_batch_material")
    private Double complexBatchMaterial;
	
	/**
	 * 当批复合物各单片价格

	 */
	@Column(name = "batch_complex_material_price")
    private Double batchComplexMaterialPrice;
	
	/**
	 * 当批复合物加加工费价格

	 */
	@Column(name = "batch_complex_add_price")
    private Double batchComplexAddPrice;
	
	


	


	public String getComplexProductRemark() {
		return complexProductRemark;
	}

	public void setComplexProductRemark(String complexProductRemark) {
		this.complexProductRemark = complexProductRemark;
	}

	public Double getCompositeManualLoss() {
		return compositeManualLoss;
	}

	public void setCompositeManualLoss(Double compositeManualLoss) {
		this.compositeManualLoss = compositeManualLoss;
	}

	public Integer getBilayer() {
		return bilayer;
	}

	public void setBilayer(Integer bilayer) {
		this.bilayer = bilayer;
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

	public String getComplexMaterielNumber() {
		return complexMaterielNumber;
	}

	public void setComplexMaterielNumber(String complexMaterielNumber) {
		this.complexMaterielNumber = complexMaterielNumber;
	}

	public String getComplexMaterielName() {
		return complexMaterielName;
	}

	public void setComplexMaterielName(String complexMaterielName) {
		this.complexMaterielName = complexMaterielName;
	}

	public Double getComplexProductCost() {
		return complexProductCost;
	}

	public void setComplexProductCost(Double complexProductCost) {
		this.complexProductCost = complexProductCost;
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

	public String getMaterielNumber() {
		return materielNumber;
	}

	public void setMaterielNumber(String materielNumber) {
		this.materielNumber = materielNumber;
	}

	public String getMaterielName() {
		return materielName;
	}

	public void setMaterielName(String materielName) {
		this.materielName = materielName;
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

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
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

	public Double getProductCost() {
		return productCost;
	}

	public void setProductCost(Double productCost) {
		this.productCost = productCost;
	}

	public String getProductRemark() {
		return productRemark;
	}

	public void setProductRemark(String productRemark) {
		this.productRemark = productRemark;
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
