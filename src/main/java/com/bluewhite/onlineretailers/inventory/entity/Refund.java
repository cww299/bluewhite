package com.bluewhite.onlineretailers.inventory.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;

/**
 * 电商退货单实体
 * @author zhangliang
 *
 */
@Entity
@Table(name = "online_refund")
public class Refund extends BaseEntity<Long>{
	
	/**
	 * 退款单号
	 */
	@Column(name="refund_id")
	private String refundId;
	
	/**
	 * 淘宝交易单号
	 */
	@Column(name="tid")
	private String tid;
	
	/**
	 * 子订单号。如果是单笔交易oid会等于tid
	 */
	@Column(name="oid")
	private String oid;
	
	/**
	 * 交易总金额。精确到2位小数;单位:元。如:200.07，表示:200元7分
	 */
	@Column(name="total_fee")
	private String totalFee;
	
	/**
	 * 退款状态。可选值
	 * WAIT_SELLER_AGREE(买家已经申请退款，等待卖家同意) 
	 * WAIT_BUYER_RETURN_GOODS(卖家已经同意退款，等待买家退货) 
	 * WAIT_SELLER_CONFIRM_GOODS(买家已经退货，等待卖家确认收货) 
	 * SELLER_REFUSE_BUYER(卖家拒绝退款) 
	 * CLOSED(退款关闭) 
	 * SUCCESS(退款成功)
	 */
	@Column(name="status")
	private String status;
	
	/**
	 * 退款对应的订单交易状态。可选值
	 * TRADE_NO_CREATE_PAY(没有创建支付宝交易) 
	 * WAIT_BUYER_PAY(等待买家付款) 
	 * WAIT_SELLER_SEND_GOODS(等待卖家发货,即:买家已付款) 
	 * WAIT_BUYER_CONFIRM_GOODS(等待买家确认收货,即:卖家已发货) 
	 * TRADE_BUYER_SIGNED(买家已签收,货到付款专用) 
	 * TRADE_FINISHED(交易成功) 
	 * TRADE_CLOSED(交易关闭) 
	 * TRADE_CLOSED_BY_TAOBAO(交易被淘宝关闭) 
	 */
	@Column(name="order_status")
	private String orderStatus;
	
	/**
	 * 支付给卖家的金额(交易总金额-退还给买家的金额)。精确到2位小数;单位:元。如:200.07，表示:200元7分
	 */
	@Column(name="payment")
	private String payment;
	
	/**
	 * 退款原因
	 */
	@Column(name="reason")
	private String reason;
	
	/**
	 * 退款说明
	 */
	@Column(name="desc")
	private String desc;

	public String getRefundId() {
		return refundId;
	}

	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	
	
	
	
}
