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
 * 贴包 包装物
 * 
 * @author zhangliang
 *
 */
@Entity
@Table(name = "ledger_packing_materials")
public class PackingMaterials extends BaseEntity<Long>{
	
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
	private BaseData packagingMaterials;
	
	/**
	 * 包装物数量
	 */
	@Column(name = "packaging_count")
	private Integer packagingCount;

	public Long getPackagingId() {
		return packagingId;
	}

	public void setPackagingId(Long packagingId) {
		this.packagingId = packagingId;
	}

	public BaseData getPackagingMaterials() {
		return packagingMaterials;
	}

	public void setPackagingMaterials(BaseData packagingMaterials) {
		this.packagingMaterials = packagingMaterials;
	}

	public Integer getPackagingCount() {
		return packagingCount;
	}

	public void setPackagingCount(Integer packagingCount) {
		this.packagingCount = packagingCount;
	}
	
	
	

}
