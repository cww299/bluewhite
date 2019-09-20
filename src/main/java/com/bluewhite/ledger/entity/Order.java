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


/**
 * 订单（下单合同）
 * @author zhangliang
 *
 */
@Entity
@Table(name = "ledger_order")
public class Order extends BaseEntity<Long>{
	
	
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
	 * 产品编号
	 */
	@Column(name = "product_number")
	private String productNumber;
	
	/**
	 * 合同数量
	 */
	@Column(name = "number")
	private Integer number;
	
	/**
	 * 剩余数量
	 */
	@Column(name = "surplus_Number")
	private Integer surplusNumber;
	
	
	/**
	 * 备注
	 */
	@Column(name = "remark")
	private String remark;
	
	/**
	 * 下单时间
	 */
	@Column(name = "order_date")
	private Date orderDate;
	
	/**
	 * 单只价格
	 */
	@Column(name = "price")
	private Double price;
	
	/**
	 * 是否内部（属于电子商务部的订单）
	 */
	@Column(name = "internal")
	private Integer internal;
	
	/**
	 * 是否审核（0=未审核，1=已审核）（审核成功后进入生产计划环节）
	 */
	@Column(name = "audit")
	private Integer audit;
	
	/**
	 * 客户name
	 * 
	 */
	@Transient
	private String customerName;
	
	/**
	 * 批量新增
	 */
	@Transient
	private String orderChild;

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
	
	/**
	 * 产品name
	 */
	@Transient
	private String productName;
	    
	
	
	
	public Integer getAudit() {
		return audit;
	}


	public void setAudit(Integer audit) {
		this.audit = audit;
	}


	public Integer getSurplusNumber() {
		return surplusNumber;
	}


	public void setSurplusNumber(Integer surplusNumber) {
		this.surplusNumber = surplusNumber;
	}


	public Integer getInternal() {
		return internal;
	}


	public void setInternal(Integer internal) {
		this.internal = internal;
	}


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


	public String getOrderChild() {
		return orderChild;
	}


	public void setOrderChild(String orderChild) {
		this.orderChild = orderChild;
	}


	public String getProductNumber() {
		return productNumber;
	}


	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}


	public Double getPrice() {
		return price;
	}


	public void setPrice(Double price) {
		this.price = price;
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


	public Date getOrderDate() {
		return orderDate;
	}


	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
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


	public Integer getNumber() {
		return number;
	}


	public void setNumber(Integer number) {
		this.number = number;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
	
	
	
}
