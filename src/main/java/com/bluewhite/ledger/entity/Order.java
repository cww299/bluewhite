package com.bluewhite.ledger.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.product.product.entity.Product;

/**
 * 订单（下单合同）
 * 
 * @author zhangliang
 *
 */
@Entity
@Table(name = "ledger_order")
public class Order extends BaseEntity<Long> {

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
	 * 下单类型id
	 */
	@Column(name = "order_type_id")
	private Long orderTypeId;

	/**
	 * 下单类型
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_type_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseData orderType;

	/**
	 * 面料的订购记录(一个订单耗料可以拥有多个采购单)
	 */
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<OrderMaterial> orderMaterials = new HashSet<OrderMaterial>();
	
	/**
	 * 销售子单
	 */
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<OrderChild> orderChilds = new HashSet<OrderChild>();


	/**
	 * 合同总数量
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
	 * 是否审核（0=未审核，1=已审核）（审核成功后进入生产计划环节）
	 */
	@Column(name = "audit")
	private Integer audit;

	/**
	 * 是否备料充足，可以进行外发单的生成
	 */
	@Column(name = "prepare_enough")
	private Integer prepareEnough;

	/**
	 * 客户name
	 * 
	 */
	@Transient
	private String customerName;

	/**
	 * 子单新增
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

	/**
	 * 产品name
	 */
	@Transient
	private String productName;
	
	/**
	 * 产品编号
	 */
	@Transient
	private String productNumber;
	
	

	public Set<OrderChild> getOrderChilds() {
		return orderChilds;
	}

	public void setOrderChilds(Set<OrderChild> orderChilds) {
		this.orderChilds = orderChilds;
	}

	public Long getOrderTypeId() {
		return orderTypeId;
	}

	public void setOrderTypeId(Long orderTypeId) {
		this.orderTypeId = orderTypeId;
	}

	public BaseData getOrderType() {
		return orderType;
	}

	public void setOrderType(BaseData orderType) {
		this.orderType = orderType;
	}

	public Integer getPrepareEnough() {
		return prepareEnough;
	}

	public void setPrepareEnough(Integer prepareEnough) {
		this.prepareEnough = prepareEnough;
	}

	public Set<OrderMaterial> getOrderMaterials() {
		return orderMaterials;
	}

	public void setOrderMaterials(Set<OrderMaterial> orderMaterials) {
		this.orderMaterials = orderMaterials;
	}

	public Integer getAudit() {
		return audit;
	}

	public void setAudit(Integer audit) {
		this.audit = audit;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

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
