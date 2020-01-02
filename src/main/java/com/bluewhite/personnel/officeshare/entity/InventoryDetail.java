package com.bluewhite.personnel.officeshare.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.system.user.entity.User;

/**
 * 出入库明细
 * 
 * @author zhangliang
 *
 */
@Entity
@Table(name = "person_inventory_detail")
public class InventoryDetail extends BaseEntity<Long> {

	/**
	 * 后勤物品id
	 */
	@Column(name = "office_supplies_id")
	private Long officeSuppliesId;

	/**
	 * 后勤物品
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "office_supplies_id", referencedColumnName = "id", insertable = false, updatable = false)
	private OfficeSupplies OfficeSupplies;

	/**
	 * 出库入库（0,1）
	 */
	@Column(name = "flag")
	private Integer flag;

	/**
	 * 部门id
	 */
	@Column(name = "orgName_id")
	private Long orgNameId;

	/**
	 * 部门
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orgName_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseData orgName;

	/**
	 * 领取人id
	 */
	@Column(name = "user_id")
	private Long userId;

	/**
	 * 领取人
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
	private User user;

	/**
	 * 出库（入库）时间
	 */
	@Column(name = "time")
	private Date time;

	/**
	 * 出库（入库）数量
	 */
	@Column(name = "number")
	private Double number;

	/**
	 * 领用价值
	 */
	@Column(name = "outbound_cost")
	private Double outboundCost;

	/**
	 * 备注
	 */
	@Column(name = "remark")
	private String remark;

	/**
	 * 对于食材明细出库的详细类型补充 1.早餐 2.中餐 3.晚餐 4.夜宵5.早中晚餐6.中晚餐
	 */
	@Column(name = "meal_type")
	private Integer mealType;

	/**
	 * 名称
	 */
	@Transient
	private String name;

	/**
	 * 查询字段
	 */
	@Transient
	private Date orderTimeBegin;
	/**
	 * 查询字段
	 */
	@Transient
	private Date orderTimeEnd;

	/**
	 * 物品类型
	 */
	@Transient
	private Integer type;
	
	/**
	 * 物料id
	 */
	@Transient
	private Long singleMealConsumptionId;
	
	

	public Long getSingleMealConsumptionId() {
		return singleMealConsumptionId;
	}

	public void setSingleMealConsumptionId(Long singleMealConsumptionId) {
		this.singleMealConsumptionId = singleMealConsumptionId;
	}

	public Integer getMealType() {
		return mealType;
	}

	public void setMealType(Integer mealType) {
		this.mealType = mealType;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Double getOutboundCost() {
		return outboundCost;
	}

	public void setOutboundCost(Double outboundCost) {
		this.outboundCost = outboundCost;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Double getNumber() {
		return number;
	}

	public void setNumber(Double number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getOrderTimeBegin() {
		return orderTimeBegin;
	}

	public void setOrderTimeBegin(Date orderTimeBegin) {
		this.orderTimeBegin = orderTimeBegin;
	}

	public Date getOrderTimeEnd() {
		return orderTimeEnd;
	}

	public void setOrderTimeEnd(Date orderTimeEnd) {
		this.orderTimeEnd = orderTimeEnd;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Long getOfficeSuppliesId() {
		return officeSuppliesId;
	}

	public void setOfficeSuppliesId(Long officeSuppliesId) {
		this.officeSuppliesId = officeSuppliesId;
	}

	public OfficeSupplies getOfficeSupplies() {
		return OfficeSupplies;
	}

	public void setOfficeSupplies(OfficeSupplies officeSupplies) {
		OfficeSupplies = officeSupplies;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Long getOrgNameId() {
		return orgNameId;
	}

	public void setOrgNameId(Long orgNameId) {
		this.orgNameId = orgNameId;
	}

	public BaseData getOrgName() {
		return orgName;
	}

	public void setOrgName(BaseData orgName) {
		this.orgName = orgName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
