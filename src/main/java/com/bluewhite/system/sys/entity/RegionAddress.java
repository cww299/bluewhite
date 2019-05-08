package com.bluewhite.system.sys.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;

/**
 * 省市县
 * @author zhangliang
 *
 */
@Entity
@Table(name = "sys_region_addr")
public class RegionAddress  extends BaseEntity<Long>{
	
	
	/**
	 * 地区编码
	 * 
	 */
	@Column(name = "region_code")
	private String regionCode;
	
	
	/**
	 * 地区名称
	 * 
	 */
	@Column(name = "region_name")
	private String regionName;
	
	/**
	 * 父id
	 * 
	 */
	@Column(name = "parent_id")
	private Long parentId;
	
	/**
	 * 地区名称英文
	 * 
	 */
	@Column(name = "region_name_en")
	private String regionNameEn;



	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getRegionNameEn() {
		return regionNameEn;
	}

	public void setRegionNameEn(String regionNameEn) {
		this.regionNameEn = regionNameEn;
	}

	
	
	

}
