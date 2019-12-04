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
import com.bluewhite.product.product.entity.Product;
import com.bluewhite.system.user.entity.User;

/**
 * 出库单
 * 
 * @author zhangliang
 *
 */
@Entity
@Table(name = "ledger_out_storage")
public class OutStorage extends BaseEntity<Long> {
	
	/**
	 * 编号
	 */
	@Column(name = "serial_number")
	private String serialNumber;
	
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
	 * 入库单id
	 */
	@Column(name = "put_storage_id")
	private Long putStorageId;

	/**
	 * 入库单（选择入库单进行出库）
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "put_storage_id", referencedColumnName = "id", insertable = false, updatable = false)
	private PutStorage putStorage;
	
	/**
	 * 出库单类型（1=生产出库） （2=调拨出库） （3=销售换货出库 ） （4=采购退货出库 ） （5=盘盈出库 ）(6=返工出库)
	 */
	@Column(name = "out_status")
	private Integer outStatus;

	/**
	 * 出库时间
	 */
	@Column(name = "arrival_time")
	private Date arrivalTime;

	/**
	 * 出库数量
	 */
	@Column(name = "arrival_number")
	private Integer arrivalNumber;

	/**
	 * 出库操作人id
	 * 
	 */
	@Column(name = "user_storage_id")
	private Long userStorageId;

	/**
	 * 出库操作人
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_storage_id", referencedColumnName = "id", insertable = false, updatable = false)
	private User userStorage;
	
	/**
	 * 物料名称
	 */
	@Transient
	private String productName;
	
	/**
	 * 物料编号
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
	
	
	

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
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

	public Long getPutStorageId() {
		return putStorageId;
	}

	public void setPutStorageId(Long putStorageId) {
		this.putStorageId = putStorageId;
	}

	public PutStorage getPutStorage() {
		return putStorage;
	}

	public void setPutStorage(PutStorage putStorage) {
		this.putStorage = putStorage;
	}

	public Integer getOutStatus() {
		return outStatus;
	}

	public void setOutStatus(Integer outStatus) {
		this.outStatus = outStatus;
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
