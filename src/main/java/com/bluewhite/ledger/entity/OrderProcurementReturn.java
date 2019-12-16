package com.bluewhite.ledger.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.product.primecostbasedata.entity.Materiel;
/**
 * 采购物料退货单
 * @author zhangliang
 *
 */
@Entity
@Table(name = "ledger_order_procurement")
public class OrderProcurementReturn extends BaseEntity<Long> {
	
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
	 * 时间
	 */
	@Column(name = "time")
	private Date time;

	/**
	 * 数量
	 */
	@Column(name = "number")
	private Double number;
	
	/**
	 * 原因
	 */
	@Column(name = "remark")
	private String remark;

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

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Double getNumber() {
		return number;
	}

	public void setNumber(Double number) {
		this.number = number;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}



}
