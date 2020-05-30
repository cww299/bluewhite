package com.bluewhite.finance.consumption.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.ledger.entity.Customer;
import com.bluewhite.ledger.entity.OrderOutSource;
import com.bluewhite.ledger.entity.OrderProcurement;
import com.bluewhite.production.temporarypack.SendOrder;
import com.bluewhite.system.user.entity.User;

/**
 * 财务账单
 * 
 * 1报销，2采购应付和预算，3工资，4税点应付和预算，5物流，6应付借款本金，7应付社保和税费，8应入库周转的材料，9应收周转中的资金，10借款利息 ，11外发加工单账单
 * 
 * 报销单(正常模式，由申请人自己申请，同时经过上级审核，审核成功后转到财务，进行报销，同时系统记录报销申请人和经办人以及财务同意放款人id)
 * 
 * （在这里目前只作为一个记账功能）
 * 
 * @author zhangliang
 *
 */
@Entity
@Table(name = "fin_consumption")
public class Consumption extends BaseEntity<Long> {
	
	
	/**
	 * 来源部门Id
	 */
	@Column(name = "orgName_id")
	private Long orgNameId;
	
	/**
	 * 来源部门
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orgName_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseData orgName;
	
	/**
	 * 父id
	 */
	@Column(name = "parent_id")
	private Long parentId;

	/**
	 * 消费类型(1.报销，2采购应付和预算，3工资，4税点应付和预算，5物流，6应付借款本金，7应付社保和税费，8应入库周转的材料，9应收周转中的资金，10借款利息 ，11外发加工单账单)
	 */
	@Column(name = "type")
	private Integer type;

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
	 * 消费内容
	 */
	@Column(name = "content")
	private String content;
	
	/**
	 * 税点
	 */
	@Column(name = "tax_point")
	private Double taxPoint;

	/**
	 * 是否是预算 0 = 否 1 =是
	 */
	@Column(name = "budget")
	private Integer budget;
	
	/**
	 * 月底是否删除（报销）
	 */
	@Column(name = "delete_flag")
	private Integer deleteFlag;

	/**
	 * (申请人申请时)金额
	 */
	@Column(name = "money")
	private Double money;

	/**
	 * (申请人申请时)申请日期
	 */
	@Column(name = "expense_date")
	private Date expenseDate;

	/**
	 * （财务付款）金额
	 */
	@Column(name = "payment_money")
	private Double paymentMoney;

	/**
	 * (财务付款日期)
	 */
	@Column(name = "payment_date")
	private Date paymentDate;
	
	/**
	 * 客户Id
	 */
	@Column(name = "customer_id")
	private Long customerId;
	
	/**
	 * 客户对象
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Customer customer;
	
	/**
	 * 扣款事由填写
	 */
	@Column(name = "withhold_reason")
	private String withholdReason;

	/**
	 * 扣款金额
	 */
	@Column(name = "withhold_money")
	private String withholdMoney;

	/**
	 * 结款模式选择
	 */
	@Column(name = "settle_accounts_mode")
	private Integer settleAccountsMode;

	/**
	 * 备注
	 */
	@Column(name = "remark")
	private String remark;

	/**
	 * 是否已付款（0=否，1=是，2，部分付款）
	 */
	@Column(name = "flag")
	private Integer flag;

	/**
	 * 实际付款时间
	 */
	@Column(name = "reality_date")
	private Date realityDate;
	
	/**
     * 预计付款时间
     */
    @Column(name = "expect_date")
    private Date expectDate;
	
	/**
	 * 采购应付账单id
	 * 
	 */
	@Column(name = "orderProcurement_id")
	private Long orderProcurementId;

	/**
	 *  采购应付账单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orderProcurement_id", referencedColumnName = "id", insertable = false, updatable = false)
	private OrderProcurement orderProcurement;

	/**
	 * 加工单id
	 * 
	 */
	@Column(name = "order_outSource_id")
	private Long orderOutSourceId;

	/**
	 * 加工单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_outSource_id", referencedColumnName = "id", insertable = false, updatable = false)
	private OrderOutSource orderOutSource;
	
    /**
     * 发货单id
     */
    @Column(name = "send_order_id")
    private Long sendOrderId;

    /**
     * 发货单
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "send_order_id", referencedColumnName = "id", insertable = false, updatable = false)
    private SendOrder sendOrder;
	
    /**
     * 物流点id
     */
    @Column(name = "logistics_id")
    private Long logisticsId;

    /**
     * 物流点
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "logistics_id", referencedColumnName = "id", insertable = false, updatable = false)
    private BaseData logistics;
	/**
	 * 过滤参数(报销人姓名)
	 */
	@Transient
	private String username;

	/**
	 * 过滤参数(客户姓名)
	 */
	@Transient
	private String customerName;
	
	/**
     * 过滤参数(业务员)
     */
    @Transient
    private String saleUserName;
	
	/**
     * 过滤参数物流点
     */
    @Transient
    private String logisticsName;
	
	/**
	 * 审核过滤
	 */
	@Transient
	private String flags;
	
	/**
	 * 生产计划单号
	 */
	@Transient
	private String orderNumber;

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

	/**
     * 查询字段(1=按日2=按月)
     */
    @Transient
    private Integer mode;
	
	
	
	public String getSaleUserName() {
        return saleUserName;
    }

    public void setSaleUserName(String saleUserName) {
        this.saleUserName = saleUserName;
    }

    public Date getExpectDate() {
        return expectDate;
    }

    public void setExpectDate(Date expectDate) {
        this.expectDate = expectDate;
    }

    public Integer getMode() {
        return mode;
    }

    public void setMode(Integer mode) {
        this.mode = mode;
    }

    public Long getSendOrderId() {
        return sendOrderId;
    }

    public void setSendOrderId(Long sendOrderId) {
        this.sendOrderId = sendOrderId;
    }

    public SendOrder getSendOrder() {
        return sendOrder;
    }

    public void setSendOrder(SendOrder sendOrder) {
        this.sendOrder = sendOrder;
    }

    public String getLogisticsName() {
        return logisticsName;
    }

    public void setLogisticsName(String logisticsName) {
        this.logisticsName = logisticsName;
    }

    public Long getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(Long logisticsId) {
        this.logisticsId = logisticsId;
    }

    public BaseData getLogistics() {
        return logistics;
    }

    public void setLogistics(BaseData logistics) {
        this.logistics = logistics;
    }

    public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public Long getOrderOutSourceId() {
		return orderOutSourceId;
	}

	public void setOrderOutSourceId(Long orderOutSourceId) {
		this.orderOutSourceId = orderOutSourceId;
	}

	public OrderOutSource getOrderOutSource() {
		return orderOutSource;
	}

	public void setOrderOutSource(OrderOutSource orderOutSource) {
		this.orderOutSource = orderOutSource;
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

	public BaseData getOrgName() {
		return orgName;
	}

	public void setOrgName(BaseData orgName) {
		this.orgName = orgName;
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

	public String getFlags() {
		return flags;
	}

	public void setFlags(String flags) {
		this.flags = flags;
	}

	public Date getRealityDate() {
		return realityDate;
	}

	public void setRealityDate(Date realityDate) {
		this.realityDate = realityDate;
	}


	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Long getOrgNameId() {
		return orgNameId;
	}

	public void setOrgNameId(Long orgNameId) {
		this.orgNameId = orgNameId;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}


	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Double getTaxPoint() {
		return taxPoint;
	}

	public void setTaxPoint(Double taxPoint) {
		this.taxPoint = taxPoint;
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
