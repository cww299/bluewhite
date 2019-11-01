package com.bluewhite.personnel.roomboard.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.basedata.entity.BaseData;

/**
 * 招聘记录
 * 
 * @author qiyong
 *
 */
@Entity
@Table(name = "person_coefficient" )
public class coefficient extends BaseEntity<Long> {
	
	/**
	 * 职位id
	 */
	@Column(name = "position_id")
	private Long positionId;
	
	/**
	 * 职位
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "position_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseData position;
	
	/**
	 * 部门id
	 */
	@Column(name = "orgName_id")
	private Long orgNameId;
	
	/**
	 * 部门
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orgName_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseData orgName;
	
	
	/**
	 * 系数
	 */
	@Column(name = "basics")
	private Double basics;
	
	/**
	 * 文凭 专业系数
	 */
	@Column(name = "basics1")
	private Double basics1;
	
	/**
	 * 技术系数系数
	 */
	@Column(name = "basics2")
	private Double basics2;
	
	/**
	 * 加班系数
	 */
	@Column(name = "basics3")
	private Double basics3;

	public Long getPositionId() {
		return positionId;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}

	public BaseData getPosition() {
		return position;
	}

	public void setPosition(BaseData position) {
		this.position = position;
	}

	public Long getOrgNameId() {
		return orgNameId;
	}

	public void setOrgNameId(Long orgNameId) {
		this.orgNameId = orgNameId;
	}

	public BaseData getOrgName() {
		return orgName;
	}

	public void setOrgName(BaseData orgName) {
		this.orgName = orgName;
	}

	public Double getBasics() {
		return basics;
	}

	public void setBasics(Double basics) {
		this.basics = basics;
	}

	public Double getBasics1() {
		return basics1;
	}

	public void setBasics1(Double basics1) {
		this.basics1 = basics1;
	}

	public Double getBasics2() {
		return basics2;
	}

	public void setBasics2(Double basics2) {
		this.basics2 = basics2;
	}

	public Double getBasics3() {
		return basics3;
	}

	public void setBasics3(Double basics3) {
		this.basics3 = basics3;
	}
	
}
