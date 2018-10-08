package com.bluewhite.system.sys.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.product.product.entity.Product;
@Entity
@Table(name = "sys_files")
public class Files extends BaseEntity<Long>{


	/**
	 * 文件名称
	 */
	@Column(name = "name")
	private String name;
	/**
	 * 大小
	 */
	@Column(name = "size")
	private Long size;
	/**
	 * 类型
	 */
	@Column(name = "type")
	private String type;
	/**
	 * 文件地址
	 */
	@Column(name = "url")
	private String url;
	

	/**
	 * 产品id
	 */
	@Column(name = "product_id")
	private Long productId;

	/**
	 * 产品
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", referencedColumnName = "id", updatable = false, insertable = false)
	private Product product;
	

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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
	






}