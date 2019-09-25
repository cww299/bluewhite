package com.bluewhite.personnel.officeshare.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

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
	 * 出库（入库）时间
	 */
	@Column(name = "time")
	private Date time;
	
	/**
	 * 出库（入库）数量
	 */
	@Column(name = "number")
	private Integer number;
	
	/**
	 * 备注
	 */
	@Column(name = "remark")
	private String remark;
	
	/**
	 * 名称
	 */
	@Transient
	private String name;
	
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
	
	
	

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
