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
 * 财务销售单（由各仓库发货单转化）
 * @author zhangliang
 *
 */
@Entity
@Table(name = "ledger_sale")
public class Sale extends BaseEntity<Long>{
	
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
	
	

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Double getSumPrice() {
		return sumPrice;
	}

	public void setSumPrice(Double sumPrice) {
		this.sumPrice = sumPrice;
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

	public String getSaleNumber() {
		return saleNumber;
	}

	public void setSaleNumber(String saleNumber) {
		this.saleNumber = saleNumber;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getAudit() {
		return audit;
	}

	public void setAudit(Integer audit) {
		this.audit = audit;
	}

	public Integer getDelivery() {
		return delivery;
	}

	public void setDelivery(Integer delivery) {
		this.delivery = delivery;
	}

	public Integer getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(Integer deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
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

	public Date getDeliveryCollectionDate() {
		return deliveryCollectionDate;
	}

	public void setDeliveryCollectionDate(Date deliveryCollectionDate) {
		this.deliveryCollectionDate = deliveryCollectionDate;
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

	public Double getDisputePay() {
		return disputePay;
	}

	public void setDisputePay(Double disputePay) {
		this.disputePay = disputePay;
	}
	
	
	
	


}
