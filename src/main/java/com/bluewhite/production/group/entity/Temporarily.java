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
import com.bluewhite.system.user.entity.TemporaryUser;
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
	 * 借调人员id(临时工id)
	 */
	@Column(name = "temporary_user_id")
	private Long temporaryUserId;
	
	/**
	 * 借调人员
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "temporary_user_id", referencedColumnName = "id", insertable = false, updatable = false)
	private TemporaryUser temporaryUser;
	
	/**
	 * 工作时长
	 */
	@Column(name = "work_time")
	private Double workTime;
	
	/**
	 * 借调时间
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
	 * 分组
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "group_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Group group;
	
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
	
	
	

	public Long getTemporaryUserId() {
		return temporaryUserId;
	}


	public void setTemporaryUserId(Long temporaryUserId) {
		this.temporaryUserId = temporaryUserId;
	}


	public TemporaryUser getTemporaryUser() {
		return temporaryUser;
	}


	public void setTemporaryUser(TemporaryUser temporaryUser) {
		this.temporaryUser = temporaryUser;
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
