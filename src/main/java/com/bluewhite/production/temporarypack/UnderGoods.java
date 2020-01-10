package com.bluewhite.production.temporarypack;

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
 * 下货单
 * @author zhangliang
 * @date 2020/01/10
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
	 * 状态(是否已经完成)
	 */
	@Column(name = "status")
	private Integer status;

	/**
	 * 下单时间
	 */
	@Column(name = "allot_time")
	private Date allotTime;
	
	/**
	 * 是否天猫
	 */
	@Column(name = "internal")
	private Integer internal;


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
	 * 剩余量化数量
	 */
	@Transient
	private Integer surplusStickNumber;
	
	/**
	 * 剩余发货数量
	 */
	@Transient
	private Integer surplusSendNumber;

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

	
	
	public Integer getInternal() {
		return internal;
	}

	public void setInternal(Integer internal) {
		this.internal = internal;
	}

	public Integer getSurplusStickNumber() {
		return surplusStickNumber;
	}

	public void setSurplusStickNumber(Integer surplusStickNumber) {
		this.surplusStickNumber = surplusStickNumber;
	}

	public Integer getSurplusSendNumber() {
		return surplusSendNumber;
	}

	public void setSurplusSendNumber(Integer surplusSendNumber) {
		this.surplusSendNumber = surplusSendNumber;
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
