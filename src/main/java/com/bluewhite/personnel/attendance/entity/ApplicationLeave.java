package com.bluewhite.personnel.attendance.entity;

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
 * 出勤中需要核算的请假事项（请假条，调休，补签，加班）
 * @author zhangliang
 *
 */
@Entity
@Table(name = "person_application_leave" )
public class ApplicationLeave extends BaseEntity<Long>  {
	
	/**
	 * 	申请时间
	 */
	@Column(name = "write_time")
	private Date writetime;
	
	/**
	 * （请假条，调休，补签，加班）时间
	 */
	@Column(name = "time")
	private Date time;
	
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
	 * （请假条，调休，补签，加班）时长
	 */
	@Column(name = "long_time")
	private Double longTime;
	
	
	/**
	 * （请假条，调休，补签，加班）类型
	 */
	@Column(name = "type")
	private Integer type;
	
	

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Date getWritetime() {
		return writetime;
	}

	public void setWritetime(Date writetime) {
		this.writetime = writetime;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
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

	public Double getLongTime() {
		return longTime;
	}

	public void setLongTime(Double longTime) {
		this.longTime = longTime;
	}
	
	
	
	
	
	

}
