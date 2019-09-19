package com.bluewhite.product.primecost.primecost.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.product.product.entity.Product;

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
	 * 产品
	 */
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "productId" ,referencedColumnName = "id", insertable = false, updatable = false)
	private Product product; 
	
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
	 * 实战单只成本
	 */
	@Column(name = "one_actual_prime_cost")
	private Double oneaAtualPrimeCost;
	 
	
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
	
	
	/****当下生产放价填写****/
	/**
	 * 面料价格(含复合物料和加工费）(除面料以外的其他物料价格)
	 */
	@Column(name = "cut_parts_price_pricing")
    private Double cutPartsPricePricing;
	
	
	/**
	 * 裁剪价格
	 */
	@Column(name = "cut_price_pricing")
    private Double cutPricePricing;
	

	
	/**
	 * 机工价格
	 */
	@Column(name = "machinist_price_pricing")
    private Double machinistPricePricing;
	

	
	/**
	 * 绣花价格
	 */
	@Column(name = "embroider_price_pricing")
    private Double embroiderPricePricing;
	
	
	/**
	 * 针工价格
	 */
	@Column(name = "needlework_price_pricing")
    private Double needleworkPricePricing;
	
	
	/**
	 * 内外包装和出入库的价格
	 */
	@Column(name = "pack_price_pricing")
    private Double packPricePricing;
	
	/**
	 * 预计运费价格
	 */
	@Column(name = "freight_price_pricing")
    private Double freightPricePricing;
	
	/****当下生产放价填写****/
	
	
	
	
	
	/**
	 * 面料价格(含复合物料和加工费）(需要支付开票点)
	 */
	@Column(name = "cut_parts_price_invoice")
    private Double cutPartsPriceInvoice;
	
	
	/**
	 * 除面料以外的其他物料价格(需要支付开票点)
	 */
	@Column(name = "other_cut_parts_price_invoice")
    private Double otherCutPartsPriceInvoice;
	
	
	/**
	 * 裁剪价格(需要支付开票点)
	 */
	@Column(name = "cut_price_invoice")
    private Double cutPriceInvoice;
	
	
	/**
	 * 机工价格(需要支付开票点)
	 */
	@Column(name = "machinist_price_invoice")
    private Double machinistPriceInvoice;
	
	
	/**
	 * 绣花价格(需要支付开票点)
	 */
	@Column(name = "embroider_price_invoice")
    private Double embroiderPriceInvoice;
	/**
	 * 针工价格(需要支付开票点)
	 */
	@Column(name = "needlework_price_invoice")
    private Double needleworkPriceInvoice;
	
	
	/**
	 * 内外包装和出入库的价格(需要支付开票点)
	 */
	@Column(name = "pack_price_invoice")
    private Double packPriceInvoice;
	
	
	
	/**
	 * 预计运费价格(需要支付开票点)
	 */
	@Column(name = "freight_price_invoice")
    private Double freightPriceInvoice;
	
	
	
	/**
	 * (0=否，1=是)默认是否
	 * 面料价格(含复合物料和加工费）(是否可开票)
	 */
	@Column(name = "cut_parts_invoice")
    private Integer cutPartsInvoice;
	
	
	/**
	 * 除面料以外的其他物料价格(是否可开票)
	 */
	@Column(name = "other_cut_parts_invoice")
    private Integer otherCutPartsInvoice;
	
	
	/**
	 * 裁剪价格(是否可开票))
	 */
	@Column(name = "cut_invoice")
    private Integer cutInvoice;
	
	
	/**
	 * 机工价格(是否可开票)
	 */
	@Column(name = "machinist_invoice")
    private Integer machinistInvoice;
	
	
	/**
	 * 绣花价格(是否可开票)
	 */
	@Column(name = "embroider_invoice")
    private Integer embroiderInvoice;
	/**
	 * 针工价格(是否可开票)
	 */
	@Column(name = "needlework_invoice")
    private Integer needleworkInvoice;
	
	
	/**
	 * 内外包装和出入库的价格(是否可开票)
	 */
	@Column(name = "pack_invoice")
    private Integer packInvoice;
	
	
	/**
	 * 预计运费价格(是否可开票)
	 */
	@Column(name = "freight_invoice")
    private Integer freightInvoice;
	
	
	
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

	/**
	 * 是否有返点↓
	 */
	@Column(name = "rebate")
	private Integer rebate;
	
	/**
	 * 返点比↓
	 */
	@Column(name = "rebate_rate")
	private Double rebateRate;
	
	/**
	 * 是否有版权点↓
	 */
	@Column(name = "copyright")
	private Integer copyright;
	
	/**
	 * 版权点比↓
	 */
	@Column(name = "copyright_rate")
	private Double copyrightRate;
	
	
	

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Double getOneaAtualPrimeCost() {
		return oneaAtualPrimeCost;
	}

	public void setOneaAtualPrimeCost(Double oneaAtualPrimeCost) {
		this.oneaAtualPrimeCost = oneaAtualPrimeCost;
	}

	public Integer getRebate() {
		return rebate;
	}

	public void setRebate(Integer rebate) {
		this.rebate = rebate;
	}

	public Double getRebateRate() {
		return rebateRate;
	}

	public void setRebateRate(Double rebateRate) {
		this.rebateRate = rebateRate;
	}

	public Integer getCopyright() {
		return copyright;
	}

	public void setCopyright(Integer copyright) {
		this.copyright = copyright;
	}

	public Double getCopyrightRate() {
		return copyrightRate;
	}

	public void setCopyrightRate(Double copyrightRate) {
		this.copyrightRate = copyrightRate;
	}

	public Double getCutPartsPriceInvoice() {
		return cutPartsPriceInvoice;
	}

	public void setCutPartsPriceInvoice(Double cutPartsPriceInvoice) {
		this.cutPartsPriceInvoice = cutPartsPriceInvoice;
	}

	public Double getOtherCutPartsPriceInvoice() {
		return otherCutPartsPriceInvoice;
	}

	public void setOtherCutPartsPriceInvoice(Double otherCutPartsPriceInvoice) {
		this.otherCutPartsPriceInvoice = otherCutPartsPriceInvoice;
	}

	public Double getCutPriceInvoice() {
		return cutPriceInvoice;
	}

	public void setCutPriceInvoice(Double cutPriceInvoice) {
		this.cutPriceInvoice = cutPriceInvoice;
	}

	public Double getMachinistPriceInvoice() {
		return machinistPriceInvoice;
	}

	public void setMachinistPriceInvoice(Double machinistPriceInvoice) {
		this.machinistPriceInvoice = machinistPriceInvoice;
	}

	public Double getEmbroiderPriceInvoice() {
		return embroiderPriceInvoice;
	}

	public void setEmbroiderPriceInvoice(Double embroiderPriceInvoice) {
		this.embroiderPriceInvoice = embroiderPriceInvoice;
	}

	public Double getNeedleworkPriceInvoice() {
		return needleworkPriceInvoice;
	}

	public void setNeedleworkPriceInvoice(Double needleworkPriceInvoice) {
		this.needleworkPriceInvoice = needleworkPriceInvoice;
	}

	public Double getPackPriceInvoice() {
		return packPriceInvoice;
	}

	public void setPackPriceInvoice(Double packPriceInvoice) {
		this.packPriceInvoice = packPriceInvoice;
	}

	public Double getFreightPriceInvoice() {
		return freightPriceInvoice;
	}

	public void setFreightPriceInvoice(Double freightPriceInvoice) {
		this.freightPriceInvoice = freightPriceInvoice;
	}



	public Integer getCutPartsInvoice() {
		return cutPartsInvoice;
	}

	public void setCutPartsInvoice(Integer cutPartsInvoice) {
		this.cutPartsInvoice = cutPartsInvoice;
	}

	public Integer getOtherCutPartsInvoice() {
		return otherCutPartsInvoice;
	}

	public void setOtherCutPartsInvoice(Integer otherCutPartsInvoice) {
		this.otherCutPartsInvoice = otherCutPartsInvoice;
	}

	public Integer getCutInvoice() {
		return cutInvoice;
	}

	public void setCutInvoice(Integer cutInvoice) {
		this.cutInvoice = cutInvoice;
	}

	public Integer getMachinistInvoice() {
		return machinistInvoice;
	}

	public void setMachinistInvoice(Integer machinistInvoice) {
		this.machinistInvoice = machinistInvoice;
	}

	public Integer getEmbroiderInvoice() {
		return embroiderInvoice;
	}

	public void setEmbroiderInvoice(Integer embroiderInvoice) {
		this.embroiderInvoice = embroiderInvoice;
	}

	public Integer getNeedleworkInvoice() {
		return needleworkInvoice;
	}

	public void setNeedleworkInvoice(Integer needleworkInvoice) {
		this.needleworkInvoice = needleworkInvoice;
	}

	public Integer getPackInvoice() {
		return packInvoice;
	}

	public void setPackInvoice(Integer packInvoice) {
		this.packInvoice = packInvoice;
	}

	public Integer getFreightInvoice() {
		return freightInvoice;
	}

	public void setFreightInvoice(Integer freightInvoice) {
		this.freightInvoice = freightInvoice;
	}

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

	public Double getCutPartsPricePricing() {
		return cutPartsPricePricing;
	}

	public void setCutPartsPricePricing(Double cutPartsPricePricing) {
		this.cutPartsPricePricing = cutPartsPricePricing;
	}

	public Double getCutPricePricing() {
		return cutPricePricing;
	}

	public void setCutPricePricing(Double cutPricePricing) {
		this.cutPricePricing = cutPricePricing;
	}

	public Double getMachinistPricePricing() {
		return machinistPricePricing;
	}

	public void setMachinistPricePricing(Double machinistPricePricing) {
		this.machinistPricePricing = machinistPricePricing;
	}

	public Double getEmbroiderPricePricing() {
		return embroiderPricePricing;
	}

	public void setEmbroiderPricePricing(Double embroiderPricePricing) {
		this.embroiderPricePricing = embroiderPricePricing;
	}

	public Double getNeedleworkPricePricing() {
		return needleworkPricePricing;
	}

	public void setNeedleworkPricePricing(Double needleworkPricePricing) {
		this.needleworkPricePricing = needleworkPricePricing;
	}

	public Double getPackPricePricing() {
		return packPricePricing;
	}

	public void setPackPricePricing(Double packPricePricing) {
		this.packPricePricing = packPricePricing;
	}

	public Double getFreightPricePricing() {
		return freightPricePricing;
	}

	public void setFreightPricePricing(Double freightPricePricing) {
		this.freightPricePricing = freightPricePricing;
	}
	
	
	
	
}
