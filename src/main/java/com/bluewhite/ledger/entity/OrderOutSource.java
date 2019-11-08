package com.bluewhite.ledger.entity;

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
import com.bluewhite.onlineretailers.inventory.entity.Inventory;
import com.bluewhite.system.user.entity.User;

/**
 * 生产计划部  加工单
 * 1.加工单
 * 2.外发加工单
 * @author zhangliang
 *
 */
@Entity
@Table(name = "ledger_order_outsource")
public class OrderOutSource extends BaseEntity<Long> {
	
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
	 *工艺单内容填充，用于打印开单  棉花克重
	 */
	@Column(name = "gram_weight")
	private Double gramWeight;
	
	/**
	 *工艺单内容填充，用于打印开单  棉花总克重（千克）
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
	 * 任务工序
	 * 
	 */
	@Column(name = "process")
	private String process;
	
	/**
	 * 任务工序ids
	 * 
	 */
	@Column(name = "process_ids")
	private String processIds;

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
	 * 跟单人id
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
	 * 订单id
	 * 
	 */
	@Column(name = "order_id")
	private Long orderId;

	/**
	 * 订单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "order_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Order order;

	/**
	 * 外发时间
	 */
	@Column(name = "out_going_time")
	private Date outGoingTime;

	/**
	 * 分类(1=成品，2=皮壳)
	 */
	@Column(name = "product_type")
	private Integer productType;

	/**
	 * 是否作废
	 */
	@Column(name = "flag")
	private Integer flag;

	/**
	 * 是否审核
	 */
	@Column(name = "audit")
	private Integer audit;

	/**
	 * 外发指定 预计入库仓库种类id
	 */
	@Column(name = "warehouse_type_id")
	private Long warehouseTypeId;

	/**
	 * 仓库种类
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "warehouse_type_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseData warehouseType;

	/**
	 * 仓管指定 入库仓库种类id
	 */
	@Column(name = "in_warehouse_type_id")
	private Long inWarehouseTypeId;

	/**
	 * 入库仓库种类
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "in_warehouse_type_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseData inWarehouseType;

	/**
	 * 仓管指定 入库库存id
	 */
	@Column(name = "inventory_id")
	private Long inventoryId;

	/**
	 * 入库库存
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "inventory_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Inventory inventory;

	/**
	 * 库存 是否到货
	 */
	@Column(name = "arrival")
	private Integer arrival;

	/**
	 * 到货时间
	 */
	@Column(name = "arrival_time")
	private Date arrivalTime;

	/**
	 * 到货数量
	 */
	@Column(name = "arrival_number")
	private Integer arrivalNumber;

	/**
	 * 库位
	 */
	@Column(name = "location")
	private String location;

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
	 * 查询字段
	 */
	@Transient
	private Date orderTimeBegin;
	/**
	 * 查询字段
	 */
	@Transient
	private Date orderTimeEnd;
	
	

	public Integer getOutsource() {
		return outsource;
	}

	public void setOutsource(Integer outsource) {
		this.outsource = outsource;
	}

	public String getProcessIds() {
		return processIds;
	}

	public void setProcessIds(String processIds) {
		this.processIds = processIds;
	}

	public Long getInventoryId() {
		return inventoryId;
	}

	public void setInventoryId(Long inventoryId) {
		this.inventoryId = inventoryId;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
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

	public Long getInWarehouseTypeId() {
		return inWarehouseTypeId;
	}

	public void setInWarehouseTypeId(Long inWarehouseTypeId) {
		this.inWarehouseTypeId = inWarehouseTypeId;
	}

	public BaseData getInWarehouseType() {
		return inWarehouseType;
	}

	public void setInWarehouseType(BaseData inWarehouseType) {
		this.inWarehouseType = inWarehouseType;
	}

	public Integer getArrival() {
		return arrival;
	}

	public void setArrival(Integer arrival) {
		this.arrival = arrival;
	}

	public Date getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(Date arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public Integer getArrivalNumber() {
		return arrivalNumber;
	}

	public void setArrivalNumber(Integer arrivalNumber) {
		this.arrivalNumber = arrivalNumber;
	}

	public String getOutSourceNumber() {
		return outSourceNumber;
	}

	public void setOutSourceNumber(String outSourceNumber) {
		this.outSourceNumber = outSourceNumber;
	}

	public Long getWarehouseTypeId() {
		return warehouseTypeId;
	}

	public void setWarehouseTypeId(Long warehouseTypeId) {
		this.warehouseTypeId = warehouseTypeId;
	}

	public BaseData getWarehouseType() {
		return warehouseType;
	}

	public void setWarehouseType(BaseData warehouseType) {
		this.warehouseType = warehouseType;
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

	public Date getOutGoingTime() {
		return outGoingTime;
	}

	public void setOutGoingTime(Date outGoingTime) {
		this.outGoingTime = outGoingTime;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
		this.productType = productType;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
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

	public String getProcess() {
		return process;
	}

	public void setProcess(String process) {
		this.process = process;
	}

}
