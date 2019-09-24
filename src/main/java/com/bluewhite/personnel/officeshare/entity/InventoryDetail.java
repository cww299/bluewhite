package com.bluewhite.personnel.officeshare.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.basedata.entity.BaseData;
import com.bluewhite.system.user.entity.User;

/**
 * 办公用品出入库明细
 * @author zhangliang
 *
 */
@Entity
@Table(name = "person_ inventory_detail")
public class InventoryDetail extends BaseEntity<Long>{
	
	/**
	 * 办公用品id
	 */
	@Column(name = "office_supplies_id")
	private Long officeSuppliesId;
	
	/**
	 * 办公用品
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "office_supplies_id", referencedColumnName = "id", insertable = false, updatable = false)
	private OfficeSupplies OfficeSupplies;
	

	/**
	 * 出库入库（0,1）
	 */
	@Column(name = "flag")
	private Integer flag;
	
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
	 * 领取人id
	 */
	@Column(name = "user_id")
	private Long userId;
	
	/**
	 * 领取人
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
	private User user;
	
	/**
	 * 领取时间
	 */
	@Column(name = "time")
	private Date time;
	
	
	

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Long getOfficeSuppliesId() {
		return officeSuppliesId;
	}

	public void setOfficeSuppliesId(Long officeSuppliesId) {
		this.officeSuppliesId = officeSuppliesId;
	}

	public OfficeSupplies getOfficeSupplies() {
		return OfficeSupplies;
	}

	public void setOfficeSupplies(OfficeSupplies officeSupplies) {
		OfficeSupplies = officeSupplies;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	

}
