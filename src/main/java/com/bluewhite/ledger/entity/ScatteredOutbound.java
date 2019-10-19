package com.bluewhite.ledger.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
	 *  跟单人id
	 * 
	 */
	@Column(name = "user_id")
	private Long userId;

	/**
	 *  跟单人
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
