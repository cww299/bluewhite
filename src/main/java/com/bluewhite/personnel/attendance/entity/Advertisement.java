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
 * 广告费用
 * 
 * @author qiyong
 *
 */
@Entity
@Table(name = "person_advertisement" )
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
	 *投放费用
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
