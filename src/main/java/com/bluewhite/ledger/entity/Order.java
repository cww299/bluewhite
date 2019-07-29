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
	 * 销售编号 
	 */
	@Column(name = "sale_number")
	private String saleNumber;
	
	/**
	 * 客户id
	 * 
	 */
	@Column(name = "customr_id")
	private Long customrId;
	

	/**
	 * 客户
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customr_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Customr customr;
	
	
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
	public Long getCustomrId() {
		return customrId;
	}


	public void setCustomrId(Long customrId) {
		this.customrId = customrId;
	}


	public Customr getCustomr() {
		return customr;
	}


	public void setCustomr(Customr customr) {
		this.customr = customr;
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
