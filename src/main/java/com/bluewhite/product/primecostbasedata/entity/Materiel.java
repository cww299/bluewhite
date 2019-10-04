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
	private BaseOne unit;
	
	
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
	private BaseOne convertUnit;
	
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
	 * 面料的订购记录
	 */
	@OneToMany(mappedBy = "materiel",cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<OrderProcurement> orderProcurements = new HashSet<OrderProcurement>();
	
	
	
	
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

	public BaseOne getConvertUnit() {
		return convertUnit;
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

	public void setConvertUnit(BaseOne convertUnit) {
		this.convertUnit = convertUnit;
	}

	public Long getUnitId() {
		return unitId;
	}

	public void setUnitId(Long unitId) {
		this.unitId = unitId;
	}

	public BaseOne getUnit() {
		return unit;
	}

	public void setUnit(BaseOne unit) {
		this.unit = unit;
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
