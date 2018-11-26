package com.bluewhite.finance.ledger.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;

/**
 * 账单清算实体
 * 
 * @author zhangliang
 *
 */
@Entity
@Table(name = "fin_ledger_bill")
public class Bill extends BaseEntity<Long>{

	/**
	 * 乙方Id
	 */
	@Column(name = "party_names_id")
	private Long partyNamesId;

	/**
	 * 乙方
	 */
	@Column(name = "party_names")
	private String partyNames;

	/**
	 * 当表已确定离岸货款值
	 */
	@Column(name = "offshore_pay")
	private Double offshorePay;

	/**
	 * 当表经业务员跟进客户已认可的货款
	 */
	@Column(name = "accept_pay")
	private Double acceptPay;

	/**
	 * 当表双方都认可的除货款以外的应付
	 */
	@Column(name = "accept_payable")
	private Double acceptPayable;

	/**
	 * 当表在途和有争议货款
	 */
	@Column(name = "dispute_pay")
	private Double disputePay;

	/**
	 * 当月未到货款
	 */
	@Column(name = "non_arrival_pay")
	private Double nonArrivalPay;

	/**
	 * 当月客户多付货款转下月应付
	 */
	@Column(name = "overpayment_pay")
	private Double overpaymentPay;

	/**
	 * 所有日期,所对应的付款(以及对应的批注)
	 * 
	 * @return
	 */
	@Column(name = "date_to_pay")
	private String dateToPay;

	public Long getPartyNamesId() {
		return partyNamesId;
	}

	public void setPartyNamesId(Long partyNamesId) {
		this.partyNamesId = partyNamesId;
	}

	public String getPartyNames() {
		return partyNames;
	}

	public void setPartyNames(String partyNames) {
		this.partyNames = partyNames;
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

	public String getDateToPay() {
		return dateToPay;
	}

	public void setDateToPay(String dateToPay) {
		this.dateToPay = dateToPay;
	}

}
