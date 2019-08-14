package com.bluewhite.production.group.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.system.user.entity.User;

/**
 * 借调人员
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_temporarily")
public class Temporarily extends BaseEntity<Long>{
	
	/**
	 * 借调人员id
	 */
	@Column(name = "user_id")
	private Long userId;
	
	/**
	 * 借调人员
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
	private User user;
	
	/**
	 * 工作时长
	 */
	@Column(name = "work_time")
	private Double workTime;
	
	/**
	 * 外调时间
	 */
	@Column(name = "temporarily_date")
	private Date temporarilyDate;	
	
	
	/**
	 * 分组所属部门类型 (1=一楼质检，2=一楼包装3.二楼针工)
	 */
	@Column(name = "type")
	private Integer type;
	
	/**
	 * 分组id
	 */
	@Column(name = "group_id")
	private Long groupId;
	
	
	/**
	 * 借调人员
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Group group;
	
	/**
	 * 工作时间段(可多选)
	 */
	@Column(name = "work_time_slice")
	private String workTimeSlice;
	
	/**
	 * 分组
	 */
	@Transient
	private String groupName;

	
	/**
	 * 外调时间批量
	 */
	@Transient
	private String temporarilyDates;
	
	/**
	 * 员工姓名
	 */
	@Transient
	private String userName;
	
	/**
	 * 是否是转正人员(在生产线上直接添加的外来人员，可成为正式员工)
	 */
	@Transient
	private Boolean positive = false;
	
	
	/**
	 * 查看方式（日期）（1=按天，2=按月）
	 */
	@Transient
	private Integer viewTypeDate;
	
	/**
	 * 查看方式（员工）（1=按个人，2=按分组）
	 */
	@Transient
	private Integer viewTypeUser;
	
	/**
	 * 时间查询字段
	 */
	@Transient
	private Date orderTimeBegin;
	/**
	 * 时间查询字段
	 */
	@Transient
	private Date orderTimeEnd;
	
	
	

	public String getWorkTimeSlice() {
		return workTimeSlice;
	}


	public void setWorkTimeSlice(String workTimeSlice) {
		this.workTimeSlice = workTimeSlice;
	}


	public Group getGroup() {
		return group;
	}


	public void setGroup(Group group) {
		this.group = group;
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


	public Integer getViewTypeDate() {
		return viewTypeDate;
	}


	public void setViewTypeDate(Integer viewTypeDate) {
		this.viewTypeDate = viewTypeDate;
	}


	public Integer getViewTypeUser() {
		return viewTypeUser;
	}


	public void setViewTypeUser(Integer viewTypeUser) {
		this.viewTypeUser = viewTypeUser;
	}


	public Boolean getPositive() {
		return positive;
	}


	public void setPositive(Boolean positive) {
		this.positive = positive;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getTemporarilyDates() {
		return temporarilyDates;
	}


	public void setTemporarilyDates(String temporarilyDates) {
		this.temporarilyDates = temporarilyDates;
	}


	public String getGroupName() {
		return groupName;
	}


	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Long getGroupId() {
		return groupId;
	}


	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}


	public Double getWorkTime() {
		return workTime;
	}


	public void setWorkTime(Double workTime) {
		this.workTime = workTime;
	}


	public Date getTemporarilyDate() {
		return temporarilyDate;
	}


	public void setTemporarilyDate(Date temporarilyDate) {
		this.temporarilyDate = temporarilyDate;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}
	
	
	
}
