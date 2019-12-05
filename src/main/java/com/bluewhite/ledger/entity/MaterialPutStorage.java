package com.bluewhite.ledger.entity;

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
import com.bluewhite.product.primecostbasedata.entity.Materiel;
import com.bluewhite.system.user.entity.User;

/**
 * 物料入库详情单
 *
 */
@Entity
@Table(name = "ledger_material_put_storage")
public class MaterialPutStorage extends BaseEntity<Long> {
	/**
	 * 编号
	 */
	@Column(name = "serial_number")
	private String serialNumber;
	
	/**
	 * 物料id
	 */
	@Column(name = "materiel_id")
	private Long materielId;

	/**
	 * 物料
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "materiel_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Materiel materiel;

	/**
	 * 物料入库时会存在采购单 采购单id
	 * 
	 */
	@Column(name = "order_procurement_id")
	private Long orderProcurementId;

	/**
	 * 采购单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_procurement_id", referencedColumnName = "id", insertable = false, updatable = false)
	private OrderProcurement orderProcurement;

	/**
	 * 入库类型 （1=采购入库）（2=调拨入库） （3=退货入库 ） （4=换货入库 ）（5=盘亏入库）
	 * 采购入库：根据采购单入库
	 * 调拨入库：根据调拨申请单入库
	 * 退货入库：根据退货申请单入库
	 * 换货入库：根据换货申请单入库
	 * 盘亏入库：根据盘盈申请单入库
	 * 
	 */
	@Column(name = "in_status")
	private Integer inStatus;

	/**
	 * 仓管指定 入库仓库种类id
	 */
	@Column(name = "in_warehouse_type_id")
	private Long inWarehouseTypeId;

	/**
	 * 入库仓库种类
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "in_warehouse_type_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseData inWarehouseType;

	/**
	 * 入库时间
	 */
	@Column(name = "arrival_time")
	private Date arrivalTime;

	/**
	 * 入库数量
	 */
	@Column(name = "arrival_number")
	private Double arrivalNumber;

	/**
	 * 库区id
	 */
	@Column(name = "storage_area_id")
	private Long storageAreaId;

	/**
	 * 库区
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "storage_area_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseData storageArea;

	/**
	 * 库位id
	 */
	@Column(name = "storage_location_id")
	private Long storageLocationId;

	/**
	 * 库位
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "storage_location_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseData storageLocation;

	/**
	 * 入库操作人id
	 * 
	 */
	@Column(name = "user_storage_id")
	private Long userStorageId;

	/**
	 * 入库操作人
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_storage_id", referencedColumnName = "id", insertable = false, updatable = false)
	private User userStorage;

	/**
	 * 是否验货
	 */
	@Column(name = "inspection")
	private Integer inspection;
	
	/**
	 * 实际平方克重
	 */
	@Column(name = "square_gram")
	private Double squareGram;
	
	/**
	 * 偷克重产生被偷价值(缺克重价值)
	 */
	@Column(name = "gram_price")
	private Double gramPrice;
	
	/**
	 * 出库后剩余数量
	 */
	@Transient
	private Double surplusNumber;

	/**
	 * 物料名称
	 */
	@Transient
	private String materielName;
	
	/**
	 * 物料编号
	 */
	@Transient
	private String materielNumber;
	
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
	
	
	
	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
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

	public String getMaterielName() {
		return materielName;
	}

	public void setMaterielName(String materielName) {
		this.materielName = materielName;
	}

	public String getMaterielNumber() {
		return materielNumber;
	}

	public void setMaterielNumber(String materielNumber) {
		this.materielNumber = materielNumber;
	}

	public Double getSquareGram() {
		return squareGram;
	}

	public void setSquareGram(Double squareGram) {
		this.squareGram = squareGram;
	}

	public Double getGramPrice() {
		return gramPrice;
	}

	public void setGramPrice(Double gramPrice) {
		this.gramPrice = gramPrice;
	}

	public Long getUserStorageId() {
		return userStorageId;
	}

	public void setUserStorageId(Long userStorageId) {
		this.userStorageId = userStorageId;
	}

	public User getUserStorage() {
		return userStorage;
	}

	public void setUserStorage(User userStorage) {
		this.userStorage = userStorage;
	}

	public Integer getInspection() {
		return inspection;
	}

	public void setInspection(Integer inspection) {
		this.inspection = inspection;
	}

	public Long getMaterielId() {
		return materielId;
	}

	public void setMaterielId(Long materielId) {
		this.materielId = materielId;
	}

	public Materiel getMateriel() {
		return materiel;
	}

	public void setMateriel(Materiel materiel) {
		this.materiel = materiel;
	}

	public Long getOrderProcurementId() {
		return orderProcurementId;
	}

	public void setOrderProcurementId(Long orderProcurementId) {
		this.orderProcurementId = orderProcurementId;
	}

	public OrderProcurement getOrderProcurement() {
		return orderProcurement;
	}

	public void setOrderProcurement(OrderProcurement orderProcurement) {
		this.orderProcurement = orderProcurement;
	}

	public Integer getInStatus() {
		return inStatus;
	}

	public void setInStatus(Integer inStatus) {
		this.inStatus = inStatus;
	}

	public Long getInWarehouseTypeId() {
		return inWarehouseTypeId;
	}

	public void setInWarehouseTypeId(Long inWarehouseTypeId) {
		this.inWarehouseTypeId = inWarehouseTypeId;
	}

	public BaseData getInWarehouseType() {
		return inWarehouseType;
	}

	public void setInWarehouseType(BaseData inWarehouseType) {
		this.inWarehouseType = inWarehouseType;
	}

	public Date getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public Double getArrivalNumber() {
		return arrivalNumber;
	}

	public void setArrivalNumber(Double arrivalNumber) {
		this.arrivalNumber = arrivalNumber;
	}

	public Long getStorageAreaId() {
		return storageAreaId;
	}

	public void setStorageAreaId(Long storageAreaId) {
		this.storageAreaId = storageAreaId;
	}

	public BaseData getStorageArea() {
		return storageArea;
	}

	public void setStorageArea(BaseData storageArea) {
		this.storageArea = storageArea;
	}

	public Long getStorageLocationId() {
		return storageLocationId;
	}

	public void setStorageLocationId(Long storageLocationId) {
		this.storageLocationId = storageLocationId;
	}

	public BaseData getStorageLocation() {
		return storageLocation;
	}

	public void setStorageLocation(BaseData storageLocation) {
		this.storageLocation = storageLocation;
	}

	public Double getSurplusNumber() {
		return surplusNumber;
	}

	public void setSurplusNumber(Double surplusNumber) {
		this.surplusNumber = surplusNumber;
	}


}
