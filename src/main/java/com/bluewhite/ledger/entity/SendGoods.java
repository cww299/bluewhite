package com.bluewhite.ledger.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.product.product.entity.Product;
/**
 * 发货单（待发货，已发货）
 * @author zhangliang
 *
 */
@Entity
@Table(name = "ledger_send_goods")
public class SendGoods extends BaseEntity<Long>{

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
	 * 预计发出数量
	 */
	@Column(name = "number")
	private Integer number;
	

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

}
