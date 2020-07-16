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
import com.bluewhite.common.utils.excel.Poi;
import com.bluewhite.ledger.entity.OrderProcurement;
/**
 * 面辅料库存
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_basedate_materiel")
public class Materiel extends BaseEntity<Long>{
	
	/**
     * 面料编号
     */
	@Column(name = "number")
	@Poi(name = "", column = "A")
    private String number;
	
	/**
     * 面料名
     */
	@Column(name = "name")
	@Poi(name = "", column = "B")
    private String name;
	
	/**
     * 面料价格
     */
	@Column(name = "price")
	@Poi(name = "", column = "C")
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
	 * 面辅料类型id
	 */
	@Column(name = "materiel_type_id")
	private Long materielTypeId;
	
	/**
	 * 面辅料类型
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
	 * 是否换算(0=是，1=否)
	 * @return
	 */
	@Column(name = "convert_change")
    private Integer convertChange;
	
	/**
	 * 需要的数字填写
	 * @return
	 */
	@Column(name = "count")
	private Integer count;
	
	/**
	 *  换算之后的单位id
	 */
	@Column(name = "convert_unit_id")
	private Long convertUnitId;
	
	/**
	 *  换算之后的单位
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "convert_unit_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseData convertUnit;
	
	/**
	 * 换算之后的单位的价格
	 * @return
	 */
	@Column(name = "convert_price")
    private Double convertPrice;
	
	/**
	 * 需要的数字填写
	 * @return
	 */
	@Column(name = "convert_number")
    private Double convertNumber;
	
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


	public Integer getConvertChange() {
		return convertChange;
	}

	public void setConvertChange(Integer convertChange) {
		this.convertChange = convertChange;
	}

	public Long getConvertUnitId() {
		return convertUnitId;
	}

	public void setConvertUnitId(Long convertUnitId) {
		this.convertUnitId = convertUnitId;
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

	public Double getConvertNumber() {
		return convertNumber;
	}

	public void setConvertNumber(Double convertNumber) {
		this.convertNumber = convertNumber;
	}

	public Double getConvertPrice() {
		return convertPrice;
	}

	public void setConvertPrice(Double convertPrice) {
		this.convertPrice = convertPrice;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public BaseData getUnit() {
		return unit;
	}

	public void setUnit(BaseData unit) {
		this.unit = unit;
	}

	public BaseData getConvertUnit() {
		return convertUnit;
	}

	public void setConvertUnit(BaseData convertUnit) {
		this.convertUnit = convertUnit;
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
}
