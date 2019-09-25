package com.bluewhite.personnel.officeshare.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.basedata.entity.BaseData;

/**
 * 办公用品库存
 * @author zhangliang
 *
 */
@Entity
@Table(name = "person_office_supplies")
public class OfficeSupplies extends BaseEntity<Long>{
	
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
     * 类型
     */
	@Column(name = "type")
    private String type;
	
	/**
	 * 库存数量
	 * 
	 */
	@Column(name = "inventory_number")
	private Integer inventoryNumber;
	
	/**
	 * 仓库种类id
	 */
	@Column(name = "warehouse_type_id")
	private Long warehouseTypeId;
	
	/**
	 * 仓库种类
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "warehouse_type_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseData warehouseType;
	
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
	@Column(name = "Library_value")
	private Double LibraryValue;
	
	

	public Double getLibraryValue() {
		return LibraryValue;
	}

	public void setLibraryValue(Double libraryValue) {
		LibraryValue = libraryValue;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getInventoryNumber() {
		return inventoryNumber;
	}

	public void setInventoryNumber(Integer inventoryNumber) {
		this.inventoryNumber = inventoryNumber;
	}

	public Long getWarehouseTypeId() {
		return warehouseTypeId;
	}

	public void setWarehouseTypeId(Long warehouseTypeId) {
		this.warehouseTypeId = warehouseTypeId;
	}

	public BaseData getWarehouseType() {
		return warehouseType;
	}

	public void setWarehouseType(BaseData warehouseType) {
		this.warehouseType = warehouseType;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	
	
	


}
