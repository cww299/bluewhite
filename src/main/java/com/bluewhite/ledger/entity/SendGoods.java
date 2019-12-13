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
 * 发货单（待发货，已发货）
 * @author zhangliang
 *
 */
@Entity
@Table(name = "ledger_send_goods")
public class SendGoods extends BaseEntity<Long>{

	/**
	 * 发货客户id
	 * 
	 */
	@Column(name = "customer_id")
	private Long customerId;

	/**
	 * 发货客户
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Customer customer;
	
	/**
	 * 发货申请人id
	 * 
	 */
	@Column(name = "user_id")
	private Long userId;

	/**
	 * 发货申请人
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
	private User user;
	
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
	 * 预计发出总数
	 */
	@Column(name = "number")
	private Integer number;
	
	/**
	 * 发货日期
	 */
	@Column(name = "send_date")
	private Date sendDate;
	
	/**
	 * 发货备注
	 */
	@Column(name = "remark")
	private String remark;
	/**
	 * 产品分类(1=成品，2=皮壳)
	 */
	@Column(name = "product_type")
	private Integer productType;
	/**
	 * (1.库存不足，2.无库存)
	 * 发货状态
	 */
	@Transient
	private Integer status;
	
	/**
	 * 剩余发货数量
	 */
	@Transient
	private Integer surplusNumber;
	
	/**
	 * 实际发货数量
	 */
	@Transient
	private Integer sendNumber;
	
	/**
	 * 批次号
	 */
	@Transient
	private String bacthNumber;
	
	/**
	 * 客户name
	 * 
	 */
	@Transient
	private String customerName;
	
	/**
	 * 产品name
	 */
	@Transient
	private String productName;

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
	 * 借货申请
	 */
	@Transient
	private String applyVoucher;
	
	
	
	
	
	
	public Integer getSendNumber() {
		return sendNumber;
	}

	public void setSendNumber(Integer sendNumber) {
		this.sendNumber = sendNumber;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getApplyVoucher() {
		return applyVoucher;
	}

	public void setApplyVoucher(String applyVoucher) {
		this.applyVoucher = applyVoucher;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public Date getSendDate() {
		return sendDate;
	}

	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getSurplusNumber() {
		return surplusNumber;
	}

	public void setSurplusNumber(Integer surplusNumber) {
		this.surplusNumber = surplusNumber;
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

}
