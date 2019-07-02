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
import com.bluewhite.system.user.entity.User;

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
	 * userId
	 */
	@Column(name = "user_id")
	private Long userId;
	
	
	/**
	 * 招聘人
	 */
	@Column(name = "recruit_id")
	private Long recruitId;
	
	/**
	 * 招聘人姓名
	 */
	@Column(name = "recruit_name")
	private String recruitName;
	
	/**
	 * 员工
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
	private User user;
	/**
	 * 平台Id
	 */
	@Column(name = "platform_id")
	private Long platformId;
	
	/**
	 * 平台
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "platform_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseData platform;
	
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
	 * 状态备注
	 */
	@Column(name = "remarks_three")
    private String remarksThree;
	
	/**
	 * 面试结果(0=不通过，1=通过)
	 */
	@Column(name = "adopt")
	private Integer adopt;
	
	/**
	 * 入职状态(0=没入职，1=已入职,2=拒绝入职,3=即将入职)
	 */
	@Column(name = "state")
	private Integer state;
	
	/**
	 * 入职时间
	 */
	@Column(name = "test_time")
	private Date testTime;
	
	/**
	 * 查询字段(是否离职)
	 */
	@Transient
	private Integer quit;
	
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
	
	public Long getRecruitId() {
		return recruitId;
	}
	public void setRecruitId(Long recruitId) {
		this.recruitId = recruitId;
	}
	public String getRemarksThree() {
		return remarksThree;
	}
	public void setRemarksThree(String remarksThree) {
		this.remarksThree = remarksThree;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}

	
	public BaseData getPlatform() {
		return platform;
	}
	public void setPlatform(BaseData platform) {
		this.platform = platform;
	}
	public String getRecruitName() {
		return recruitName;
	}
	public void setRecruitName(String recruitName) {
		this.recruitName = recruitName;
	}
	public Integer getQuit() {
		return quit;
	}
	public void setQuit(Integer quit) {
		this.quit = quit;
	}
	public Date getTestTime() {
		return testTime;
	}
	public void setTestTime(Date testTime) {
		this.testTime = testTime;
	}
	public Integer getAdopt() {
		return adopt;
	}
	public void setAdopt(Integer adopt) {
		this.adopt = adopt;
	}
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
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
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
