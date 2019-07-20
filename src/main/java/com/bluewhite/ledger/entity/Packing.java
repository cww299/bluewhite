 package com.bluewhite.ledger.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.basedata.entity.BaseData;

/**
 * 贴包
 * @author zhangliang
 *
 */
@Entity
@Table(name = "ledger_packing")
public class Packing  extends BaseEntity<Long>{
	
	/**
	 * 编号 (19N7Y20R01)
	 */
	@Column(name = "number")
	private String number;
	
	
	/**
	 * 客户id
	 * 
	 */
	@Column(name = "customr_id")
	private Long customrId;

	/**
	 * 客户
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customr_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Customr customr;
	
	
	/**
	 * 当批外包编号
	 */
	@Column(name = "packaging_number")
	private Integer packagingNumber;
	
	
	/**
	 * 包装物名称id
	 */
	@Column(name = "packaging_id")
	private Long packagingId;
	
	/**
	 * 包装物名称
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "packaging_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseData packaging;

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Long getCustomrId() {
		return customrId;
	}

	public void setCustomrId(Long customrId) {
		this.customrId = customrId;
	}

	public Customr getCustomr() {
		return customr;
	}

	public void setCustomr(Customr customr) {
		this.customr = customr;
	}

	public Integer getPackagingNumber() {
		return packagingNumber;
	}

	public void setPackagingNumber(Integer packagingNumber) {
		this.packagingNumber = packagingNumber;
	}

	public Long getPackagingId() {
		return packagingId;
	}

	public void setPackagingId(Long packagingId) {
		this.packagingId = packagingId;
	}

	public BaseData getPackaging() {
		return packaging;
	}

	public void setPackaging(BaseData packaging) {
		this.packaging = packaging;
	}
	
	
	
	
	
}
