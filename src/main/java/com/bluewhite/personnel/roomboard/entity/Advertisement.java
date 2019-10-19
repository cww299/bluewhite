package com.bluewhite.personnel.roomboard.entity;

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
 * 广告费用
 * 
 * @author qiyong
 *
 */
@Entity
@Table(name = "person_advertisement")
public class Advertisement extends BaseEntity<Long> {

	/**
	 * 平台Id
	 */
	@Column(name = "platform_id")
	private Long platformId;

	/**
	 * 广告平台
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "platform_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseData platform;

	/**
	 * 投放费用
	 */
	@Column(name = "price")
	private Double price;

	/**
	 * 月份
	 */
	@Column(name = "time")
	private Date time;

	/**
	 * 开始时间
	 */
	@Column(name = "start_time")
	private Date startTime;

	/**
	 * 结束时间
	 */
	@Column(name = "end_time")
	private Date endTime;

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
	 * 招聘
	 */
	@Column(name = "recruit_id")
	private Long recruitId;

	/**
	 * 招聘
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recruit_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Recruit recruitName;

	/**
	 * 培训内容
	 */
	@Column(name = "train")
	private String train;

	/**
	 * 培训老师id
	 */
	@Column(name = "user_id")
	private Long userId;

	/**
	 * 老师
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
	private User user;

	/**
	 * 是否培训合格(0=否，1=是)
	 */
	@Column(name = "qualified")
	private Integer qualified;

	/**
	 * 类型(0=广告，1=培训)
	 */
	@Column(name = "type")
	private Integer type;

	/**
	 * 合格简历数
	 */
	@Column(name = "number")
	private Integer number;

	/**
	 * 待定简历数
	 */
	@Column(name = "number2")
	private Integer number2;

	/**
	 * 不合格简历数
	 */
	@Column(name = "number3")
	private Integer number3;

	/**
	 * 收取简历数
	 */
	@Column(name = "number4")
	private Integer number4;
	
	/**
	 * 备注
	 */
	@Column(name = "remarks")
	private String remarks;
	
	/**
	 * 查询字段应聘人名字
	 */
	@Transient
	private Date applyName;
	
	/**
	 * 查询字段招聘人名字
	 */
	@Transient
	private Date recruitmentName;

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
	

	

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

	public Date getApplyName() {
		return applyName;
	}

	public void setApplyName(Date applyName) {
		this.applyName = applyName;
	}

	public Date getRecruitmentName() {
		return recruitmentName;
	}

	public void setRecruitmentName(Date recruitmentName) {
		this.recruitmentName = recruitmentName;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Integer getNumber2() {
		return number2;
	}

	public void setNumber2(Integer number2) {
		this.number2 = number2;
	}

	public Integer getNumber3() {
		return number3;
	}

	public void setNumber3(Integer number3) {
		this.number3 = number3;
	}

	public Integer getNumber4() {
		return number4;
	}

	public void setNumber4(Integer number4) {
		this.number4 = number4;
	}

	public Long getPlatformId() {
		return platformId;
	}

	public void setPlatformId(Long platformId) {
		this.platformId = platformId;
	}

	public BaseData getPlatform() {
		return platform;
	}

	public void setPlatform(BaseData platform) {
		this.platform = platform;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Long getRecruitId() {
		return recruitId;
	}

	public void setRecruitId(Long recruitId) {
		this.recruitId = recruitId;
	}

	public Recruit getRecruitName() {
		return recruitName;
	}

	public void setRecruitName(Recruit recruitName) {
		this.recruitName = recruitName;
	}

	public String getTrain() {
		return train;
	}

	public void setTrain(String train) {
		this.train = train;
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

	public Integer getQualified() {
		return qualified;
	}

	public void setQualified(Integer qualified) {
		this.qualified = qualified;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

}
