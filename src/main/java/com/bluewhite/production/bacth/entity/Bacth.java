package com.bluewhite.production.bacth.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.product.entity.Product;

/**
 * 产品批次实体
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_bacth")
public class Bacth extends BaseEntity<Long>{
	
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
     * 该批次产品数量
     */
	@Column(name = "number")
    private Integer number;
    /**
     * 备注
     */
	@Column(name = "remarks")
    private String remarks;
    /**
     * 状态，是否完成（0=未完成，1=完成）
     */
	@Column(name = "status")
    private Integer status = 0;
	
	/**
	 * 工序所属部门类型 (1=一楼质检，2=一楼包装，3=二楼针工)
	 */
	@Column(name = "type")
	private Integer type;
	
    /**
     * 批次外发价格
     */
	@Column(name = "bacth_hair_price")
    private Double bacthHairPrice;
	
    /**
     * 批次部门预计生产价格
     */
	@Column(name = "bacth_department_price")
    private Double bacthDepartmentPrice;
	
	/**
	 * 产品名称
	 */
	@Transient
	private String name;
	
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
    
	
	
	
	
	public Double getBacthDepartmentPrice() {
		return bacthDepartmentPrice;
	}
	public void setBacthDepartmentPrice(Double bacthDepartmentPrice) {
		this.bacthDepartmentPrice = bacthDepartmentPrice;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
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
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Double getBacthHairPrice() {
		return bacthHairPrice;
	}
	public void setBacthHairPrice(Double bacthHairPrice) {
		this.bacthHairPrice = bacthHairPrice;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProductNumber() {
		return productNumber;
	}
	public void setProductNumber(String productNumber) {
		this.productNumber = productNumber;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
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
