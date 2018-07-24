package com.bluewhite.product.primecost.primecost.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;

/**
 * 成本价格实体
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_prime_cost")
public class PrimeCost extends BaseEntity<Long>{
	
	
	
	/**
	 * 面料价格(含复合物料和加工费）
	 */
	@Column(name = "cut_parts_price")
    private Double cutPartsPrice;
	
	
	/**
	 * 除面料以外的其他物料价格
	 */
	@Column(name = "other_cut_parts_price")
    private Double otherCutPartsPrice;
	
	
	/**
	 * 裁剪价格
	 */
	@Column(name = "cut_price")
    private Double cutPrice;
	
	
	/**
	 * 机工价格
	 */
	@Column(name = "machinist_price")
    private Double machinistPrice;
	
	/**
	 * 绣花价格
	 */
	@Column(name = "embroider_price")
    private Double embroiderPrice;
	
	/**
	 * 针工价格
	 */
	@Column(name = "needlework_price")
    private Double needleworkPrice;
	
	/**
	 * 内外包装和出入库的价格
	 */
	@Column(name = "pack_price")
    private Double packPrice;
	
	/**
	 *预计运费价格
	 */
	@Column(name = "freight_price")
    private Double freightPrice;
	

	/**
	 * 是否含开票
	 */
	@Column(name = "invoice")
	private Integer invoice;
	
	/**
	 * 综合税负加所得税负
	 */
	@Column(name = "taxIncidence")
	private Double taxIncidence;
	
	/**
	 * 剩余到手的
	 */
	@Column(name = "surplus")
	private Double surplus;
	
	/**
	 * 预算成本
	 */
	@Column(name = "budget")
	private Double budget ;
	
	/**
	 * 预算成本加价率
	 */
	@Column(name = "budgetRate")
	private Double  budgetRate;
	
	/**
	 * 实战成本
	 */
	@Column(name = "actualCombat")
	private Double actualCombat;
	
	/**
	 * 实战成本加价率
	 */
	@Column(name = "actualCombatRate")
	private Double actualCombatRate;


	public Double getCutPartsPrice() {
		return cutPartsPrice;
	}

	public void setCutPartsPrice(Double cutPartsPrice) {
		this.cutPartsPrice = cutPartsPrice;
	}

	public Double getOtherCutPartsPrice() {
		return otherCutPartsPrice;
	}

	public void setOtherCutPartsPrice(Double otherCutPartsPrice) {
		this.otherCutPartsPrice = otherCutPartsPrice;
	}

	public Double getCutPrice() {
		return cutPrice;
	}

	public void setCutPrice(Double cutPrice) {
		this.cutPrice = cutPrice;
	}

	public Double getMachinistPrice() {
		return machinistPrice;
	}

	public void setMachinistPrice(Double machinistPrice) {
		this.machinistPrice = machinistPrice;
	}

	public Double getEmbroiderPrice() {
		return embroiderPrice;
	}

	public void setEmbroiderPrice(Double embroiderPrice) {
		this.embroiderPrice = embroiderPrice;
	}

	public Double getNeedleworkPrice() {
		return needleworkPrice;
	}

	public void setNeedleworkPrice(Double needleworkPrice) {
		this.needleworkPrice = needleworkPrice;
	}

	public Double getPackPrice() {
		return packPrice;
	}

	public void setPackPrice(Double packPrice) {
		this.packPrice = packPrice;
	}

	public Double getFreightPrice() {
		return freightPrice;
	}

	public void setFreightPrice(Double freightPrice) {
		this.freightPrice = freightPrice;
	}

	public Integer getInvoice() {
		return invoice;
	}

	public void setInvoice(Integer invoice) {
		this.invoice = invoice;
	}

	public Double getTaxIncidence() {
		return taxIncidence;
	}

	public void setTaxIncidence(Double taxIncidence) {
		this.taxIncidence = taxIncidence;
	}

	public Double getSurplus() {
		return surplus;
	}

	public void setSurplus(Double surplus) {
		this.surplus = surplus;
	}

	public Double getBudget() {
		return budget;
	}

	public void setBudget(Double budget) {
		this.budget = budget;
	}

	public Double getBudgetRate() {
		return budgetRate;
	}

	public void setBudgetRate(Double budgetRate) {
		this.budgetRate = budgetRate;
	}

	public Double getActualCombat() {
		return actualCombat;
	}

	public void setActualCombat(Double actualCombat) {
		this.actualCombat = actualCombat;
	}

	public Double getActualCombatRate() {
		return actualCombatRate;
	}

	public void setActualCombatRate(Double actualCombatRate) {
		this.actualCombatRate = actualCombatRate;
	}
	
	
	
}
