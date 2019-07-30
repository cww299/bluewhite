package com.bluewhite.product.product.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.product.primecost.primecost.entity.PrimeCost;
import com.bluewhite.system.sys.entity.Files;

/**
 * 
 * @author zhangliang
 *
 */
@Entity
@Table(name = "sys_product")
public class Product extends BaseEntity<Long>{
	
	/**
	 * 产品编号
	 */
	@Column(name = "number")
    private String number;
	
    /**
     * 产品名
     */
	@Column(name = "name")
    private String name;
	
    /**
     * 产品图片地址
     */
	@Column(name = "url")
    private String url;
	
	/**
	 * 产品图片
	 */
	@OneToMany(mappedBy = "product",cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Files> productFile = new HashSet<Files>();
	
    @OneToOne(fetch=FetchType.LAZY,cascade = CascadeType.ALL) 
    @JoinColumn(name="prime_cost_id",referencedColumnName="id",nullable=true)
    @NotFound(action=NotFoundAction.IGNORE)
	private PrimeCost primeCost;
	
    /**
     * 各个部门自己的产品编号
     */
 	@Column(name = "department_number")
    private String departmentNumber;
    
   /**
    * 产品来源部门
    */
	@Column(name = "origin_department")
    private String originDepartment;
	
   /**
    * 产品分类(1=成品，2=皮壳)
    */
	@Column(name = "product_type")
    private Integer productType;
	 
	/**
	 * 是否拥有版权
	 */
	@Column(name = "copyright")
	private boolean copyright = false;
	
	/**
	 * 八号仓库特殊业务，同一种产品会有会有激光和冲床两种类型工序，同时会产生不同的外发单价（0=激光，1=冲床）
	 */
	@Transient
	private Integer sign; 
	
    /**
     * 产品本身外发价格
     */
	@Transient
    private Double hairPrice;
	
    /**
     * 当部门预计生产价格
     */
	@Transient
    private Double departmentPrice;
	
    /**
     * 产品本身外发价格()
     */
	@Transient
    private Double puncherHairPrice;
	
    /**
     * 当部门预计生产价格()
     */
	@Transient
    private Double puncherDepartmentPrice;
	
	/**
	 * 工序部门类型
	 */
	@Transient
	private Integer type;	
	
	/**
	 * 针工价格
	 */
	@Transient
	private Double deedlePrice;
	
	
	
	
	public boolean isCopyright() {
		return copyright;
	}

	public void setCopyright(boolean copyright) {
		this.copyright = copyright;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}

	public String getDepartmentNumber() {
		return departmentNumber;
	}

	public void setDepartmentNumber(String departmentNumber) {
		this.departmentNumber = departmentNumber;
	}

	public String getOriginDepartment() {
		return originDepartment;
	}

	public void setOriginDepartment(String originDepartment) {
		this.originDepartment = originDepartment;
	}

	public PrimeCost getPrimeCost() {
		return primeCost;
	}

	public void setPrimeCost(PrimeCost primeCost) {
		this.primeCost = primeCost;
	}

	public Integer getSign() {
		return sign;
	}

	public void setSign(Integer sign) {
		this.sign = sign;
	}

	public Double getPuncherHairPrice() {
		return puncherHairPrice;
	}

	public void setPuncherHairPrice(Double puncherHairPrice) {
		this.puncherHairPrice = puncherHairPrice;
	}

	public Double getPuncherDepartmentPrice() {
		return puncherDepartmentPrice;
	}

	public void setPuncherDepartmentPrice(Double puncherDepartmentPrice) {
		this.puncherDepartmentPrice = puncherDepartmentPrice;
	}

	public Double getDeedlePrice() {
		return deedlePrice;
	}

	public void setDeedlePrice(Double deedlePrice) {
		this.deedlePrice = deedlePrice;
	}

	public Double getHairPrice() {
		return hairPrice;
	}

	public void setHairPrice(Double hairPrice) {
		this.hairPrice = hairPrice;
	}

	public Double getDepartmentPrice() {
		return departmentPrice;
	}

	public void setDepartmentPrice(Double departmentPrice) {
		this.departmentPrice = departmentPrice;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Set<Files> getProductFile() {
		return productFile;
	}

	public void setProductFile(Set<Files> productFile) {
		this.productFile = productFile;
	}

	
	
	

}
