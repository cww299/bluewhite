package com.bluewhite.finance.attendance.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;

/**
 * 生产控制部  考勤工资（A工资）实体
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_attendance_pay")
public class AttendancePay  extends BaseEntity<Long>{
	
	/**
	 * 员工姓名
	 */
	@Column(name = "user_name")
    private String userName;
	
	
	/**
	 * 员工id
	 */
	@Column(name = "user_id")
    private Long userId;
	
	
	/**
	 * 到岗小时预计收入
	 */
	@Column(name = "work_price")
	private Double workPrice;
	
	
	/**
	 * 工作时长
	 */
	@Column(name = "work_time")
	private Double workTime;
	
	/**
	 * 当天考勤工资（A工资）
	 */
	@Column(name = "pay_number")
	private Double payNumber;
	
	
	/**
	 * 工序所属部门类型 (1=一楼质检，2=一楼包装，3=二楼针工)
	 */
	@Column(name = "type")
	private Integer type;
	
	/**
	 * 考勤时间
	 */
	@Column(name = "allot_time")
	private Date allotTime;
	
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
	

	@Transient
    private String[] usersId;
	
	


	public String[] getUsersId() {
		return usersId;
	}


	public void setUsersId(String[] usersId) {
		this.usersId = usersId;
	}


	public Double getWorkPrice() {
		return workPrice;
	}


	public void setWorkPrice(Double workPrice) {
		this.workPrice = workPrice;
	}


	public Double getWorkTime() {
		return workTime;
	}


	public void setWorkTime(Double workTime) {
		this.workTime = workTime;
	}

	public Double getPayNumber() {
		return payNumber;
	}


	public void setPayNumber(Double payNumber) {
		this.payNumber = payNumber;
	}


	public Integer getType() {
		return type;
	}


	public void setType(Integer type) {
		this.type = type;
	}


	public Date getAllotTime() {
		return allotTime;
	}


	public void setAllotTime(Date allotTime) {
		this.allotTime = allotTime;
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


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
	
	
	

}
