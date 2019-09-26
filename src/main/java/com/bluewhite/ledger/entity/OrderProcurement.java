package com.bluewhite.ledger.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.basedata.entity.BaseData;

/**
 * 采购（采购面辅料订单）(由生产下单用料转化得到)
 * 当生产耗料库存不足时，生成采购单
 * @author zhangliang
 *
 */
@Entity
@Table(name = "ledger_order_material")
public class OrderProcurement extends BaseEntity<Long>{
	
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
	 * 下单日期
	 */
	@Column(name = "place_order_time")
	private Date placeOrderTime;
	
	
	/**
	 * 到货日期
	 */
	@Column(name = "arrival_time")
	private Date arrivalTime;
	
	/**
	 * 客户id
	 * 
	 */
	@Column(name = "customer_id")
	private Long customerId;
	

	/**
	 * 客户
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Customer customer;


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


	public Date getPlaceOrderTime() {
		return placeOrderTime;
	}


	public void setPlaceOrderTime(Date placeOrderTime) {
		this.placeOrderTime = placeOrderTime;
	}


	public Date getArrivalTime() {
		return arrivalTime;
	}


	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
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
	
	


}
