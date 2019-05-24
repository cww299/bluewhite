package com.bluewhite.onlineretailers.inventory.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
	 * 转换剩余数量
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
	 * （0=采购入库）
	 * （1=销售退货入库 ）
	 * （2=销售换货入库 ）
	 * （4=生产入库）
	 * （5=调拨入库）
	 * 
	 * 
	 * （0=销售入库）
	 * （1=采购退货入库 ）
	 * （2=销售换货入库 ）
	 * （4=调拨出库）
	 */
	@Column(name = "status")
	private Integer status;
	
	/**
	 * 备注
	 * 
	 */
	@Column(name = "child_remark")
	private String childRemark;

	
	



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
	
	
	
	
	
	
}
