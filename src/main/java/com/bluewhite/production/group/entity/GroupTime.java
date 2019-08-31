package com.bluewhite.production.group.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.system.user.entity.User;
/**
 * 用于记录员工所在组工作时长
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_group_time")
public class GroupTime extends BaseEntity<Long>{
	
	/**
	 * 员工id
	 */
	@Column(name = "user_id")
	private Long userId;

	/**
	 * 员工
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
	private User user;
	
	/**
	 * 完成任务的分组id 
	 */
	@Column(name = "group_id")
	private Long  groupId;

	/**
	 * 所在组工作时长(当员工不是全部在一个组工作，可以修改他在单个组上的工作时间)
	 */
	@Column(name = "group_work_time")
	private Double groupWorkTime;
	
	/**
	 * 考勤日期
	 */
	@Column(name = "allot_time")
	private Date allotTime;
	
	/**
	 * 工序所属部门类型 (1=一楼质检，2=一楼包装，3=二楼针工,4=二楼机工,5=8号裁剪)
	 */
	@Column(name = "type")
	private Integer type;
	
	/**
	 * 在当前组开始做任务时间
	 */
	@Column(name = "start_time")
	private Date startTime;
	
	/**
	 * 在当前组开结束做任务时间
	 */
	@Column(name = "end_time")
	private Date endTime;

	
	
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Double getGroupWorkTime() {
		return groupWorkTime;
	}

	public void setGroupWorkTime(Double groupWorkTime) {
		this.groupWorkTime = groupWorkTime;
	}

	public Date getAllotTime() {
		return allotTime;
	}

	public void setAllotTime(Date allotTime) {
		this.allotTime = allotTime;
	}

	
	
}
