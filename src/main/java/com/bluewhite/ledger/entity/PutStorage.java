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
import com.bluewhite.onlineretailers.inventory.entity.Inventory;
import com.bluewhite.product.product.entity.Product;
import com.bluewhite.system.user.entity.User;

/**
 * 
 * 入库单（对于所有入库行为生成的入库数据）
 * 
 * @author zhangliang
 *
 */
@Entity
@Table(name = "ledger_put_storage")
public class PutStorage extends BaseEntity<Long> {

	/**
	 * 产品id
	 */
	@Column(name = "product_id")
	private Long productId;

	/**
	 * 产品
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Product product;
	
	/**
	 * 生产入库时会存在加工单
	 * 加工单id
	 */
	@Column(name = "order_outSource_id")
	private Long orderOutSourceId;

	/**
	 * 加工单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_OutSource_id", referencedColumnName = "id", insertable = false, updatable = false)
	private OrderOutSource orderOutSource;

	/**
	 * 入库单的订单状态 （1=生产入库） （2=调拨入库） （3=退货入库 ） （4=换货入库 ） （5=采购入库） （6=盘亏入库）
	 */
	@Column(name = "in_status")
	private Integer inStatus;

	/**
	 * 仓管指定 入库库存id
	 */
	@Column(name = "inventory_id")
	private Long inventoryId;

	/**
	 * 入库库存
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "inventory_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Inventory inventory;

	/**
	 * 入库时间
	 */
	@Column(name = "arrival_time")
	private Date arrivalTime;

	/**
	 * 入库数量
	 */
	@Column(name = "arrival_number")
	private Integer arrivalNumber;
	
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
	 * 仓管指定 入库仓库种类id
	 */
	@Column(name = "in_warehouse_type_id")
	private Long inWarehouseTypeId;

	/**
	 * 出库后剩余数量
	 */
	@Transient
	private Double surplusNumber;
	
	/**
	 * 产品名称
	 */
	@Transient
	private String productName;
	
	/**
	 * 产品名编号
	 */
	@Transient
	private String productNumber;
	
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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
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

	public Double getSurplusNumber() {
		return surplusNumber;
	}

	public void setSurplusNumber(Double surplusNumber) {
		this.surplusNumber = surplusNumber;
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

	public Long getOrderOutSourceId() {
		return orderOutSourceId;
	}

	public void setOrderOutSourceId(Long orderOutSourceId) {
		this.orderOutSourceId = orderOutSourceId;
	}

	public OrderOutSource getOrderOutSource() {
		return orderOutSource;
	}

	public void setOrderOutSource(OrderOutSource orderOutSource) {
		this.orderOutSource = orderOutSource;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
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

	public Long getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(Long inventoryId) {
		this.inventoryId = inventoryId;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public Date getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public Integer getArrivalNumber() {
		return arrivalNumber;
	}

	public void setArrivalNumber(Integer arrivalNumber) {
		this.arrivalNumber = arrivalNumber;
	}
	
	
	

}
