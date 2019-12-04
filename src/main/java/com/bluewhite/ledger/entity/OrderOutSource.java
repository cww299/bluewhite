package com.bluewhite.ledger.entity;

import java.util.Date;
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
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.onlineretailers.inventory.entity.Inventory;
import com.bluewhite.system.user.entity.User;

/**
 * 生产计划部 加工单 1.加工单 2.外发加工单
 * 
 * @author zhangliang
 *
 */
@Entity
@Table(name = "ledger_order_outsource")
public class OrderOutSource extends BaseEntity<Long> {

	/**
	 * 生产计划单id
	 * 
	 */
	@Column(name = "order_id")
	private Long orderId;

	/**
	 * 生产计划单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Order order;

	/**
	 * 开单时间
	 */
	@Column(name = "open_order_time")
	private Date openOrderTime;

	/**
	 * 工艺单内容填充，用于打印开单 1.棉花规格
	 */
	@Column(name = "fill")
	private String fill;

	/**
	 * 工艺单内容填充，用于打印开单 1.棉花备注
	 */
	@Column(name = "fill_remark")
	private String fillRemark;

	/**
	 * 工艺单内容填充，用于打印开单 棉花克重
	 */
	@Column(name = "gram_weight")
	private Double gramWeight;

	/**
	 * 工艺单内容填充，用于打印开单 棉花总克重（千克）
	 */
	@Column(name = "kilogram_weight")
	private Double kilogramWeight;

	/**
	 * 任务编号
	 * 
	 */
	@Column(name = "out_source_number")
	private String outSourceNumber;

	/**
	 * 任务工序多对多
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "ledger_outsource_task", joinColumns = @JoinColumn(name = "outsource_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id"))
	private Set<BaseData> outsourceTask = new HashSet<BaseData>();

	/**
	 * 任务数量
	 */
	@Column(name = "process_number")
	private Integer processNumber;

	/**
	 * 备注
	 */
	@Column(name = "remark")
	private String remark;

	/**
	 * 是否外发
	 * 
	 */
	@Column(name = "outsource")
	private Integer outsource;

	/**
	 * 加工点id
	 * 
	 */
	@Column(name = "customer_id")
	private Long customerId;

	/**
	 * 加工点
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Customer customer;

	/**
	 * （在家加工）加工人id
	 * 
	 */
	@Column(name = "processing_user_id")
	private Long processingUserId;

	/**
	 * 加工人
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "processing_user_id", referencedColumnName = "id", insertable = false, updatable = false)
	private User processingUser;

	/**
	 * 跟单人id（外协）
	 * 
	 */
	@Column(name = "user_id")
	private Long userId;

	/**
	 * 跟单人
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
	private User user;

	/**
	 * 是否审核
	 */
	@Column(name = "audit")
	private Integer audit;

	/**
	 * 是否出账
	 */
	@Column(name = "charge_off")
	private Integer chargeOff;

	/**
	 * (申请人申请时)金额
	 */
	@Transient
	private Double money;

	/**
	 * 产品name
	 */
	@Transient
	private String productName;

	/**
	 * 跟单人name
	 * 
	 */
	@Transient
	private String userName;

	/**
	 * 加工点name
	 * 
	 */
	@Transient
	private String customerName;

	/**
	 * 工序ids
	 */
	@Transient
	private String outsourceTaskIds;

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
	 * (申请人申请时)申请日期
	 */
	@Transient
	private Date expenseDate;

	/**
	 * 到货数量
	 */
	@Transient
	private Integer arrivalNumber;
	
	

	public Date getExpenseDate() {
		return expenseDate;
	}

	public void setExpenseDate(Date expenseDate) {
		this.expenseDate = expenseDate;
	}

	public Integer getArrivalNumber() {
		return arrivalNumber;
	}

	public void setArrivalNumber(Integer arrivalNumber) {
		this.arrivalNumber = arrivalNumber;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Integer getChargeOff() {
		return chargeOff;
	}

	public void setChargeOff(Integer chargeOff) {
		this.chargeOff = chargeOff;
	}

	public Long getProcessingUserId() {
		return processingUserId;
	}

	public void setProcessingUserId(Long processingUserId) {
		this.processingUserId = processingUserId;
	}

	public User getProcessingUser() {
		return processingUser;
	}

	public void setProcessingUser(User processingUser) {
		this.processingUser = processingUser;
	}

	public String getOutsourceTaskIds() {
		return outsourceTaskIds;
	}

	public void setOutsourceTaskIds(String outsourceTaskIds) {
		this.outsourceTaskIds = outsourceTaskIds;
	}

	public Set<BaseData> getOutsourceTask() {
		return outsourceTask;
	}

	public void setOutsourceTask(Set<BaseData> outsourceTask) {
		this.outsourceTask = outsourceTask;
	}

	public Integer getOutsource() {
		return outsource;
	}

	public void setOutsource(Integer outsource) {
		this.outsource = outsource;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getOutSourceNumber() {
		return outSourceNumber;
	}

	public void setOutSourceNumber(String outSourceNumber) {
		this.outSourceNumber = outSourceNumber;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
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

	public Integer getAudit() {
		return audit;
	}

	public void setAudit(Integer audit) {
		this.audit = audit;
	}

	public String getFill() {
		return fill;
	}

	public void setFill(String fill) {
		this.fill = fill;
	}

	public String getFillRemark() {
		return fillRemark;
	}

	public void setFillRemark(String fillRemark) {
		this.fillRemark = fillRemark;
	}

	public Double getGramWeight() {
		return gramWeight;
	}

	public void setGramWeight(Double gramWeight) {
		this.gramWeight = gramWeight;
	}

	public Double getKilogramWeight() {
		return kilogramWeight;
	}

	public void setKilogramWeight(Double kilogramWeight) {
		this.kilogramWeight = kilogramWeight;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	public Integer getProcessNumber() {
		return processNumber;
	}

	public void setProcessNumber(Integer processNumber) {
		this.processNumber = processNumber;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Date getOpenOrderTime() {
		return openOrderTime;
	}

	public void setOpenOrderTime(Date openOrderTime) {
		this.openOrderTime = openOrderTime;
	}

}
