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
/**
 * (裁剪普通激光,绣花定位激光，冲床，电烫，电推，手工剪刀）
 * 不同的裁剪方式，获取不同的成本价格
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_product_ordinary_laser")
public class OrdinaryLaser extends BaseEntity<Long>{
	
	/**
	 * 产品id
	 */
	@Column(name = "product_id")
    private Long productId;
	
	/**
	 * 裁剪页面id
	 */
	@Column(name = "tailor_id")
    private Long tailorId;
	
	/**
	 * 批量产品数量或模拟批量数
	 */
	@Column(name = "number")
	private Integer number;
	
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
	 * 该裁片的平方M
	 */
	@Column(name = "tailor_size")
    private Double tailorSize;
	
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
	 *得到理论(市场反馈）含管理价值
	 */
	@Column(name = "manage_price")
    private Double managePrice;	
	
	/**
	 * 裁片周长/CM(≈)
	 */
	@Column(name = "perimeter")
    private Double perimeter;	
	
	/**
	 * 叠片层数
	 */
	@Column(name = "layer_number ")
    private Double layerNumber ;
	
	/**
	 * 选择一板排版片数
	 */
	@Column(name = "typesetting_number ")
    private Double typesettingNumber ;
	
	
	/**
	 * 激光停顿点
	 */
	@Column(name = "stall_point")
    private Double stallPoint;	
	
	/**
	 * 单双激光头(单 = 1，双 =2)
	 */
	@Column(name = "single_double")
    private Integer singleDouble;	
	
	/**
	 * 捡片时间
	 */
	@Column(name = "time")
    private Double time;	
	
	/**
	 * 绣切的撕片时间
	 */
	@Column(name = " embroider_time")
    private Double  embroiderTime;	
	
	
	/**
	 * 其他未考虑时间1
	 */
	@Column(name = "other_time_one")
    private Double otherTimeOne;	
	
	/**
	 * 其他未考虑时间2
	 */
	@Column(name = "other_time_two")
    private Double otherTimeTwo;
	
	/**
	 * 其他未考虑时间3
	 */
	@Column(name = "other_time_three")
    private Double otherTimeThree;	
	
	/**
	 * 拉布时间
	 */
	@Column(name = "rabb_time")
    private Double rabbTime;	
	
	/**
	 * 叠布秒数（含快手)
	 */
	@Column(name = "overlapped_seconds")
    private Double  overlappedSeconds;	
	
	/**
	 * 冲压秒数（含快手)
	 */
	@Column(name = "punching_seconds ")
    private Double punchingSeconds ;
	
	
	/**
	 * 电推秒数（含快手)
	 */
	@Column(name = "electric_seconds ")
    private Double electricSeconds ;
	
	
	/**
	 * 单片激光需要用净时
	 */
	@Column(name = "single_laser_time")
    private Double singleLaserTime;	
	
	/**
	 * 单片激光放快手时间
	 */
	@Column(name = "single_laser_hand_time")
    private Double singleLaserHandTime;	
	
	
	/**
	 * 电烫秒数（含快手)
	 */
	@Column(name = "perm_seconds ")
    private Double permSeconds;
	
	/**
	 * 撕片秒数（含快手)
	 */
	@Column(name = "tearing_seconds")
    private Double tearingSeconds;	
	
	/**
	 * 手工秒数（含快手)
	 */
	@Column(name = "manual_seconds")
    private Double manualSeconds;	
	
	/**
	 * 电烫工价（含快手)
	 */
	@Column(name = "perm_price")
    private Double permPrice;	
	
	/**
	 * 撕片工价
	 */
	@Column(name = "tearing_price")
    private Double tearingPrice;	
	
	/**
	 * 工价（含快手)
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
	 * 普通激光切割该裁片费用
	 */
	@Column(name = "stall_price")
    private Double stallPrice;
	
	
	
	/**
	 * 叠片数量（冲床）
	 * @return
	 */
	@Column(name = "lamination")
    private Integer lamination;
	
	
	/**
	 * 是否保存
	 * @return
	 */
	@Transient
    private Integer save;
	
	/**
	 * 单只裁剪价格
	 */
	@Transient
    private Double oneCutPrice;
	
	

	public Double getOneCutPrice() {
		return oneCutPrice;
	}

	public void setOneCutPrice(Double oneCutPrice) {
		this.oneCutPrice = oneCutPrice;
	}

	public Integer getSave() {
		return save;
	}

	public void setSave(Integer save) {
		this.save = save;
	}

	public Double getManualSeconds() {
		return manualSeconds;
	}

	public void setManualSeconds(Double manualSeconds) {
		this.manualSeconds = manualSeconds;
	}

	public Double getElectricSeconds() {
		return electricSeconds;
	}

	public void setElectricSeconds(Double electricSeconds) {
		this.electricSeconds = electricSeconds;
	}

	public Double getTypesettingNumber() {
		return typesettingNumber;
	}

	public void setTypesettingNumber(Double typesettingNumber) {
		this.typesettingNumber = typesettingNumber;
	}

	public Double getPermSeconds() {
		return permSeconds;
	}

	public void setPermSeconds(Double permSeconds) {
		this.permSeconds = permSeconds;
	}

	public Double getTearingSeconds() {
		return tearingSeconds;
	}

	public void setTearingSeconds(Double tearingSeconds) {
		this.tearingSeconds = tearingSeconds;
	}

	public Double getPermPrice() {
		return permPrice;
	}

	public void setPermPrice(Double permPrice) {
		this.permPrice = permPrice;
	}

	public Double getTearingPrice() {
		return tearingPrice;
	}

	public void setTearingPrice(Double tearingPrice) {
		this.tearingPrice = tearingPrice;
	}

	public Long getTailorId() {
		return tailorId;
	}

	public void setTailorId(Long tailorId) {
		this.tailorId = tailorId;
	}

	public Double getOverlappedSeconds() {
		return overlappedSeconds;
	}

	public void setOverlappedSeconds(Double overlappedSeconds) {
		this.overlappedSeconds = overlappedSeconds;
	}

	public Double getPunchingSeconds() {
		return punchingSeconds;
	}

	public void setPunchingSeconds(Double punchingSeconds) {
		this.punchingSeconds = punchingSeconds;
	}

	public Double getLayerNumber() {
		return layerNumber;
	}

	public void setLayerNumber(Double layerNumber) {
		this.layerNumber = layerNumber;
	}

	public Double getEmbroiderTime() {
		return embroiderTime;
	}

	public void setEmbroiderTime(Double embroiderTime) {
		this.embroiderTime = embroiderTime;
	}

	public Integer getLamination() {
		return lamination;
	}

	public void setLamination(Integer lamination) {
		this.lamination = lamination;
	}

	public Double getOtherTimeThree() {
		return otherTimeThree;
	}

	public void setOtherTimeThree(Double otherTimeThree) {
		this.otherTimeThree = otherTimeThree;
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

	public Double getTailorSize() {
		return tailorSize;
	}

	public void setTailorSize(Double tailorSize) {
		this.tailorSize = tailorSize;
	}

	public Double getManagePrice() {
		return managePrice;
	}

	public void setManagePrice(Double managePrice) {
		this.managePrice = managePrice;
	}

	public Double getPerimeter() {
		return perimeter;
	}

	public void setPerimeter(Double perimeter) {
		this.perimeter = perimeter;
	}

	public Double getStallPoint() {
		return stallPoint;
	}

	public void setStallPoint(Double stallPoint) {
		this.stallPoint = stallPoint;
	}

	public Integer getSingleDouble() {
		return singleDouble;
	}

	public void setSingleDouble(Integer singleDouble) {
		this.singleDouble = singleDouble;
	}

	public Double getTime() {
		return time;
	}

	public void setTime(Double time) {
		this.time = time;
	}

	public Double getOtherTimeOne() {
		return otherTimeOne;
	}

	public void setOtherTimeOne(Double otherTimeOne) {
		this.otherTimeOne = otherTimeOne;
	}

	public Double getOtherTimeTwo() {
		return otherTimeTwo;
	}

	public void setOtherTimeTwo(Double otherTimeTwo) {
		this.otherTimeTwo = otherTimeTwo;
	}

	public Double getRabbTime() {
		return rabbTime;
	}

	public void setRabbTime(Double rabbTime) {
		this.rabbTime = rabbTime;
	}

	public Double getSingleLaserTime() {
		return singleLaserTime;
	}

	public void setSingleLaserTime(Double singleLaserTime) {
		this.singleLaserTime = singleLaserTime;
	}

	public Double getSingleLaserHandTime() {
		return singleLaserHandTime;
	}

	public void setSingleLaserHandTime(Double singleLaserHandTime) {
		this.singleLaserHandTime = singleLaserHandTime;
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

	public Double getStallPrice() {
		return stallPrice;
	}

	public void setStallPrice(Double stallPrice) {
		this.stallPrice = stallPrice;
	}

	public Long getTailorTypeId() {
		return tailorTypeId;
	}

	public void setTailorTypeId(Long tailorTypeId) {
		this.tailorTypeId = tailorTypeId;
	}	
	
	
	


}
