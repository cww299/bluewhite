package com.bluewhite.product.primecost.tailor.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.product.primecostbasedata.entity.BaseOne;
import com.bluewhite.product.primecostbasedata.entity.BaseThree;

/**
 * 裁剪页面
 * 
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_product_tailor")
public class Tailor extends BaseEntity<Long> {

	/**
	 * 产品id
	 */
	@Column(name = "product_id")
	private Long productId;

	/**
	 * 裁片id
	 */
	@Column(name = "cutParts_id")
	private Long cutPartsId;

	/**
	 * 裁剪类型页面id
	 */
	@Column(name = "ordinaryLaser_id")
	private Long ordinaryLaserId;

	/**
	 * 手选裁剪方式id
	 */
	@Column(name = "tailor_Type_id")
	private Long tailorTypeId;
	
	/**
	 * 裁剪方式
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tailor_Type_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseOne tailorType;
	
	/**
	 * 绣花id
	 */
	@Column(name = "embroidery_id")
	private Long embroideryId;

	/**
	 * 裁剪部位
	 */
	@Column(name = "tailor_name")
	private String tailorName;

	/**
	 * 裁剪片数
	 */
	@Column(name = "tailor_number")
	private Integer tailorNumber;

	/**
	 * 手选该裁片的平方M
	 */
	@Column(name = "tailor_size_id")
	private Long tailorSizeId;
	
	/**
	 * 裁片的平方M
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tailor_size_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseThree tailorSize;

	/**
	 * 得到理论(市场反馈）含管理价值
	 */
	@Column(name = "manage_price")
	private Double managePrice;

	/**
	 * 得到实验推算价格
	 */
	@Column(name = "experiment_price")
	private Double experimentPrice;

	/**
	 * 市场价与实推价比
	 */
	@Column(name = "rate_price")
	private Double ratePrice;
	
	/**
	 * 选择单个入成本价格(1.得到理论(市场反馈）含管理价值   2.得到实验推算价格 )
	 */
	@Column(name = "cost_price_select")
	private Integer costPriceSelect;

	/**
	 * 单个入成本价格
	 */
	@Column(name = "cost_price")
	private Double costPrice;

	/**
	 * 各单片比全套工价
	 */
	@Column(name = "scale_material")
	private Double scaleMaterial;

	/**
	 * 物料压价
	 */
	@Column(name = "price_down")
	private Double priceDown;

	/**
	 * 不含绣花环节的为机工压价
	 */
	@Column(name = "no_embroider_price_down")
	private Double noeMbroiderPriceDown;

	/**
	 * 含绣花环节的为机工压价
	 */
	@Column(name = "embroider_price_down")
	private Double embroiderPriceDown;

	/**
	 * 为机工准备的压价
	 */
	@Column(name = "machinist_price_down")
	private Double machinistPriceDown;

	/**
	 * 单只裁剪价格
	 */
	@Transient
	private Double oneCutPrice;
	
	/**
     * 批量产品数量或模拟批量数
     */
	@Transient
    private Integer number;
	
	/**
     * 总入成本价格
     */
	@Transient
    private Double allCostPrice;

    /**
     * 当批裁剪片数
     */
	@Transient
    private Integer bacthTailorNumber;

	public BaseOne getTailorType() {
		return tailorType;
	}

	public void setTailorType(BaseOne tailorType) {
		this.tailorType = tailorType;
	}

	public Integer getCostPriceSelect() {
		return costPriceSelect;
	}

	public void setCostPriceSelect(Integer costPriceSelect) {
		this.costPriceSelect = costPriceSelect;
	}

	public Long getTailorSizeId() {
		return tailorSizeId;
	}

	public void setTailorSizeId(Long tailorSizeId) {
		this.tailorSizeId = tailorSizeId;
	}

	public BaseThree getTailorSize() {
		return tailorSize;
	}

	public void setTailorSize(BaseThree tailorSize) {
		this.tailorSize = tailorSize;
	}

	public Long getEmbroideryId() {
		return embroideryId;
	}

	public void setEmbroideryId(Long embroideryId) {
		this.embroideryId = embroideryId;
	}

	public Double getOneCutPrice() {
		return oneCutPrice;
	}

	public void setOneCutPrice(Double oneCutPrice) {
		this.oneCutPrice = oneCutPrice;
	}

	public Long getCutPartsId() {
		return cutPartsId;
	}

	public void setCutPartsId(Long cutPartsId) {
		this.cutPartsId = cutPartsId;
	}

	public Long getOrdinaryLaserId() {
		return ordinaryLaserId;
	}

	public void setOrdinaryLaserId(Long ordinaryLaserId) {
		this.ordinaryLaserId = ordinaryLaserId;
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

	public String getTailorName() {
		return tailorName;
	}

	public void setTailorName(String tailorName) {
		this.tailorName = tailorName;
	}

	public Integer getTailorNumber() {
		return tailorNumber;
	}

	public void setTailorNumber(Integer tailorNumber) {
		this.tailorNumber = tailorNumber;
	}

	public Integer getBacthTailorNumber() {
		return bacthTailorNumber;
	}

	public void setBacthTailorNumber(Integer bacthTailorNumber) {
		this.bacthTailorNumber = bacthTailorNumber;
	}

	public Double getManagePrice() {
		return managePrice;
	}

	public void setManagePrice(Double managePrice) {
		this.managePrice = managePrice;
	}

	public Double getExperimentPrice() {
		return experimentPrice;
	}

	public void setExperimentPrice(Double experimentPrice) {
		this.experimentPrice = experimentPrice;
	}

	public Double getRatePrice() {
		return ratePrice;
	}

	public void setRatePrice(Double ratePrice) {
		this.ratePrice = ratePrice;
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

	public Double getNoeMbroiderPriceDown() {
		return noeMbroiderPriceDown;
	}

	public void setNoeMbroiderPriceDown(Double noeMbroiderPriceDown) {
		this.noeMbroiderPriceDown = noeMbroiderPriceDown;
	}

	public Double getEmbroiderPriceDown() {
		return embroiderPriceDown;
	}

	public void setEmbroiderPriceDown(Double embroiderPriceDown) {
		this.embroiderPriceDown = embroiderPriceDown;
	}

	public Double getMachinistPriceDown() {
		return machinistPriceDown;
	}

	public void setMachinistPriceDown(Double machinistPriceDown) {
		this.machinistPriceDown = machinistPriceDown;
	}

	public Long getTailorTypeId() {
		return tailorTypeId;
	}

	public void setTailorTypeId(Long tailorTypeId) {
		this.tailorTypeId = tailorTypeId;
	}

}
