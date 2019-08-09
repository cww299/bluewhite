package com.bluewhite.onlineretailers.inventory.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.ledger.entity.Customer;
import com.bluewhite.system.user.entity.User;

/**
 * 电商采购单（对于商品的库存管理）
 * @author zhangliang
 *
 */
@Entity
@Table(name = "online_procurement"  ,indexes = {@Index(columnList = "created_at")})
public class Procurement extends BaseEntity<Long>{
	
	
	/**
	 * 订单id
	 * 
	 */
	@Column(name = "order_id")
	private Long orderId;
	
	/**
	 * 单据编号
	 */
	@Column(name = "document_number")
	private String documentNumber;
	

	/**
	 * 经手人id
	 * 
	 */
	@Column(name = "user_id")
	private Long userId;

	/**
	 * 经手人
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
	private User user;
	
	/**
	 * 调拨人id
	 * 
	 */
	@Column(name = "transfers_user_id")
	private Long transfersUserId;

	/**
	 * 调拨人
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "transfers_user_id", referencedColumnName = "id", insertable = false, updatable = false)
	private User transfersUser;
	
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
	
	
	/**
	 * 子订单list
	 */
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
	@JoinColumn(name = "procurement_id")
	private List<ProcurementChild> procurementChilds = new ArrayList<>();
	
	
	/**
	 * 总数量
	 * 
	 */
	@Column(name = "number")
	private Integer number;
	
	/**
	 * 转换剩余总数量
	 */
	@Column(name = "residue_number")
	private Integer residueNumber;
	
	/**
	 *  单据类型(0=生产单，1=针工单,2=入库单，3=出库单)
	 */
	@Column(name = "type")
	private Integer type;
	
	/**
	 * 第一种是入库单的订单状态，第二种是出库单的订单状态
	 * （0=生产入库）
	 * （1=调拨入库）
	 * （2=销售退货入库 ）
	 * （3=销售换货入库 ）
	 * （4=采购入库）
	 * 
	 * 
	 * （0=销售出库）
	 * （1=调拨出库）
	 * （2=销售换货出库 ）
	 * （3=采购退货出库 ）
	 */
	@Column(name = "status")
	private Integer status;
	
	
	/**
	 * 是否反冲（0=否，1=是）
	 */
	@Column(name = "flag")
	private Integer flag;
	
	/**
	 * 备注
	 * 
	 */
	@Column(name = "remark")
	private String remark;
	
	/**
	 * 是否审核（审核成功后入库）
	 * 
	 */
	@Column(name = "audit")
	private Integer audit;
	
	/**
	 * json 储存商品id和数量
	 * 
	 */
	@Transient
	private String commodityNumber;

	/**
	 * 时间查询字段
	 */
	@Transient
	private Date orderTimeBegin;
	/**
	 * 时间查询字段
	 */
	@Transient
	private Date orderTimeEnd;
	
	/**
	 * 报表report
	 */
	@Transient
	private Integer report;
	
	/**
	 * 仓库类型id
	 */
	@Transient
	private Long warehouseId;
	
	
	/**
	 * 批次号
	 */
	@Transient
	private String batchNumber;
	
	/**
	 * 商品名称
	 */
	@Transient
	private String commodityName;
	
	
	
	
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Integer getAudit() {
		return audit;
	}

	public void setAudit(Integer audit) {
		this.audit = audit;
	}

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public String getBatchNumber() {
		return batchNumber;
	}

	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	public Integer getReport() {
		return report;
	}

	public void setReport(Integer report) {
		this.report = report;
	}

	public Long getTransfersUserId() {
		return transfersUserId;
	}

	public void setTransfersUserId(Long transfersUserId) {
		this.transfersUserId = transfersUserId;
	}

	public User getTransfersUser() {
		return transfersUser;
	}

	public void setTransfersUser(User transfersUser) {
		this.transfersUser = transfersUser;
	}

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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public Integer getResidueNumber() {
		return residueNumber;
	}

	public void setResidueNumber(Integer residueNumber) {
		this.residueNumber = residueNumber;
	}

	public List<ProcurementChild> getProcurementChilds() {
		return procurementChilds;
	}

	public void setProcurementChilds(List<ProcurementChild> procurementChilds) {
		this.procurementChilds = procurementChilds;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}


	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getCommodityNumber() {
		return commodityNumber;
	}

	public void setCommodityNumber(String commodityNumber) {
		this.commodityNumber = commodityNumber;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
	

}
