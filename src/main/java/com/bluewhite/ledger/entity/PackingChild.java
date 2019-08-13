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
	 * 贴包类型（1=发货，2=调拨）
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
	 * 入库仓库类型id
	 */
	@Column(name = "warehouse_id")
	private Long warehouseId;
	
	/**
	 * 入库仓库类型（0=主仓库，1=客供仓库，2=次品）
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "warehouse_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseData warehouse;
	
	
	/**
	 * 贴包数量（包或者箱）
	 */
	@Column(name = "stick_number")
	private String stickNumber;
	
	/**
	 * 单只价格
	 */
	@Column(name = "price")
	private Double price;
	
	/**
	 * 实际数量
	 */
	@Column(name = "count")
	private Integer count;
	
	/**
	 * 总价
	 */
	@Column(name = "sum_price")
	private Double sumPrice;

	/**
	 * 是否拥有版权（0=否1=是）
	 */
	@Column(name = "copyright")
	private Integer copyright ;
	
	/**
	 * 是否产生新批次号（0=否1=是）
	 */
	@Column(name = "new_bacth")
	private Integer newBacth ;
	
	/**
	 * 销售编号 
	 */
	@Column(name = "sale_number")
	private String saleNumber;
	
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
	 * 预付款备注
	 */
	@Column(name = "remark")
	private String remark;
	
	/**
	 * 是否审核（0=未审核，1=已审核）
	 */
	@Column(name = "audit")
	private Integer audit;
	
	/**
	 * 到岸（收货）状态（3=全部收货，2=部分收货，1=未收货 ）
	 */
	@Column(name = "delivery")
	private Integer delivery;
	
	/**
	 * 到岸數量是否确认 （0=否,1=是）
	 */
	@Column(name = "delivery_status")
	private Integer deliveryStatus;
	
	/**
	 * 到岸（收货）数量
	 */
	@Column(name = "delivery_number")
	private Integer deliveryNumber;
	
	/**
	 * 到岸（收货）日期
	 */
	@Column(name = "delivery_date")
	private Date deliveryDate;
	
	/**
	 * 争议数量
	 */
	@Column(name = "dispute_number")
	private Integer disputeNumber;
	
	/**
	 * 争议数量备注
	 */
	@Column(name = "dispute_remark")
	private String disputeRemark;
	
	/**
	 * 预计到岸（收货）结款日期
	 */
	@Column(name = "delivery_collection_date")
	private Date deliveryCollectionDate;
	
	/**
	 * 货款总值
	 */
	@Column(name = "offshore_pay")
	private Double offshorePay;

	/**
	 * 已收货货款
	 */
	@Column(name = "accept_pay")
	private Double acceptPay;
	
	/**
	 * 争议货款
	 */
	@Column(name = "dispute_pay")
	private Double disputePay;
	
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

	public Integer getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(Integer deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Integer getDisputeNumber() {
		return disputeNumber;
	}

	public void setDisputeNumber(Integer disputeNumber) {
		this.disputeNumber = disputeNumber;
	}

	public String getDisputeRemark() {
		return disputeRemark;
	}

	public void setDisputeRemark(String disputeRemark) {
		this.disputeRemark = disputeRemark;
	}

	public Double getDisputePay() {
		return disputePay;
	}

	public void setDisputePay(Double disputePay) {
		this.disputePay = disputePay;
	}

	public Double getOffshorePay() {
		return offshorePay;
	}

	public void setOffshorePay(Double offshorePay) {
		this.offshorePay = offshorePay;
	}

	public Double getAcceptPay() {
		return acceptPay;
	}

	public void setAcceptPay(Double acceptPay) {
		this.acceptPay = acceptPay;
	}



	public Integer getDelivery() {
		return delivery;
	}

	public void setDelivery(Integer delivery) {
		this.delivery = delivery;
	}

	public Integer getDeliveryNumber() {
		return deliveryNumber;
	}

	public void setDeliveryNumber(Integer deliveryNumber) {
		this.deliveryNumber = deliveryNumber;
	}

	public Date getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(Date deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public Date getDeliveryCollectionDate() {
		return deliveryCollectionDate;
	}

	public void setDeliveryCollectionDate(Date deliveryCollectionDate) {
		this.deliveryCollectionDate = deliveryCollectionDate;
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getSumPrice() {
		return sumPrice;
	}

	public void setSumPrice(Double sumPrice) {
		this.sumPrice = sumPrice;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSaleNumber() {
		return saleNumber;
	}

	public void setSaleNumber(String saleNumber) {
		this.saleNumber = saleNumber;
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

	
	public Integer getCopyright() {
		return copyright;
	}

	public void setCopyright(Integer copyright) {
		this.copyright = copyright;
	}

	public Integer getNewBacth() {
		return newBacth;
	}

	public void setNewBacth(Integer newBacth) {
		this.newBacth = newBacth;
	}

	public Integer getAudit() {
		return audit;
	}

	public void setAudit(Integer audit) {
		this.audit = audit;
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

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
	
	


}
