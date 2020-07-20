package com.bluewhite.product.primecostbasedata.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.ledger.entity.OrderProcurement;
/**
 *  物料
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_basedate_materiel")
public class Materiel extends BaseEntity<Long>{
	
	/**
     * 物料编号
     */
	@Column(name = "number")
    private String number;
	
	/**
     * 物料名
     */
	@Column(name = "name")
    private String name;
	
	
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
	 * 物料类型id
	 */
	@Column(name = "materiel_type_id")
	private Long materielTypeId;
	
	/**
	 * 物料类型
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "materiel_type_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseData materielType;
	
	/**
	 * 物料定性id
	 */
	@Column(name = "material_qualitative_id")
	private Long materialQualitativeId;
	
	/**
	 * 物料定性
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "material_qualitative_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseData materialQualitative;
		
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
	 * 面料的订购记录
	 */
	@OneToMany(mappedBy = "materiel",cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<OrderProcurement> orderProcurements = new HashSet<OrderProcurement>();
		
	/**
	 * 库存数量
	 * 
	 */
	@Transient
	private Double inventoryNumber;
	@Transient
	private String materielTypeIds;
	
	
	public Long getMaterialQualitativeId() {
		return materialQualitativeId;
	}

	public void setMaterialQualitativeId(Long materialQualitativeId) {
		this.materialQualitativeId = materialQualitativeId;
	}

	public BaseData getMaterialQualitative() {
		return materialQualitative;
	}

	public void setMaterialQualitative(BaseData materialQualitative) {
		this.materialQualitative = materialQualitative;
	}

	public Set<OrderProcurement> getOrderProcurements() {
		return orderProcurements;
	}

	public void setOrderProcurements(Set<OrderProcurement> orderProcurements) {
		this.orderProcurements = orderProcurements;
	}

	public Long getMaterielTypeId() {
		return materielTypeId;
	}

	public void setMaterielTypeId(Long materielTypeId) {
		this.materielTypeId = materielTypeId;
	}

	public BaseData getMaterielType() {
		return materielType;
	}

	public void setMaterielType(BaseData materielType) {
		this.materielType = materielType;
	}


	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}


	public Double getInventoryNumber() {
		return inventoryNumber;
	}

	public void setInventoryNumber(Double inventoryNumber) {
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


	public BaseData getUnit() {
		return unit;
	}

	public void setUnit(BaseData unit) {
		this.unit = unit;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}



	public String getMaterielTypeIds() {
		return materielTypeIds;
	}

	public void setMaterielTypeIds(String materielTypeIds) {
		this.materielTypeIds = materielTypeIds;
	}

	
}
