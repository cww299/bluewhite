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
import com.bluewhite.ledger.entity.Customer;

/**
 * 后勤物品库存
 * 
 * @author zhangliang
 *
 */
@Entity
@Table(name = "person_office_supplies")
public class OfficeSupplies extends BaseEntity<Long> {

	/**
	 * 物品名
	 */
	@Column(name = "name")
	private String name;

	/**
	 * 价格
	 */
	@Column(name = "price")
	private Double price;

	/**
	 * 单位id
	 */
	@Column(name = "unit_id")
	private Long unitId;

	/**
	 * 单位
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "unit_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseData unit;
	
	/**
	 * 物料id
	 */
	@Column(name = "single_meal_consumption_id")
	private Long singleMealConsumptionId;
	
	/**
	 * 物料
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "single_meal_consumption_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseData singleMealConsumption;

	/**
	 * 供应商id
	 * 
	 */
	@Column(name = "customer_id")
	private Long customerId;

	/**
	 * 供应商
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Customer customer;

	/**
	 * 类型(1.办公用品，2.机械配件,3.食材)
	 */
	@Column(name = "type")
	private Integer type;

	/**
	 * 库存数量
	 * 
	 */
	@Column(name = "inventory_number")
	private Double inventoryNumber;

	/**
	 * 库位
	 * 
	 */
	@Column(name = "location")
	private String location;

	/**
	 * 库值
	 * 
	 */
	@Column(name = "library_value")
	private Double libraryValue;

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
	
	

	public Long getSingleMealConsumptionId() {
		return singleMealConsumptionId;
	}

	public void setSingleMealConsumptionId(Long singleMealConsumptionId) {
		this.singleMealConsumptionId = singleMealConsumptionId;
	}

	public BaseData getSingleMealConsumption() {
		return singleMealConsumption;
	}

	public void setSingleMealConsumption(BaseData singleMealConsumption) {
		this.singleMealConsumption = singleMealConsumption;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
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

	public Double getLibraryValue() {
		return libraryValue;
	}

	public void setLibraryValue(Double libraryValue) {
		this.libraryValue = libraryValue;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

	public BaseData getUnit() {
		return unit;
	}

	public void setUnit(BaseData unit) {
		this.unit = unit;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Double getInventoryNumber() {
		return inventoryNumber;
	}

	public void setInventoryNumber(Double inventoryNumber) {
		this.inventoryNumber = inventoryNumber;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
