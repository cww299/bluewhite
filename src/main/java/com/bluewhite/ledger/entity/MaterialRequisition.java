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
import com.bluewhite.system.user.entity.User;

/**
 * 领料单（1.领料单，2.外发领料单） 
 * 领料单由耗料单得到 采购部将所有已经拥有库存的耗料表生成领料单
 * 
 * 
 *
 */
@Entity
@Table(name = "ledger_material_requisition")
public class MaterialRequisition extends BaseEntity<Long> {
	
	
	/**
	 * 开单时间
	 */
	@Column(name = "open_order_time")
	private Date openOrderTime;

	/**
	 * 生产计划单id
	 * 
	 */
	@Column(name = "order_id")
	private Long orderId;

	/**
	 * 生产计划单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Order order;
	
	/**
	 * 领料单类型(1=领料单，2=补领单，3=退料单)
	 */
	@Column(name = "type")
	private Integer type;

	/**
	 * 领料单编号（SCLL+日期+当日下单数量）
	 */
	@Column(name = "requisition_number")
	private String requisitionNumber;

	/**
	 * 分散出库单id
	 */
	@Column(name = "scattered_outbound_id")
	private Long scatteredOutboundId;

	/**
	 * 分散出库单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "scattered_outbound_id", referencedColumnName = "id", insertable = false, updatable = false)
	private ScatteredOutbound scatteredOutbound;

	/**
	 * 领取加工点id
	 */
	@Column(name = "customer_id")
	private Long customerId;

	/**
	 * 领取加工点
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Customer customer;

	/**
	 * 领取人id
	 * 
	 */
	@Column(name = "user_id")
	private Long userId;

	/**
	 * 领取人
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
	private User user;

	/**
	 * 是否外发（0.领料单，1.外发领料单）
	 * 
	 */
	@Column(name = "outsource")
	private Integer outsource;

	/**
	 * 任务数量
	 */
	@Column(name = "process_number")
	private Integer processNumber;

	/**
	 * 领取用量
	 */
	@Column(name = "dosage")
	private Double dosage;

	/**
	 * 备注
	 */
	@Column(name = "remark")
	private String remark;

	/**
	 * 是否审核
	 */
	@Column(name = "audit")
	private Integer audit;

	/**
	 * 领取时间
	 */
	@Column(name = "requisition_time")
	private Date requisitionTime;

	/**
	 * 产品name
	 */
	@Transient
	private String productName;

	/**
	 * 跟单人name
	 * 
	 */
	@Transient
	private String userName;

	/**
	 * 加工点name
	 * 
	 */
	@Transient
	private String customerName;
	
	/**
	 * 采购单编号(批次+产品名称+物料名称+订货客户名称生成的新编号)
	 */
	@Transient
	private String orderProcurementNumber;

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
	 * 已领数量(通过出库单实际数量计算)
	 */
	@Transient
	private Double requisitionCount;
	
	
	
	
	public Double getRequisitionCount() {
		return requisitionCount;
	}

	public void setRequisitionCount(Double requisitionCount) {
		this.requisitionCount = requisitionCount;
	}

	public String getOrderProcurementNumber() {
		return orderProcurementNumber;
	}

	public void setOrderProcurementNumber(String orderProcurementNumber) {
		this.orderProcurementNumber = orderProcurementNumber;
	}

	public Date getOpenOrderTime() {
		return openOrderTime;
	}

	public void setOpenOrderTime(Date openOrderTime) {
		this.openOrderTime = openOrderTime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Date getRequisitionTime() {
		return requisitionTime;
	}

	public void setRequisitionTime(Date requisitionTime) {
		this.requisitionTime = requisitionTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getRequisitionNumber() {
		return requisitionNumber;
	}

	public void setRequisitionNumber(String requisitionNumber) {
		this.requisitionNumber = requisitionNumber;
	}

	public Long getScatteredOutboundId() {
		return scatteredOutboundId;
	}

	public void setScatteredOutboundId(Long scatteredOutboundId) {
		this.scatteredOutboundId = scatteredOutboundId;
	}

	public ScatteredOutbound getScatteredOutbound() {
		return scatteredOutbound;
	}

	public void setScatteredOutbound(ScatteredOutbound scatteredOutbound) {
		this.scatteredOutbound = scatteredOutbound;
	}

	public Integer getProcessNumber() {
		return processNumber;
	}

	public void setProcessNumber(Integer processNumber) {
		this.processNumber = processNumber;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Integer getOutsource() {
		return outsource;
	}

	public void setOutsource(Integer outsource) {
		this.outsource = outsource;
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

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
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

	public Double getDosage() {
		return dosage;
	}

	public void setDosage(Double dosage) {
		this.dosage = dosage;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
