package com.bluewhite.ledger.entity;

import java.util.Date;

/**
 * 账单清算实体
 * 
 * @author zhangliang
 *
 */
public class Bill {
	
	/**
	 * 客户id
	 * 
	 */
	private Long customerId;

	/**
	 * 客户name
	 * 
	 */
	private String customerName;

	/**
	 * 账单日期
	 */
	private Date billDate;

	/**
	 * 货款总值
	 */
	private Double offshorePay;

	/**
	 * 客户认可货款
	 */
	private Double acceptPay;

	/**
	 * 杂支应付
	 */
	private Double acceptPayable;

	/**
	 * 争议货款
	 */
	private Double disputePay;

	/**
	 * 未到货款
	 */
	private Double nonArrivalPay;

	/**
	 * 客户多付货款
	 */
	private Double overpaymentPay;
	
	/**
	 * 已到货款
	 */
	private Double arrivalPay;

	/**
	 * 查询字段
	 */
	private Date orderTimeBegin;
	/**
	 * 查询字段
	 */
	private Date orderTimeEnd;
	
	/**
	 * 是否发货
	 */
	private Integer flag;
	/**
	 * 是否审核
	 */
	private boolean audit;
	
	

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public boolean isAudit() {
		return audit;
	}

	public void setAudit(boolean audit) {
		this.audit = audit;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
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

	public Double getArrivalPay() {
		return arrivalPay;
	}

	public void setArrivalPay(Double arrivalPay) {
		this.arrivalPay = arrivalPay;
	}

	public Date getBillDate() {
		return billDate;
	}

	public void setBillDate(Date billDate) {
		this.billDate = billDate;
	}

	public Double getOffshorePay() {
		return offshorePay;
	}

	public void setOffshorePay(Double offshorePay) {
		this.offshorePay = offshorePay;
	}

	public Double getAcceptPay() {
		return acceptPay;
	}

	public void setAcceptPay(Double acceptPay) {
		this.acceptPay = acceptPay;
	}

	public Double getAcceptPayable() {
		return acceptPayable;
	}

	public void setAcceptPayable(Double acceptPayable) {
		this.acceptPayable = acceptPayable;
	}

	public Double getDisputePay() {
		return disputePay;
	}

	public void setDisputePay(Double disputePay) {
		this.disputePay = disputePay;
	}

	public Double getNonArrivalPay() {
		return nonArrivalPay;
	}

	public void setNonArrivalPay(Double nonArrivalPay) {
		this.nonArrivalPay = nonArrivalPay;
	}

	public Double getOverpaymentPay() {
		return overpaymentPay;
	}

	public void setOverpaymentPay(Double overpaymentPay) {
		this.overpaymentPay = overpaymentPay;
	}


}
