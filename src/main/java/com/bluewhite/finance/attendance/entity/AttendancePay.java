package com.bluewhite.finance.attendance.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.system.user.entity.User;

/**
 * 生产控制部 考勤工资（A工资）实体
 * 
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_attendance_pay", indexes = { @Index(columnList = "type"), 
												@Index(columnList = "user_id"),
												@Index(columnList = "allot_time") })
public class AttendancePay extends BaseEntity<Long> {

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
	 * 员工
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
	private User user;

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
	 * 
	 * 出勤时长
	 */
	@Column(name = "turnWorkTime")
	private Double turnWorkTime;

	/**
	 * 加班时间
	 */
	@Column(name = "over_time")
	private Double overTime;

	/**
	 * 所在组工作时长
	 */
	@Column(name = "group_work_time")
	private Double groupWorkTime;

	/**
	 * 当天考勤工资（A工资）
	 */
	@Column(name = "pay_number")
	private Double payNumber;

	/**
	 * 工序所属部门类型 (1=一楼质检，2=一楼包装，3=二楼针工,4=二楼机工,5=8号裁剪)
	 */
	@Column(name = "type")
	private Integer type;

	/**
	 * 考勤日期
	 */
	@Column(name = "allot_time")
	private Date allotTime;

	/**
	 * 同种最高工资
	 */
	@Column(name = "max_pay")
	private Double maxPay;

	/**
	 * 未拿到差价
	 */
	@Column(name = "disparity")
	private Double disparity;

	/**
	 * 分组
	 */
	@Column(name = "group_id")
	private Long groupId;

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

	/**
	 * 工种id
	 */
	@Transient
	private Long kindWorkId;

	@Transient
	private String[] usersId;

	@Transient
	private Double[] workTimes;

	@Transient
	private Double[] turnWorkTimes;
	
	/**
	 * 加班时间
	 */
	@Transient
	private Double[] overtimes;

	/**
	 * 是否计算同种工资差
	 */
	@Transient
	private Integer sign;

	
	
	public Double[] getTurnWorkTimes() {
		return turnWorkTimes;
	}

	public void setTurnWorkTimes(Double[] turnWorkTimes) {
		this.turnWorkTimes = turnWorkTimes;
	}

	public Double getTurnWorkTime() {
		return turnWorkTime;
	}

	public void setTurnWorkTime(Double turnWorkTime) {
		this.turnWorkTime = turnWorkTime;
	}

	public Double getGroupWorkTime() {
		return groupWorkTime;
	}

	public void setGroupWorkTime(Double groupWorkTime) {
		this.groupWorkTime = groupWorkTime;
	}

	public Integer getSign() {
		return sign;
	}

	public void setSign(Integer sign) {
		this.sign = sign;
	}

	public Long getKindWorkId() {
		return kindWorkId;
	}

	public void setKindWorkId(Long kindWorkId) {
		this.kindWorkId = kindWorkId;
	}

	public Double[] getOvertimes() {
		return overtimes;
	}

	public void setOvertimes(Double[] overtimes) {
		this.overtimes = overtimes;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Double getOverTime() {
		return overTime;
	}

	public void setOverTime(Double overTime) {
		this.overTime = overTime;
	}

	public Double getMaxPay() {
		return maxPay;
	}

	public void setMaxPay(Double maxPay) {
		this.maxPay = maxPay;
	}

	public Double getDisparity() {
		return disparity;
	}

	public void setDisparity(Double disparity) {
		this.disparity = disparity;
	}

	public Double[] getWorkTimes() {
		return workTimes;
	}

	public void setWorkTimes(Double[] workTimes) {
		this.workTimes = workTimes;
	}

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
