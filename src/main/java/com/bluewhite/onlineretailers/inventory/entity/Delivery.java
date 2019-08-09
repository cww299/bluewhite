package com.bluewhite.onlineretailers.inventory.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.ledger.entity.Customer;
/**
 * 电商发货单
 * @author zhangliang
 *
 */
@Entity
@Table(name = "online_delivery")
public class Delivery extends BaseEntity<Long>{
	
	
	
	/**
	 * 订单id
	 * 
	 */
	@Column(name = "online_order_id")
	private Long onlineOrderId;
	

	/**
	 * 父订单实体
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "online_order_id", referencedColumnName = "id", insertable = false, updatable = false)
	private OnlineOrder onlineOrder;
	

	/**
	 * 数量
	 */
	@Column(name = "sum_number")
	private Integer sumNumber;
	
	/**
	 * 子发货单list
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
	@JoinColumn(name = "delivery_id")
	private List<DeliveryChild> deliveryChilds = new ArrayList<>();
	
	/**
	 * 运单号
	 */
	@Column(name = "tracking_number")
	private String trackingNumber;
	
	/**
	 * 客户id
	 * 
	 */
	@Column(name = "online_customer_id")
	private Long onlineCustomerId;
	

	/**
	 * 客户
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "online_customer_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Customer onlineCustomer;
	
	
	

	public Long getOnlineCustomerId() {
		return onlineCustomerId;
	}


	public void setOnlineCustomerId(Long onlineCustomerId) {
		this.onlineCustomerId = onlineCustomerId;
	}


	public Customer getOnlineCustomer() {
		return onlineCustomer;
	}


	public void setOnlineCustomer(Customer onlineCustomer) {
		this.onlineCustomer = onlineCustomer;
	}


	public String getTrackingNumber() {
		return trackingNumber;
	}


	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}


	public List<DeliveryChild> getDeliveryChilds() {
		return deliveryChilds;
	}


	public void setDeliveryChilds(List<DeliveryChild> deliveryChilds) {
		this.deliveryChilds = deliveryChilds;
	}


	public Long getOnlineOrderId() {
		return onlineOrderId;
	}


	public void setOnlineOrderId(Long onlineOrderId) {
		this.onlineOrderId = onlineOrderId;
	}


	public OnlineOrder getOnlineOrder() {
		return onlineOrder;
	}


	public void setOnlineOrder(OnlineOrder onlineOrder) {
		this.onlineOrder = onlineOrder;
	}


	public Integer getSumNumber() {
		return sumNumber;
	}


	public void setSumNumber(Integer sumNumber) {
		this.sumNumber = sumNumber;
	}




	
	

}
