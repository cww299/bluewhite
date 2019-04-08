package com.bluewhite.finance.expenseAccount.entity;

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
 * 
 * 
 * 财务 报销单(正常模式，由申请人自己申请，同时经过上级审核，审核成功后转到财务，进行报销，同时系统记录报销申请人和经办人以及财务同意放款人id)
 * （在这里目前只作为一个记账功能）
 * @author zhangliang
 *
 */
@Entity
@Table(name = "fin_expense_account" )
public class ExpenseAccount  extends BaseEntity<Long>{
	
	
	/**
	 * 报销人Id
	 */
	@Column(name = "user_id")
    private Long userId;
	
	
	/**
	 * 报销人
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
	private User user;
	
	/**
	 * 报销内容
	 */
	@Column(name = "content")
    private String content;
	
	
	/**
	 * 是否预算（0=否，1=是）
	 */
	@Column(name = "budget")
    private Integer budget;
	
	/**
	 * (申请人申请时)报销金额
	 */
	@Column(name = "money")
    private Double money;
	
	/**
	 * (申请人申请时)申请报销日期
	 */
	@Column(name = "expense_date")
    private Date  expenseDate;
	
	/**
	 * （财务付款）报销金额
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
	 * 该报销单是否已付款报销（0=否，1=是）
	 */
	@Column(name = "flag")
    private Integer flag;
	
	/**
	 * 过滤参数(报销人姓名)
	 */
	@Transient
    private String username;
	
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


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
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


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public Integer getBudget() {
		return budget;
	}


	public void setBudget(Integer budget) {
		this.budget = budget;
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
