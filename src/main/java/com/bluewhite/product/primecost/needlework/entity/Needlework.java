package com.bluewhite.product.primecost.needlework.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;

/**
 * 针工页面
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_product_needlework")
public class Needlework extends BaseEntity<Long>{
	
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
	 * 针工步骤名称
	 */
	@Column(name = "needlework_name")
	private String needleworkName;
	
	
	/**
	 * 
	 * (通过基础数据表1获取相应的类型)
	 * 请选择在该工序下的分类（一定要手选下，不然会覆盖公式）
	 */
	@Column(name = "classify")
	private String classify;
	
	/**
	 * (通过基础数据表1获取相应的类型的秒数)
	 * 自动跳出设定秒数等同于该步骤用时
	 */
	@Column(name = "seconds")
	private Double seconds;
	
	/**
	 * 对工序大，辅工定性选择↓
	 */
	@Column(name = "help_work")
	private String helpWork;
	
	/**
	 * 对工序大，辅工定性价格
	 */
	@Column(name = "help_work_price")
	private Double helpWorkPrice;
	
	
	/**
	 * 二次手动调整价格（有需要填写）
	 */
	@Column(name = "secondary_price")
	private Double secondaryPrice;
	
	/**
	 * 单工序单只时间
	 */
	@Column(name = "single_time")
	private Double singleTime;
	
	/**
	 * 单工序单只放快手时间
	 */
	@Column(name = "single_quick_time")
	private Double singleQuickTime;
	
	/**
	 * 工价/单只（含快手)
	 */
	@Column(name = "labour_cost")
	private Double labourCost;
	
	/**
	 * 设备折旧和房水电费
	 */
	@Column(name = "equipment_price")
    private Double equipmentPrice;	
	/**
	 * 管理人员费用
	 */
	@Column(name = "administrative_staff")
    private Double administrativeAtaff;	
	
	/**
	 * 针工工序费用
	 */
	@Column(name = "needlework_price")
    private Double needleworkPrice;
	
	
	/**
	 *入成本批量价格
	 */
	@Column(name = "all_cost_price")
    private Double allCostPrice;
	
	
	/**
	 * 物料压价
	 */
	@Column(name = "price_down")
    private Double priceDown;
	
	
	

	public Double getHelpWorkPrice() {
		return helpWorkPrice;
	}

	public void setHelpWorkPrice(Double helpWorkPrice) {
		this.helpWorkPrice = helpWorkPrice;
	}

	public Double getAllCostPrice() {
		return allCostPrice;
	}

	public void setAllCostPrice(Double allCostPrice) {
		this.allCostPrice = allCostPrice;
	}

	public Double getPriceDown() {
		return priceDown;
	}

	public void setPriceDown(Double priceDown) {
		this.priceDown = priceDown;
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

	public String getNeedleworkName() {
		return needleworkName;
	}

	public void setNeedleworkName(String needleworkName) {
		this.needleworkName = needleworkName;
	}

	public String getClassify() {
		return classify;
	}

	public void setClassify(String classify) {
		this.classify = classify;
	}

	public Double getSeconds() {
		return seconds;
	}

	public void setSeconds(Double seconds) {
		this.seconds = seconds;
	}

	public String getHelpWork() {
		return helpWork;
	}

	public void setHelpWork(String helpWork) {
		this.helpWork = helpWork;
	}

	public Double getSecondaryPrice() {
		return secondaryPrice;
	}

	public void setSecondaryPrice(Double secondaryPrice) {
		this.secondaryPrice = secondaryPrice;
	}

	public Double getSingleTime() {
		return singleTime;
	}

	public void setSingleTime(Double singleTime) {
		this.singleTime = singleTime;
	}

	public Double getSingleQuickTime() {
		return singleQuickTime;
	}

	public void setSingleQuickTime(Double singleQuickTime) {
		this.singleQuickTime = singleQuickTime;
	}

	public Double getLabourCost() {
		return labourCost;
	}

	public void setLabourCost(Double labourCost) {
		this.labourCost = labourCost;
	}

	public Double getEquipmentPrice() {
		return equipmentPrice;
	}

	public void setEquipmentPrice(Double equipmentPrice) {
		this.equipmentPrice = equipmentPrice;
	}

	public Double getAdministrativeAtaff() {
		return administrativeAtaff;
	}

	public void setAdministrativeAtaff(Double administrativeAtaff) {
		this.administrativeAtaff = administrativeAtaff;
	}

	public Double getNeedleworkPrice() {
		return needleworkPrice;
	}

	public void setNeedleworkPrice(Double needleworkPrice) {
		this.needleworkPrice = needleworkPrice;
	}	
	
	
	
	
	

}
