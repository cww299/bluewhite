package com.bluewhite.onlineretailers.inventory.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.system.user.entity.Role;

/**
 * 电商订单实体
 * @author zhangliang
 *
 */
@Entity
@Table(name = "online_order")
public class OnlineOrder extends BaseEntity<Long>{
	
	
	/**
	 * 单据号
	 */
	@Column(name = "document_number")
	private String documentNumber;
	
	/**
	 * 订单编号
	 * 
	 */
	@Column(name = "number")
	private String number;
	
	/**
	 * 订单状态
	 * 
	 */
	@Column(name = "status")
	private Integer status;
	
	
	/**
	 * 商品集合 （一个订单可以有多个商品）
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "online_order_commodity", joinColumns = @JoinColumn(name = "online_order_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private Set<Commodity> roles = new HashSet<Commodity>();
	
	
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
	private OnlineCustomer onlineCustomer;
	
	
	/**
	 * 邮费
	 */
	@Column(name = "franking")
	private Double franking;
	
	/**
	 * 实收金额
	 */
	@Column(name = "amountMoney")
	private String amountMoney;
	
	/**
	 * 件数
	 */
	@Column(name = "countable")
	private Integer countable;
	
	
	/**
	 * 运单号
	 */
	@Column(name = "tracking_number")
	private String trackingNumber;
	
	
	/**
	 * 买家留言
	 * 
	 */
	@Column(name = "buyer_remarks")
	private String buyerRemarks;
	
	/**
	 * 卖家备注
	 * 
	 */
	@Column(name = "seller_remarks")
	private String sellerRemarks;
	
	/**
	 * 整单优惠
	 */

	@Column(name = "all_bill_preferential")
	private Double allBillPreferential;
	
	/**
	 * 系统优惠
	 */
	@Column(name = "system_preferential")
	private Double systemPreferential;
	
	
	/**
	 * 卖家调价
	 */
	@Column(name = "seller_readjust_prices")
	private Double sellerReadjustPrices;
	
	/**
	 * 实际金额
	 */
	@Column(name = "actual_sum")
	private Double actualSum;

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Set<Commodity> getRoles() {
		return roles;
	}

	public void setRoles(Set<Commodity> roles) {
		this.roles = roles;
	}

	public Long getOnlineCustomerId() {
		return onlineCustomerId;
	}

	public void setOnlineCustomerId(Long onlineCustomerId) {
		this.onlineCustomerId = onlineCustomerId;
	}

	public OnlineCustomer getOnlineCustomer() {
		return onlineCustomer;
	}

	public void setOnlineCustomer(OnlineCustomer onlineCustomer) {
		this.onlineCustomer = onlineCustomer;
	}

	public Double getFranking() {
		return franking;
	}

	public void setFranking(Double franking) {
		this.franking = franking;
	}

	public String getAmountMoney() {
		return amountMoney;
	}

	public void setAmountMoney(String amountMoney) {
		this.amountMoney = amountMoney;
	}

	public Integer getCountable() {
		return countable;
	}

	public void setCountable(Integer countable) {
		this.countable = countable;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}


	public String getBuyerRemarks() {
		return buyerRemarks;
	}

	public void setBuyerRemarks(String buyerRemarks) {
		this.buyerRemarks = buyerRemarks;
	}

	public String getSellerRemarks() {
		return sellerRemarks;
	}

	public void setSellerRemarks(String sellerRemarks) {
		this.sellerRemarks = sellerRemarks;
	}

	public Double getAllBillPreferential() {
		return allBillPreferential;
	}

	public void setAllBillPreferential(Double allBillPreferential) {
		this.allBillPreferential = allBillPreferential;
	}

	public Double getSystemPreferential() {
		return systemPreferential;
	}

	public void setSystemPreferential(Double systemPreferential) {
		this.systemPreferential = systemPreferential;
	}

	public Double getSellerReadjustPrices() {
		return sellerReadjustPrices;
	}

	public void setSellerReadjustPrices(Double sellerReadjustPrices) {
		this.sellerReadjustPrices = sellerReadjustPrices;
	}

	public Double getActualSum() {
		return actualSum;
	}

	public void setActualSum(Double actualSum) {
		this.actualSum = actualSum;
	}
		
	
	
	
	
	
	
	

}
