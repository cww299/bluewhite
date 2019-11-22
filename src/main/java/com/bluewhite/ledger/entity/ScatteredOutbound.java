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

/**
 * 耗料出库记录 （采购单虚拟库存）
 * 
 * 采购部将所有已经拥有库存的耗料表生成分散出库记录表
 *
 */
@Entity
@Table(name = "ledger_scattered_outbound")
public class ScatteredOutbound extends BaseEntity<Long> {

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
	private OrderProcurement orderProcurement;

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
	 * 领取用量对应数量
	 */
	@Column(name = "dosageNumber")
	private Integer dosageNumber;
	
	/**
	 * 领取用量对应数量
	 */
	@Column(name = "residue_dosageNumber")
	private Integer residueDosageNumber;
	
	/**
	 * 领取用量
	 */
	@Column(name = "dosage")
	private Double dosage;
	
	/**
	 * 剩余领取用量
	 */
	@Column(name = "residue_dosage")
	private Double residueDosage;

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
	
	

	public Integer getResidueDosageNumber() {
		return residueDosageNumber;
	}

	public void setResidueDosageNumber(Integer residueDosageNumber) {
		this.residueDosageNumber = residueDosageNumber;
	}

	public Integer getDosageNumber() {
		return dosageNumber;
	}

	public void setDosageNumber(Integer dosageNumber) {
		this.dosageNumber = dosageNumber;
	}

	public Double getResidueDosage() {
		return residueDosage;
	}

	public void setResidueDosage(Double residueDosage) {
		this.residueDosage = residueDosage;
	}

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
		return orderProcurement;
	}

	public void setOrderProcurement(OrderProcurement orderProcurement) {
		this.orderProcurement = orderProcurement;
	}

	public Double getDosage() {
		return dosage;
	}

	public void setDosage(Double dosage) {
		this.dosage = dosage;
	}

}
