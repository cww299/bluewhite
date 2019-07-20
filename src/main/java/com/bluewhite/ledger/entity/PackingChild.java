package com.bluewhite.ledger.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.onlineretailers.inventory.entity.OnlineOrder;
import com.bluewhite.product.product.entity.Product;

/**
 * 贴包 子单
 * 
 * @author zhangliang
 *
 */
@Entity
@Table(name = "ledger_packing_child")
public class PackingChild extends BaseEntity<Long>{

	/**
	 * 贴包父类id
	 * 
	 */
	@Column(name = "packing_id")
	private Long packingId;

	/**
	 * 贴包父类
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
	 * 数量
	 */
	@Column(name = "number")
	private Integer number;

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
