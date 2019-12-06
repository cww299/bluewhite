
package com.bluewhite.production.temporarypack;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.ledger.entity.PackingChild;
import com.bluewhite.product.product.entity.Product;

/**
 * 量化单
 * 
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_quantitative")
public class Quantitative extends BaseEntity<Long> {
	
	/**
	 * 量化编号
	 */
	@Column(name = "quantitative_number")
	private String quantitativeNumber;
	
	/**
	 * 包装时间
	 */
	@Column(name = "time")
	private Date time;
	
	/**
	 * 子单list
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
	@JoinColumn(name = "quantitative_id")
	private List<QuantitativeChild> quantitativeChilds = new ArrayList<>();
	
	/**
	 * 产品名称
	 */
	@Transient
	private String name;
	
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
	
	/**
	 * 子单
	 */
	@Transient
	private String child;
	
	/**
	 * 批次号
	 */
	@Transient
	private String bacthNumber;
	
	
	public String getBacthNumber() {
		return bacthNumber;
	}

	public void setBacthNumber(String bacthNumber) {
		this.bacthNumber = bacthNumber;
	}

	public List<QuantitativeChild> getQuantitativeChilds() {
		return quantitativeChilds;
	}

	public void setQuantitativeChilds(List<QuantitativeChild> quantitativeChilds) {
		this.quantitativeChilds = quantitativeChilds;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getChild() {
		return child;
	}

	public void setChild(String child) {
		this.child = child;
	}

	public String getQuantitativeNumber() {
		return quantitativeNumber;
	}

	public void setQuantitativeNumber(String quantitativeNumber) {
		this.quantitativeNumber = quantitativeNumber;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}
