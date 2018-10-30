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
	 * 批量产品数量或模拟批量数
	 */
	@Column(name = "number")
	private Integer number;
	
	/**
	 * 调整外发产量
	 */
	@Column(name = "adjust_number")
	private Integer adjustNumber;
	

	/**
	 * 产品id
	 */
	@Column(name = "product_id")
	private Long productId;
	
	/**
	 * 批成本
	 */
	@Column(name = "bacth_prime_cost")
	private Double bacthPrimeCost;
	
	/**
	 * 单只成本
	 */
	@Column(name = "one_prime_cost")
	private Double onePrimeCost;
	
	/**
	 * 面料价格(含复合物料和加工费）
	 */
	@Column(name = "cut_parts_price")
    private Double cutPartsPrice;
	
	/**
	 * 单只面料价格(含复合物料和加工费）
	 */
	@Column(name = "one_cut_parts_price")
    private Double oneCutPartsPrice;
	
	/**
	 * 除面料以外的其他物料价格
	 */
	@Column(name = "other_cut_parts_price")
    private Double otherCutPartsPrice;
	
	/**
	 * 单只除面料以外的其他物料价格
	 */
	@Column(name = "one_other_cut_parts_price")
    private Double oneOtherCutPartsPrice;
	
	/**
	 * 裁剪价格
	 */
	@Column(name = "cut_price")
    private Double cutPrice;
	
	/**
	 * 单只裁剪价格
	 */
	@Column(name = "one_cut_price")
    private Double oneCutPrice;
	
	
	/**
	 * 机工价格
	 */
	@Column(name = "machinist_price")
    private Double machinistPrice;
	
	/**
	 * 单只机工价格
	 */
	@Column(name = "one_machinist_price")
    private Double oneMachinistPrice;
	
	/**
	 * 绣花价格
	 */
	@Column(name = "embroider_price")
    private Double embroiderPrice;
	
	/**
	 * 单只绣花价格
	 */
	@Column(name = "one_embroider_price")
    private Double oneEmbroiderPrice;
	
	/**
	 * 针工价格
	 */
	@Column(name = "needlework_price")
    private Double needleworkPrice;
	
	/**
	 * 单只针工价格
	 */
	@Column(name = "one_needlework_price")
    private Double oneNeedleworkPrice;
	
	/**
	 * 内外包装和出入库的价格
	 */
	@Column(name = "pack_price")
    private Double packPrice;
	
	/**
	 * 单只内外包装和出入库的价格
	 */
	@Column(name = "one_pack_price")
    private Double onePackPrice;
	
	
	/**
	 * 预计运费价格
	 */
	@Column(name = "freight_price")
    private Double freightPrice;
	
	/**
	 * 单只预计运费价格
	 */
	@Column(name = "one_freight_price")
    private Double oneFreightPrice;

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
	
	/**
	 * 目前售价
	 */
	@Column(name = "sale")
	private Double sale;
	
	
	/**
	 * 付上游开票点
	 */
	@Column(name = "upstream")
	private Double upstream;
	
	/**
	 * 预计多付国家的
	 */
	@Column(name = "expect_state")
	private Double expectState;
	
	/**
	 * 付国家的
	 */
	@Column(name = "state")
	private Double state;
	
	/**
	 * 考虑多付国家的不付需要的进项票点
	 */
	@Column(name = "no_state")
	private Double noState;
	
	/**
	 * 付返点和版权点
	 */
	@Column(name = "recidivate")
	private Double recidivate;
	
	/**
	 * 付运费
	 */
	@Column(name = "freight")
	private Double freight;
	
	/**
	 * 为目前得出综合税负加所得税负填写↓
	 */
	@Column(name = "taxes")
	private Double taxes;
	
	/**
	 * 实际加价率
	 */
	@Column(name = "make_rate")
	private Double makeRate;
	
	/**
	 * 目前综合税负加所得税负比
	 */
	@Column(name = "taxes_rate")
	private Double taxesRate;

	
	

	public Double getSale() {
		return sale;
	}

	public void setSale(Double sale) {
		this.sale = sale;
	}

	public Double getUpstream() {
		return upstream;
	}

	public void setUpstream(Double upstream) {
		this.upstream = upstream;
	}

	public Double getExpectState() {
		return expectState;
	}

	public void setExpectState(Double expectState) {
		this.expectState = expectState;
	}

	public Double getState() {
		return state;
	}

	public void setState(Double state) {
		this.state = state;
	}

	public Double getNoState() {
		return noState;
	}

	public void setNoState(Double noState) {
		this.noState = noState;
	}

	public Double getRecidivate() {
		return recidivate;
	}

	public void setRecidivate(Double recidivate) {
		this.recidivate = recidivate;
	}

	public Double getFreight() {
		return freight;
	}

	public void setFreight(Double freight) {
		this.freight = freight;
	}

	public Double getTaxes() {
		return taxes;
	}

	public void setTaxes(Double taxes) {
		this.taxes = taxes;
	}

	public Double getMakeRate() {
		return makeRate;
	}

	public void setMakeRate(Double makeRate) {
		this.makeRate = makeRate;
	}

	public Double getTaxesRate() {
		return taxesRate;
	}

	public void setTaxesRate(Double taxesRate) {
		this.taxesRate = taxesRate;
	}

	public Double getOneFreightPrice() {
		return oneFreightPrice;
	}

	public void setOneFreightPrice(Double oneFreightPrice) {
		this.oneFreightPrice = oneFreightPrice;
	}

	public Integer getAdjustNumber() {
		return adjustNumber;
	}

	public void setAdjustNumber(Integer adjustNumber) {
		this.adjustNumber = adjustNumber;
	}

	public Double getBacthPrimeCost() {
		return bacthPrimeCost;
	}

	public void setBacthPrimeCost(Double bacthPrimeCost) {
		this.bacthPrimeCost = bacthPrimeCost;
	}

	public Double getOnePrimeCost() {
		return onePrimeCost;
	}

	public void setOnePrimeCost(Double onePrimeCost) {
		this.onePrimeCost = onePrimeCost;
	}

	public Double getOneCutPartsPrice() {
		return oneCutPartsPrice;
	}

	public void setOneCutPartsPrice(Double oneCutPartsPrice) {
		this.oneCutPartsPrice = oneCutPartsPrice;
	}

	public Double getOneOtherCutPartsPrice() {
		return oneOtherCutPartsPrice;
	}

	public void setOneOtherCutPartsPrice(Double oneOtherCutPartsPrice) {
		this.oneOtherCutPartsPrice = oneOtherCutPartsPrice;
	}

	public Double getOneCutPrice() {
		return oneCutPrice;
	}

	public void setOneCutPrice(Double oneCutPrice) {
		this.oneCutPrice = oneCutPrice;
	}

	public Double getOneMachinistPrice() {
		return oneMachinistPrice;
	}

	public void setOneMachinistPrice(Double oneMachinistPrice) {
		this.oneMachinistPrice = oneMachinistPrice;
	}

	public Double getOneEmbroiderPrice() {
		return oneEmbroiderPrice;
	}

	public void setOneEmbroiderPrice(Double oneEmbroiderPrice) {
		this.oneEmbroiderPrice = oneEmbroiderPrice;
	}

	public Double getOneNeedleworkPrice() {
		return oneNeedleworkPrice;
	}

	public void setOneNeedleworkPrice(Double oneNeedleworkPrice) {
		this.oneNeedleworkPrice = oneNeedleworkPrice;
	}

	public Double getOnePackPrice() {
		return onePackPrice;
	}

	public void setOnePackPrice(Double onePackPrice) {
		this.onePackPrice = onePackPrice;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

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

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
	
	
	
}
