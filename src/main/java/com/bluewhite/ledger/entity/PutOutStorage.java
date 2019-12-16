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
 * 成品 入库单和出库单关系中间表
 * @author zhangliang
 *
 */
@Entity
@Table(name = "ledger_put_out_storage")
public class PutOutStorage extends BaseEntity<Long> {
	
	/**
	 * 出库单id
	 */
	@Column(name = "out_storage_id")
	private Long outStorageId;

	/**
	 * 出库单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "out_storage_id", referencedColumnName = "id", insertable = false, updatable = false)
	private OutStorage outStorage;
	
	/**
	 * 入库单id
	 */
	@Column(name = "put_storage_id")
	private Long putStorageId;

	/**
	 * 入库单
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "put_storage_id", referencedColumnName = "id", insertable = false, updatable = false)
	private PutStorage putStorage;
	
	/**
	 * 数量（出库单占用入库单的实际数量）
	 */
	@Column(name = "number")
	private Integer number;

	

	public Long getOutStorageId() {
		return outStorageId;
	}

	public void setOutStorageId(Long outStorageId) {
		this.outStorageId = outStorageId;
	}

	public OutStorage getOutStorage() {
		return outStorage;
	}

	public void setOutStorage(OutStorage outStorage) {
		this.outStorage = outStorage;
	}

	public Long getPutStorageId() {
		return putStorageId;
	}

	public void setPutStorageId(Long putStorageId) {
		this.putStorageId = putStorageId;
	}

	public PutStorage getPutStorage() {
		return putStorage;
	}

	public void setPutStorage(PutStorage putStorage) {
		this.putStorage = putStorage;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
	
	

}
