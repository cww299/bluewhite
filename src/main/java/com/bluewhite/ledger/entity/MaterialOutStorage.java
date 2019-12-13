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
import com.bluewhite.product.primecostbasedata.entity.Materiel;
import com.bluewhite.system.user.entity.User;

/**
 * 物料出库单
 * 
 * @author zhangliang
 *
 */
@Entity
@Table(name = "ledger_material_out_storage")
public class MaterialOutStorage extends BaseEntity<Long>{
	/**
	 * 编号
	 */
	@Column(name = "serial_number")
	private String serialNumber;
	
	/**
	 * 物料id
	 */
	@Column(name = "materiel_id")
	private Long materielId;

	/**
	 * 物料
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "materiel_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Materiel materiel;
	
	/**
	 * 领料单id(生产出库)
	 * 
	 */
	@Column(name = "material_requisition_id")
	private Long materialRequisitionId;

	/**
	 * 领料单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "material_requisition_id", referencedColumnName = "id", insertable = false, updatable = false)
	private MaterialRequisition materialRequisition;

	/**
	 * 入库单多对多
	 */
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "ledger_put_out_material_storage", joinColumns = @JoinColumn(name = "material_out_storage_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "material_put_storage_id", referencedColumnName = "id"))
	private Set<MaterialPutStorage> materialPutOutStorage = new HashSet<MaterialPutStorage>();
	
	/**
	 * 出库类型（1=生产出库） （2=调拨出库） （3=换货出库 ） （4=退货出库 ） （5=盘盈出库 ）
	 * 生产出库：根据领料申请单出库
	 * 调拨出库：根据调拨申请单出库
	 * 换货出库：根据换货申请单出库
	 * 退货出库：根据退货申请单出库
	 * 盘盈出库：根据盘盈申请单出库
	 * 
	 * 
	 */
	@Column(name = "out_status")
	private Integer outStatus;

	/**
	 * 出库时间
	 */
	@Column(name = "arrival_time")
	private Date arrivalTime;

	/**
	 * 出库数量
	 */
	@Column(name = "arrival_number")
	private Integer arrivalNumber;
	
	/**
	 * 退货原因
	 */
	@Column(name = "remark")
	private String remark;
	
	/**
	 * 出库操作人id
	 * 
	 */
	@Column(name = "user_storage_id")
	private Long userStorageId;

	/**
	 * 出库操作人
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_storage_id", referencedColumnName = "id", insertable = false, updatable = false)
	private User userStorage;
	
	/**
	 * 物料名称
	 */
	@Transient
	private String materielName;
	
	/**
	 * 物料编号
	 */
	@Transient
	private String materielNumber;
	
	/**
	 * 入库单ids
	 * @return
	 */
	@Transient
	private String materialPutOutStorageIds;
	
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
	
	
	
	public String getMaterialPutOutStorageIds() {
		return materialPutOutStorageIds;
	}

	public void setMaterialPutOutStorageIds(String materialPutOutStorageIds) {
		this.materialPutOutStorageIds = materialPutOutStorageIds;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public Long getMaterielId() {
		return materielId;
	}

	public void setMaterielId(Long materielId) {
		this.materielId = materielId;
	}

	public Materiel getMateriel() {
		return materiel;
	}

	public void setMateriel(Materiel materiel) {
		this.materiel = materiel;
	}

	public Long getUserStorageId() {
		return userStorageId;
	}

	public void setUserStorageId(Long userStorageId) {
		this.userStorageId = userStorageId;
	}

	public User getUserStorage() {
		return userStorage;
	}

	public void setUserStorage(User userStorage) {
		this.userStorage = userStorage;
	}

	public String getMaterielName() {
		return materielName;
	}

	public void setMaterielName(String materielName) {
		this.materielName = materielName;
	}

	public String getMaterielNumber() {
		return materielNumber;
	}

	public void setMaterielNumber(String materielNumber) {
		this.materielNumber = materielNumber;
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

	public Long getMaterialRequisitionId() {
		return materialRequisitionId;
	}

	public void setMaterialRequisitionId(Long materialRequisitionId) {
		this.materialRequisitionId = materialRequisitionId;
	}

	public MaterialRequisition getMaterialRequisition() {
		return materialRequisition;
	}

	public void setMaterialRequisition(MaterialRequisition materialRequisition) {
		this.materialRequisition = materialRequisition;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Set<MaterialPutStorage> getMaterialPutOutStorage() {
		return materialPutOutStorage;
	}

	public void setMaterialPutOutStorage(Set<MaterialPutStorage> materialPutOutStorage) {
		this.materialPutOutStorage = materialPutOutStorage;
	}

	public Integer getOutStatus() {
		return outStatus;
	}

	public void setOutStatus(Integer outStatus) {
		this.outStatus = outStatus;
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
	

}
