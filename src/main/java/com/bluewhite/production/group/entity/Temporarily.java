package com.bluewhite.production.group.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;

/**
 * 借调人员
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_temporarily")
public class Temporarily extends BaseEntity<Long>{
	
	/**
	 *借调人员id
	 */
	@Column(name = "user_id")
	private Long userId;
	
	/**
	 * 姓名
	 */
	@Column(name = "user_name")
	private String userName;
	
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
	 * 分组所属部门类型 (1=一楼质检，2=一楼包装)
	 */
	@Column(name = "type")
	private Integer type;
	
	/**
	 * 分组id
	 */
	@Column(name = "group_id")
	private Long groupId;
	

	/**
	 * 是否是外来人员（0=否，1=是）
	 */
	@Column(name = "foreigns")
	private Integer foreigns ;
	
	
	/**
	 * 分组id
	 */
	@Transient
	private String groupName;

	
	

	public String getGroupName() {
		return groupName;
	}


	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}


	public Integer getForeigns() {
		return foreigns;
	}


	public void setForeigns(Integer foreigns) {
		this.foreigns = foreigns;
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


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}
	
	
	
}
