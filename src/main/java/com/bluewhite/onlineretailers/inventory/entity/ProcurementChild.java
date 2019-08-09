 package com.bluewhite.onlineretailers.inventory.entity;

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
/**
 * 电商子生产针工入库出库单实体
 * 
 * @author zhangliang
 *
 */
@Entity
@Table(name = "online_procurement_child")
public class ProcurementChild  extends BaseEntity<Long>{
	
	
	/**
	 * 批次号
	 */
	@Column(name = "batch_number")
	private String batchNumber;
	
	/**
	 * 商品id
	 */
	@Column(name = "commodity_id")
	private Long commodityId;
	
	/**
	 * 商品实体
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "commodity_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Commodity commodity;
	
	/**
	 * 上一阶段单据id（用于做反冲数据时确定上一阶段反冲数据）
	 */
	@Column(name = "parent_id")
	private Long parentId;
	
	/**
	 * 父id
	 * 
	 */
	@Column(name = "procurement_id")
	private Long procurementId;
	

	/**
	 * 父实体
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "procurement_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Procurement procurement;
	
	/**
	 * 子单原数量
	 */
	@Column(name = "number")
	private Integer number;
	
	/**
	 * 转换剩余数量(入库单数量无法转换，在出库单生成时，自动减去按时间排序的入库单剩余数量)
	 */
	@Column(name = "residue_number")
	private Integer residueNumber;
	
	
	/**
	 * 仓库类型id
	 */
	@Column(name = "warehouse_id")
	private Long warehouseId;
	
	/**
	 * 仓库类型（0=主仓库，1=客供仓库，2=次品）
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "warehouse_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseData warehouse;

	
	/**
	 * 第一种是入库单的订单状态，第二种是出库单的订单状态
	 * （0=生产入库）
	 * （1=调拨入库）
	 * （2=销售退货入库 ）
	 * （3=销售换货入库 ）
	 * （4=采购入库）
	 * （5=盘亏入库）
	 * 
	 * 
	 * （0=销售出库）
	 * （1=调拨出库）
	 * （2=销售换货出库 ）
	 * （3=采购退货出库 ）
	 * （4=盘盈出库 ）
	 */
	@Column(name = "status")
	private Integer status;
	
	/**
	 * 当入库单状态是销售出库时，存入销售子单的id
	 */
	@Column(name = "online_order_id")
	private Long  onlineOrderId;
	
	/**
	 * 当单据为库单时，同时记录一下是从那个生产单出库的，便于反冲数据直接获取
	 * 因为出库数据可能存在不够的情况，存入ids
	 */
	@Column(name = "put_warehouse_ids")
	private String putWarehouseIds;
	
	
	/**
	 * 备注
	 * 
	 */
	@Column(name = "child_remark")
	private String childRemark;

	/**
	 * 库存位置
	 * 
	 */
	@Column(name = "place")
	private String place;
	

	/**
	 * 单据编号
	 */
	@Transient
	private String documentNumber;
	
	
	/**
	 * 是否反冲（0=否，1=是）
	 */
	@Transient
	private Integer flag;
	
	/**
	 * 是否审核（审核成功后入库）
	 * 
	 */
	@Transient
	private Integer audit;
	
	/**
	 * 类型
	 * 
	 */
	@Transient
	private Integer type;
	
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
	
	
	
	
	public Long getParentId() {
		return parentId;
	}


	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}


	public String getDocumentNumber() {
		return documentNumber;
	}


	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}


	public Integer getFlag() {
		return flag;
	}


	public void setFlag(Integer flag) {
		this.flag = flag;
	}


	public Integer getAudit() {
		return audit;
	}


	public void setAudit(Integer audit) {
		this.audit = audit;
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


	public Long getOnlineOrderId() {
		return onlineOrderId;
	}


	public void setOnlineOrderId(Long onlineOrderId) {
		this.onlineOrderId = onlineOrderId;
	}


	public String getPutWarehouseIds() {
		return putWarehouseIds;
	}


	public void setPutWarehouseIds(String putWarehouseIds) {
		this.putWarehouseIds = putWarehouseIds;
	}


	public String getBatchNumber() {
		return batchNumber;
	}


	public void setBatchNumber(String batchNumber) {
		this.batchNumber = batchNumber;
	}


	public String getPlace() {
		return place;
	}


	public void setPlace(String place) {
		this.place = place;
	}


	public Integer getResidueNumber() {
		return residueNumber;
	}


	public void setResidueNumber(Integer residueNumber) {
		this.residueNumber = residueNumber;
	}

	public String getChildRemark() {
		return childRemark;
	}


	public void setChildRemark(String childRemark) {
		this.childRemark = childRemark;
	}

	public Long getCommodityId() {
		return commodityId;
	}


	public void setCommodityId(Long commodityId) {
		this.commodityId = commodityId;
	}


	public Commodity getCommodity() {
		return commodity;
	}


	public void setCommodity(Commodity commodity) {
		this.commodity = commodity;
	}


	public Long getProcurementId() {
		return procurementId;
	}


	public void setProcurementId(Long procurementId) {
		this.procurementId = procurementId;
	}


	public Procurement getProcurement() {
		return procurement;
	}


	public void setProcurement(Procurement procurement) {
		this.procurement = procurement;
	}


	public Integer getNumber() {
		return number;
	}


	public void setNumber(Integer number) {
		this.number = number;
	}


	public Long getWarehouseId() {
		return warehouseId;
	}


	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}


	public BaseData getWarehouse() {
		return warehouse;
	}


	public void setWarehouse(BaseData warehouse) {
		this.warehouse = warehouse;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}
	
	
	
	
	
	
}
