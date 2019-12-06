package com.bluewhite.production.temporarypack;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.product.product.entity.Product;

/**
 * 下货单
 * 
 * @author
 *
 */
@Entity
@Table(name = "pro_under_goods")
public class UnderGoods extends BaseEntity<Long> {

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
	 * 批次号
	 */
	@Column(name = "bacth_number")
	private String bacthNumber;
	/**
	 * 产品数量
	 */
	@Column(name = "number")
	private Integer number;
	/**
	 * 备注
	 */
	@Column(name = "remarks")
	private String remarks;

	/**
	 * 状态
	 */
	@Column(name = "status")
	private Integer status;

	/**
	 * 下单时间
	 */
	@Column(name = "allot_time")
	private Date allotTime;

	/**
	 * 产品名称
	 */
	@Transient
	private String productName;

	/**
	 * 产品编号
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

	public Date getAllotTime() {
		return allotTime;
	}

	public void setAllotTime(Date allotTime) {
		this.allotTime = allotTime;
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

	public String getBacthNumber() {
		return bacthNumber;
	}

	public void setBacthNumber(String bacthNumber) {
		this.bacthNumber = bacthNumber;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
