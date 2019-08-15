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
import com.bluewhite.product.product.entity.Product;
/**
 * 贴包子单
 * 
 * @author zhangliang
 *
 */
@Entity
@Table(name = "ledger_packing_child")
public class PackingChild extends BaseEntity<Long> {
	
	
	/**
	 * 客户id
	 * 
	 */
	@Column(name = "customer_id")
	private Long customerId;
	
	/**
	 * 客户
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Customer customer;
	
	/**
	 * 发货单id
	 * 
	 */
	@Column(name = "sendGoods_id")
	private Long sendGoodsId;

	/**
	 * 发货单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sendGoods_id", referencedColumnName = "id", insertable = false, updatable = false)
	private SendGoods sendGoods;
	
	/**
	 * 订单id
	 * 
	 */
	@Column(name = "packing_id")
	private Long packingId;
	

	/**
	 * 父订单实体
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "packing_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Packing packing;
	     
	/**
	 * 批次号
	 */
	@Column(name = "bacth_number")
	private String bacthNumber;

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
	 * 调拨仓库id
	 */
	@Column(name = "warehouse_type_id")
	private Long warehouseTypeId;
	
	/**
	 * 调拨仓库
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "warehouse_type_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseData warehouseType;
	
	/**
	 * 出库仓库id
	 */
	@Column(name = "warehouse_type_delivery_id")
	private Long warehouseTypeDeliveryId;
	
	/**
	 * 出库仓库
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "warehouse_type_delivery_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseData warehouseTypeDelivery;

	/**
	 * 类型（1=发货，2=调拨）
	 * 
	 */
	@Column(name = "type")
	private Integer type;
	
	/**
	 * 是否确认（调拨单确认入库  ）
	 */
	@Column(name = "confirm")
	private Integer confirm ;
	
	/**
	 * 调拨单确认入库数字  
	 */
	@Column(name = "confirm_number")
	private Integer confirmNumber ;
	
	/**
	 * 仓库类型id
	 */
	@Column(name = "warehouse_id")
	private Long warehouseId;
	
	/**
	 * 仓库类型（0=主仓库，1=客供仓库，2=次品）
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "warehouse_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseData warehouse;
	
	/**
	 * 八号仓库上一级调拨单id
	 */
	@Column(name = "last_packing_childId")
	private Long lastPackingChildId;
	
	/**
	 * 八号仓库上一级调拨单剩余数量
	 */
	@Column(name = "surplus_Number")
	private Integer surplusNumber;
	
	/**
	 * 贴包数量（包或者箱）
	 */
	@Column(name = "stick_number")
	private String stickNumber;
	
	/**
	 * 实际数量
	 */
	@Column(name = "count")
	private Integer count;
	
	/**
	 * 发货时间
	 */
	@Column(name = "send_date")
	private Date sendDate;
	
	/**
	 * 是否发货
	 */
	@Column(name = "flag")
	private Integer flag;
	
	/**
	 * 产品name
	 */
	@Transient
	private String productName;
	
	/**
	 * 客户name
	 * 
	 */
	@Transient
	private String customerName;
	
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
	
	
	
	
	public Integer getSurplusNumber() {
		return surplusNumber;
	}

	public void setSurplusNumber(Integer surplusNumber) {
		this.surplusNumber = surplusNumber;
	}

	public Long getLastPackingChildId() {
		return lastPackingChildId;
	}

	public void setLastPackingChildId(Long lastPackingChildId) {
		this.lastPackingChildId = lastPackingChildId;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public Long getWarehouseTypeDeliveryId() {
		return warehouseTypeDeliveryId;
	}

	public void setWarehouseTypeDeliveryId(Long warehouseTypeDeliveryId) {
		this.warehouseTypeDeliveryId = warehouseTypeDeliveryId;
	}

	public BaseData getWarehouseTypeDelivery() {
		return warehouseTypeDelivery;
	}

	public void setWarehouseTypeDelivery(BaseData warehouseTypeDelivery) {
		this.warehouseTypeDelivery = warehouseTypeDelivery;
	}

	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	public BaseData getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(BaseData warehouse) {
		this.warehouse = warehouse;
	}

	public Integer getConfirmNumber() {
		return confirmNumber;
	}

	public void setConfirmNumber(Integer confirmNumber) {
		this.confirmNumber = confirmNumber;
	}

	public Integer getConfirm() {
		return confirm;
	}

	public void setConfirm(Integer confirm) {
		this.confirm = confirm;
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

	public String getStickNumber() {
		return stickNumber;
	}

	public void setStickNumber(String stickNumber) {
		this.stickNumber = stickNumber;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
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

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Long getPackingId() {
		return packingId;
	}

	public void setPackingId(Long packingId) {
		this.packingId = packingId;
	}

	public Packing getPacking() {
		return packing;
	}

	public void setPacking(Packing packing) {
		this.packing = packing;
	}

	
	public Long getSendGoodsId() {
		return sendGoodsId;
	}

	public void setSendGoodsId(Long sendGoodsId) {
		this.sendGoodsId = sendGoodsId;
	}

	public SendGoods getSendGoods() {
		return sendGoods;
	}

	public void setSendGoods(SendGoods sendGoods) {
		this.sendGoods = sendGoods;
	}

	public String getBacthNumber() {
		return bacthNumber;
	}

	public void setBacthNumber(String bacthNumber) {
		this.bacthNumber = bacthNumber;
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


}
