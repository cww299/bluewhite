package com.bluewhite.product.primecost.tailor.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;

/**
 * 裁剪页面
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_product_tailor")
public class Tailor extends BaseEntity<Long>{
	

	/**
	 * 产品id
	 */
	@Column(name = "product_id")
    private Long productId;
	
	
	/**
	 * 裁剪类型页面id
	 */
	@Column(name = "ordinaryLaser_id")
    private Long ordinaryLaserId;
	
	
	/**
	 * 批量产品数量或模拟批量数
	 */
	@Column(name = "number")
	private Integer number;
	
	
	
	/**
	 * 选择该样品的裁片id
	 */
	@Column(name = "base_id")
    private Long baseId;
	
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
	 * 当批片数
	 */
	@Column(name = "bacth_tailor_number")
    private Integer bacthTailorNumber;
	
	/**
	 * 手选该裁片的平方M
	 */
	@Column(name = "tailor_size")
    private Double tailorSize;
	
	/**
	 * 手选裁剪方式id
	 */
	@Column(name = "tailor_Type_id")
    private Long tailorTypeId;
	
	
	/**
	 * 手选裁剪方式
	 */
	@Column(name = "tailor_Type")
    private String tailorType;
	
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
	
	
	



	public Long getBaseId() {
		return baseId;
	}

	public void setBaseId(Long baseId) {
		this.baseId = baseId;
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

	public Double getTailorSize() {
		return tailorSize;
	}

	public void setTailorSize(Double tailorSize) {
		this.tailorSize = tailorSize;
	}

	public String getTailorType() {
		return tailorType;
	}

	public void setTailorType(String tailorType) {
		this.tailorType = tailorType;
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
