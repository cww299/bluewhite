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
	 * 	申请时间
	 */
	@Column(name = "write_time")
	private Date writeTime;
	
	/**
	 * 请假原因
	 */
	@Column(name = "content")
	private String content;
	
	/**
	 * 请假类型(0=事假、1=病假、2=丧假、3=婚假、4=产假、5=护理假、6=抵消迟到）
	 */
	@Column(name = "holiday_type")
	private Integer holidayType;
	
	/**
	 * 是否请假(true=是)
	 */
	@Column(name = "holiday")
	private boolean holiday;
	
	/**
	 * 详情
	 */
	@Column(name = "holiday_detail")
	private String holidayDetail;
	
	/**
	 * 是否调休(true=是)
	 */
	@Column(name = "trade_days")
	private boolean tradeDays;
	
	/**
	 * 是否补签(true=是)
	 */
	@Column(name = "add_sign_in")
	private boolean addSignIn;
	
	/**
	 * 是否申请加班(true=是)
	 */
	@Column(name = "apply_overtime")
	private boolean applyOvertime;
	
	/**
	 * 加班类型(默认1=正常加班，2=撤销加班,3=生产加班)
	 */
	@Column(name = "overtime_type")
	private Integer overtimeType;
	
	/**
	 * （请假，调休，补签，加班）时间(json格式传递（日期+时长）)
	 */
	@Column(name = "time")
	private String time;
	
	/**
	 * 	调休到的日期时间
	 */
	@Column(name = "tradeDays_time")
	private Date tradeDaysTime;

	/**
	 * 员工姓名
	 */
	@Transient
	private String userName;
	
	/**
	 * 查询字段（部门）
	 */
	@Transient
	private Long orgNameId;
	
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
	 * 0=签入 1=签出
	 */
	@Transient
	private Integer sign;
	
	
	public Integer getSign() {
		return sign;
	}

	public void setSign(Integer sign) {
		this.sign = sign;
	}

	public Integer getOvertimeType() {
		return overtimeType;
	}

	public void setOvertimeType(Integer overtimeType) {
		this.overtimeType = overtimeType;
	}

	public String getHolidayDetail() {
		return holidayDetail;
	}

	public void setHolidayDetail(String holidayDetail) {
		this.holidayDetail = holidayDetail;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getOrgNameId() {
		return orgNameId;
	}

	public void setOrgNameId(Long orgNameId) {
		this.orgNameId = orgNameId;
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

	public boolean isHoliday() {
		return holiday;
	}

	public void setHoliday(boolean holiday) {
		this.holiday = holiday;
	}

	public Date getWriteTime() {
		return writeTime;
	}

	public void setWriteTime(Date writeTime) {
		this.writeTime = writeTime;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Date getTradeDaysTime() {
		return tradeDaysTime;
	}

	public void setTradeDaysTime(Date tradeDaysTime) {
		this.tradeDaysTime = tradeDaysTime;
	}

	public boolean isTradeDays() {
		return tradeDays;
	}

	public void setTradeDays(boolean tradeDays) {
		this.tradeDays = tradeDays;
	}

	public boolean isAddSignIn() {
		return addSignIn;
	}

	public void setAddSignIn(boolean addSignIn) {
		this.addSignIn = addSignIn;
	}

	public boolean isApplyOvertime() {
		return applyOvertime;
	}

	public void setApplyOvertime(boolean applyOvertime) {
		this.applyOvertime = applyOvertime;
	}

	public Integer getHolidayType() {
		return holidayType;
	}

	public void setHolidayType(Integer holidayType) {
		this.holidayType = holidayType;
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
