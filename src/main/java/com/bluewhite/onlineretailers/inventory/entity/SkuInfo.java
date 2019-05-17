package com.bluewhite.onlineretailers.inventory.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
/**
 * 电商商品的sku信息
 * @author zhangliang
 *
 */
@Entity
@Table(name = "online_skuInfo")
public class SkuInfo extends BaseEntity<Long>{
	/**
	 * 指定规格的货号
	 * 
	 */
	@Column(name = "cargo_number")
	private String cargoNumber;
	
	/**
	 * 可销售数量
	 * 
	 */
	@Column(name = "amount_onsale")
	private String amountOnSale;
	
	/**
	 * 建议零售价
	 * 
	 */
	@Column(name = "retail_price")
	private Double retailPrice;
	
	/**
	 * 报价时该规格的单价
	 * 
	 */
	@Column(name = "price")
	private Double price;
	
	/**
	 * 商品编码
	 * 
	 */
	@Column(name = "sku_code")
	private String skuCode;
	
	/**
	 * skuId
	 * 
	 */
	@Column(name = "sku_id")
	private Long skuId;
	
	/**
	 * specId
	 * 
	 */
	@Column(name = "spec_id")
	private String specId;
	
	/**
	 * specId
	 * 
	 */
	@Column(name = "consign_price")
	private Double consignPrice;

	public String getCargoNumber() {
		return cargoNumber;
	}

	public void setCargoNumber(String cargoNumber) {
		this.cargoNumber = cargoNumber;
	}

	public String getAmountOnSale() {
		return amountOnSale;
	}

	public void setAmountOnSale(String amountOnSale) {
		this.amountOnSale = amountOnSale;
	}

	public Double getRetailPrice() {
		return retailPrice;
	}

	public void setRetailPrice(Double retailPrice) {
		this.retailPrice = retailPrice;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getSkuCode() {
		return skuCode;
	}

	public void setSkuCode(String skuCode) {
		this.skuCode = skuCode;
	}

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public String getSpecId() {
		return specId;
	}

	public void setSpecId(String specId) {
		this.specId = specId;
	}

	public Double getConsignPrice() {
		return consignPrice;
	}

	public void setConsignPrice(Double consignPrice) {
		this.consignPrice = consignPrice;
	}
	
	
	
	
}
