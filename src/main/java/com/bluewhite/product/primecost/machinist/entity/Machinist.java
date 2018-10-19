package com.bluewhite.product.primecost.machinist.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;

@Entity
@Table(name = "pro_product_machinist")
public class Machinist extends BaseEntity<Long> {

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
	 * 缝纫步骤名称
	 */
	@Column(name = "machinist_name")
	private String machinistName;
	
	/**
	 * 电脑推算机缝该工序费用
	 */
	@Column(name = "computer_machinist_price")
	private Double computerMachinistPrice;
	
	/**
	 * 试制机缝该工序费用
	 */
	@Column(name = "machinist_price")
	private Double machinistPrice;
	
	/**
	 * 选择单个入成本价格
	 */
	@Column(name = "cost_price")
    private Double costPrice;
	
	/**
	 * 总入成本价格
	 */
	@Column(name = "all_cost_price")
    private Double allCostPrice;
	
	
	/**
	 * 各单片比全套工价
	 */
	@Column(name = "scale_material")
    private Double scaleMaterial;
	
	/**
	 * 物料和上道压（裁剪）价
	 */
	@Column(name = "price_down")
    private Double priceDown;
	
	/**
	 * 物料和上道压（裁剪,上道机工）价-(只有在当行机工环节单独发放给某个加工店，这个才起作用)
	 */
	@Column(name = "price_down_remark")
    private Double priceDownRemark;
	
	/**
	 *为针工准备的压价
	 */
	@Column(name = "needlework_price_down")
    private Double needlework;
	
	/**
	 * 单独机工工序外发的压价
	 */
	@Column(name = "machinist_price_down")
    private Double machinistPriceDown;

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

	public String getMachinistName() {
		return machinistName;
	}

	public void setMachinistName(String machinistName) {
		this.machinistName = machinistName;
	}

	public Double getComputerMachinistPrice() {
		return computerMachinistPrice;
	}

	public void setComputerMachinistPrice(Double computerMachinistPrice) {
		this.computerMachinistPrice = computerMachinistPrice;
	}

	public Double getMachinistPrice() {
		return machinistPrice;
	}

	public void setMachinistPrice(Double machinistPrice) {
		this.machinistPrice = machinistPrice;
	}

	public Double getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}

	public Double getAllCostPrice() {
		return allCostPrice;
	}

	public void setAllCostPrice(Double allCostPrice) {
		this.allCostPrice = allCostPrice;
	}

	public Double getScaleMaterial() {
		return scaleMaterial;
	}

	public void setScaleMaterial(Double scaleMaterial) {
		this.scaleMaterial = scaleMaterial;
	}

	public Double getPriceDown() {
		return priceDown;
	}

	public void setPriceDown(Double priceDown) {
		this.priceDown = priceDown;
	}

	public Double getPriceDownRemark() {
		return priceDownRemark;
	}

	public void setPriceDownRemark(Double priceDownRemark) {
		this.priceDownRemark = priceDownRemark;
	}

	public Double getNeedlework() {
		return needlework;
	}

	public void setNeedlework(Double needlework) {
		this.needlework = needlework;
	}

	public Double getMachinistPriceDown() {
		return machinistPriceDown;
	}

	public void setMachinistPriceDown(Double machinistPriceDown) {
		this.machinistPriceDown = machinistPriceDown;
	}
	
	
	
	

}
