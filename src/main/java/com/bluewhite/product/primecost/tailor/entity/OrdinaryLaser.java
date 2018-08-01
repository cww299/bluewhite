package com.bluewhite.product.primecost.tailor.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
/**
 * 裁剪普通激光
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_ordinary_laser")
public class OrdinaryLaser extends BaseEntity<Long>{
	
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
	@Column(name = "tailor_Type")
    private String tailorType;
	
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
	 * 激光停顿点
	 */
	@Column(name = "stall_point")
    private Double stallPoint;	
	
	/**
	 * 单双激光头(单 = 1，双 =2， 0.5=3， 1=4)
	 */
	@Column(name = "single_double")
    private Integer singleDouble;	
	
	/**
	 * 捡片时间
	 */
	@Column(name = "time")
    private Double time;	
	
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
	 * 其他未考虑时间2
	 */
	@Column(name = "other_time_three")
    private Double otherTimeThree;	
	
	/**
	 * 拉布时间
	 */
	@Column(name = "rabb_time")
    private Double rabbTime;	
	
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
	 * 
	 */
	@Column(name = "type")
    private String type;
	
	
	

	public Double getOtherTimeThree() {
		return otherTimeThree;
	}

	public void setOtherTimeThree(Double otherTimeThree) {
		this.otherTimeThree = otherTimeThree;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
