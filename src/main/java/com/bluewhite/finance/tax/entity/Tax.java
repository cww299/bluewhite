package com.bluewhite.finance.tax.entity;

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
 * 财务 税点
 * 
 * @author zhangliang
 *
 */
@Entity
@Table(name = "fin_tax" )
public class Tax  extends BaseEntity<Long>{
	
	
	
	/**
	 * 供应商名称
	 */
	@Column(name = "content")
    private String content;
	
	
	/**
	 * 税点
	 */
	@Column(name = "tax_point")
    private Double taxPoint;
	
	/**
	 * 票面金额
	 */
	@Column(name = "money")
    private Double money;
	
	/**
	 * 申请日期
	 */
	@Column(name = "expense_date")
    private Date  expenseDate;
	
	/**
	 * （财务付款）付款日要付金额
	 */
	@Column(name = "payment_money")
    private Double paymentMoney;
	
	/**
	 * (财务付款日期)
	 */
	@Column(name = "payment_date")
    private Date  paymentDate;
	
	
	/**
	 * 扣款事由填写	
	 */
	@Column(name = "withhold_reason")
    private String  withholdReason;
	
	/**
	 * 扣款金额
	 */
	@Column(name = "withhold_money")
    private String  withholdMoney;
	
	/**
	 * 结款模式选择
	 */
	@Column(name = "settle_accounts_mode")
    private Integer  settleAccountsMode;
	
	
	/**
	 * 备注
	 */
	@Column(name = "remark")
    private String remark;
	
	/**
	 * 是否已付款（0=否，1=是）
	 */
	@Column(name = "flag")
    private Integer flag;
	
	
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


	public Double getPaymentMoney() {
		return paymentMoney;
	}


	public void setPaymentMoney(Double paymentMoney) {
		this.paymentMoney = paymentMoney;
	}


	public Integer getFlag() {
		return flag;
	}


	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public Double getMoney() {
		return money;
	}


	public void setMoney(Double money) {
		this.money = money;
	}


	public Date getExpenseDate() {
		return expenseDate;
	}


	public void setExpenseDate(Date expenseDate) {
		this.expenseDate = expenseDate;
	}


	public Date getPaymentDate() {
		return paymentDate;
	}


	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}


	public String getWithholdReason() {
		return withholdReason;
	}


	public void setWithholdReason(String withholdReason) {
		this.withholdReason = withholdReason;
	}


	public String getWithholdMoney() {
		return withholdMoney;
	}


	public void setWithholdMoney(String withholdMoney) {
		this.withholdMoney = withholdMoney;
	}


	public Integer getSettleAccountsMode() {
		return settleAccountsMode;
	}


	public void setSettleAccountsMode(Integer settleAccountsMode) {
		this.settleAccountsMode = settleAccountsMode;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}



	
	

}
