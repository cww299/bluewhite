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
 * 分散出库记录
 * 
 * 采购部将所有已经拥有库存的耗料表生成出库分散记录表
 *
 */
@Entity
@Table(name = "ledger_scattered_outbound")
public class ScatteredOutbound extends BaseEntity<Long>{
	
	/**
	 * 分散出库编号
	 */
	@Column(name = "outbound_number")
	private String outboundNumber;
	
	/**
	 * 采购单id
	 */
	@Column(name = "order_procurement_id")
	private Long orderProcurementId;

	/**
	 * 采购单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_procurement_id", referencedColumnName = "id", insertable = false, updatable = false)
	private OrderProcurement OrderProcurement;
	
	/**
	 * 订单（下单合同）生产用料id
	 */
	@Column(name = "order_material_id")
	private Long orderMaterialId;

	/**
	 * 订单（下单合同）生产用料
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_material_id", referencedColumnName = "id", insertable = false, updatable = false)
	private OrderMaterial orderMaterial;
	
	/**
	 * 领取人
	 */
	@Column(name = "receive_user")
    private String receiveUser;
	
   	/**
	 * 跟单人id
	 * 
	 */
	@Column(name = "user_id")
	private Long userId;

	/**
	 * 跟单人
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
	private User user;
	
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
	 * 审核正式出库时间（当所有材料库存准备就绪，填写完正式日期，审核成功过后通知到生产计划部）
	 */
	@Column(name = "audit_time")
    private Date auditTime;
	
	/**
	 * 产品name
	 */
	@Transient
	private String productName;
	
	/**
	 * 下单合同id
	 */
	@Transient
	private Long orderId;
	
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
	
	

	public Integer getAudit() {
		return audit;
	}

	public void setAudit(Integer audit) {
		this.audit = audit;
	}

	public Date getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
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

	public String getOutboundNumber() {
		return outboundNumber;
	}

	public void setOutboundNumber(String outboundNumber) {
		this.outboundNumber = outboundNumber;
	}

	public Long getOrderMaterialId() {
		return orderMaterialId;
	}

	public void setOrderMaterialId(Long orderMaterialId) {
		this.orderMaterialId = orderMaterialId;
	}

	public OrderMaterial getOrderMaterial() {
		return orderMaterial;
	}

	public void setOrderMaterial(OrderMaterial orderMaterial) {
		this.orderMaterial = orderMaterial;
	}

	public Long getOrderProcurementId() {
		return orderProcurementId;
	}

	public void setOrderProcurementId(Long orderProcurementId) {
		this.orderProcurementId = orderProcurementId;
	}

	public OrderProcurement getOrderProcurement() {
		return OrderProcurement;
	}

	public void setOrderProcurement(OrderProcurement orderProcurement) {
		OrderProcurement = orderProcurement;
	}

	public String getReceiveUser() {
		return receiveUser;
	}

	public void setReceiveUser(String receiveUser) {
		this.receiveUser = receiveUser;
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
