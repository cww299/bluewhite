package com.bluewhite.personnel.attendance.entity;

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

/**
 * 招聘记录
 * 
 * @author qiyong
 *
 */
@Entity
@Table(name = "person_recruit" )
public class Recruit extends BaseEntity<Long> {
	
	/**
	 * 时间
	 */
	@Column(name = "time")
	private Date time;
	
	/**
	 * 平台Id
	 */
	@Column(name = "platform_id")
	private Long platformId;
	
	/**
	 * 职位
	 */
	@Column(name = "position")
	private String position;
	
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
	 *人员姓名
	 */
	@Column(name = "name")
	private String name;
	
	/**
	 * 性别(0=男，1=女)
	 */
	@Column(name = "gender")
	private Integer gender;
	
	/**
	 * 手机
	 */
	@Column(name = "phone")
	private String phone;
	
	/**
	 * 现居住地址
	 */
	@Column(name = "living_address")
    private String livingAddress;
	
	/**
     * 面试时间
     */
	@Column(name = "entry")
    private String entry;
	
	
	/**
	 * 是否应面(0=否，1=是)
	 */
	@Column(name = "type")
	private Integer type;
	
	/**
	 * 备注
	 */
	@Column(name = "remarks")
    private String remarks;
	
	/**
	 * 一面(0=否，1=是)
	 */
	@Column(name = "type_one")
	private Integer typeOne;
	
	/**
	 * 一面备注
	 */
	@Column(name = "remarks_one")
    private String remarksOne;

	/**
	 * 二面(0=否，1=是)
	 */
	@Column(name = "type_two")
	private Integer typeTwo;
	
	/**
	 * 二面备注
	 */
	@Column(name = "remarks_two")
    private String remarksTwo;
	
	
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
	public Long getPlatformId() {
		return platformId;
	}
	public void setPlatformId(Long platformId) {
		this.platformId = platformId;
	}
	
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getGender() {
		return gender;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getLivingAddress() {
		return livingAddress;
	}
	public void setLivingAddress(String livingAddress) {
		this.livingAddress = livingAddress;
	}
	
	public String getEntry() {
		return entry;
	}
	public void setEntry(String entry) {
		this.entry = entry;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Integer getTypeOne() {
		return typeOne;
	}
	public void setTypeOne(Integer typeOne) {
		this.typeOne = typeOne;
	}
	public String getRemarksOne() {
		return remarksOne;
	}
	public void setRemarksOne(String remarksOne) {
		this.remarksOne = remarksOne;
	}
	public Integer getTypeTwo() {
		return typeTwo;
	}
	public void setTypeTwo(Integer typeTwo) {
		this.typeTwo = typeTwo;
	}
	public String getRemarksTwo() {
		return remarksTwo;
	}
	public void setRemarksTwo(String remarksTwo) {
		this.remarksTwo = remarksTwo;
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
	
	

	

	
	

}
