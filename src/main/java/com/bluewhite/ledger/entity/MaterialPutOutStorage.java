package com.bluewhite.ledger.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;

/**
 * 
 * 物料 入库单和出库单关系中间表
 * @author zhangliang
 *
 */
@Entity
@Table(name = "ledger_material_put_out_storage")
public class MaterialPutOutStorage extends BaseEntity<Long> {
	
	/**
	 * 出库单id
	 */
	@Column(name = "material_out_storage_id")
	private Long materialOutStorageId;

	/**
	 * 出库单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "material_out_storage_id", referencedColumnName = "id", insertable = false, updatable = false)
	private MaterialOutStorage materialOutStorage;
	
	/**
	 * 入库单id
	 */
	@Column(name = "material_put_storage_id")
	private Long materialPutStorageId;

	/**
	 * 入库单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "material_put_storage_id", referencedColumnName = "id", insertable = false, updatable = false)
	private MaterialPutStorage materialPutStorage;
	
	/**
	 * 数量（出库单占用入库单的实际数量）
	 */
	@Column(name = "number")
	private Double number;

	public Long getMaterialOutStorageId() {
		return materialOutStorageId;
	}

	public void setMaterialOutStorageId(Long materialOutStorageId) {
		this.materialOutStorageId = materialOutStorageId;
	}

	public MaterialOutStorage getMaterialOutStorage() {
		return materialOutStorage;
	}

	public void setMaterialOutStorage(MaterialOutStorage materialOutStorage) {
		this.materialOutStorage = materialOutStorage;
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

	public Double getNumber() {
		return number;
	}

	public void setNumber(Double number) {
		this.number = number;
	}


}
