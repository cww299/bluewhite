package com.bluewhite.ledger.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;

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
	 * 入库单id
	 */
	@Column(name = "material_put_storage_id")
	private Long materialPutStorageId;

	/**
	 * 入库单（选择入库单进行出库）
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "material_put_storage_id", referencedColumnName = "id", insertable = false, updatable = false)
	private MaterialPutStorage materialPutStorage;

	
	/**
	 * （1=生产出库） （2=调拨出库） （3=销售换货出库 ） （4=采购退货出库 ） （5=盘盈出库 ）
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

	
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getMaterialPutStorageId() {
		return materialPutStorageId;
	}

	public void setMaterialPutStorageId(Long materialPutStorageId) {
		this.materialPutStorageId = materialPutStorageId;
	}

	public MaterialPutStorage getMaterialPutStorage() {
		return materialPutStorage;
	}

	public void setMaterialPutStorage(MaterialPutStorage materialPutStorage) {
		this.materialPutStorage = materialPutStorage;
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
