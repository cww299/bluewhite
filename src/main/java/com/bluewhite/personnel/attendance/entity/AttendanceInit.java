package com.bluewhite.personnel.attendance.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;
import com.bluewhite.system.user.entity.User;

/**
 * 关于设定人员考勤汇总时，所需要的初始数据
 * @author zhangliang
 *
 */
@Entity
@Table(name = "person_attendance_init" )
public class AttendanceInit extends BaseEntity<Long> {
	
	/**
	 * 查询字段
	 */
	@Column(name = "user_id")
	private Long userId;
	
	/**
	 * 一对一的用户
	 */
    @OneToOne(fetch=FetchType.LAZY) 
    @JoinColumn(name="user_id",referencedColumnName="id",nullable=true, insertable = false, updatable = false)
    private User user;
    
    
	@Column(name = "username")
	private String username;
    
    
	/**
	 * 约定休息方式（1.周休一天，2.月休两天，其他周日算加班,3.全年无休）
	 */
	@Column(name = "rest_type")
	private Integer restType;
	
	/**
	 * 出勤方式（1.无到岗要求，2，无打卡要求，3.按到岗小时计算）
	 */
	@Column(name = "work_type")
	private Integer workType;
    
	/**
	 * 约定休息日
	 */
	@Column(name = "rest_day") 
	private String restDay;
	
	
	/**
	 * 工作间隔开始时间（上班）工作间隔结束时间（下班）（夏令时）
	 */
	@Column(name = "work_time_summer")
	private String workTimeSummer;
	
	/**
	 * 工作间隔开始时间（上班）工作间隔结束时间（下班）（冬令时)
	 */
	@Column(name = "work_time_winter")
	private String workTimeWinter;
	
	/**
	 * 出勤时长（夏令时长）
	 */
	@Column(name = "turn_work_time_summer")
	private Double turnWorkTimeSummer;
	
	/**
	 * 出勤时长（冬令时长）
	 */
	@Column(name = "turn_work_time_winter")
	private Double turnWorkTimeWinter;
	
	/**
	 * 中午休息开始时间,中午休息结束时间（夏令时）
	 */
	@Column(name = "rest_time_summer")
	private String restTimeSummer;
	
	/**
	 * 中午休息开始时间,中午休息结束时间（冬令时）
	 */
	@Column(name = "rest_time_winter")
	private String restTimeWinter;
	
	/**
	 * 中午休息时长（夏令时长）
	 */
	@Column(name = "rest_summer")
	private Double restSummer;
	
	/**
	 * 中午休息时长（冬令时长）
	 */
	@Column(name = "rest_winter")
	private Double restWinter;
	
	/**
	 * 中午休息时长,1=默认休息,2=出勤,3=加班
	 */
	@Column(name = "rest_time_work")
	private Integer restTimeWork;
	
	/**
	 * 签到时间在下班时间之后是否合算加班（1.看加班申请2.按打卡正常核算加班）
	 * 
	 */
	@Column(name = "over_time_type")
	private Integer overTimeType;
	
	/**
	 * 加班后晚到岗类型(1.按点上班，2.第二天上班时间以超过24:00后的时间往后推,3.超过24:30后默认休息7.5小时)
	 */
	@Column(name = "come_work")
	private Integer comeWork;
	
	/**
	 * 设定此参数后，早于默认上班时间前15分钟，进行加班0.5计算。
	 */
	@Column(name = "earth_work")
	private boolean earthWork = false;
	
	/**
	 * 吃饭（1.早饭2.晚餐3.早晚餐 4.早晚夜宵）
	 * 
	 */
	@Column(name = "eat_type")
	private Integer eatType;
	
	/**
	 * 倒班（1.不倒班2.倒班）
	 * 
	 */
	@Column(name = "fail")
	private Integer fail;
	
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
	 * 查询字段（部门）
	 */
	@Transient
	private String orgName;
	
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
	
	
	

	
	


	public Integer getFail() {
		return fail;
	}


	public void setFail(Integer fail) {
		this.fail = fail;
	}


	public Integer getEatType() {
		return eatType;
	}


	public void setEatType(Integer eatType) {
		this.eatType = eatType;
	}


	public boolean isEarthWork() {
		return earthWork;
	}


	public void setEarthWork(boolean earthWork) {
		this.earthWork = earthWork;
	}


	public Integer getWorkType() {
		return workType;
	}


	public void setWorkType(Integer workType) {
		this.workType = workType;
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


	public Long getOrgNameId() {
		return orgNameId;
	}


	public void setOrgNameId(Long orgNameId) {
		this.orgNameId = orgNameId;
	}


	public String getOrgName() {
		return orgName;
	}


	public void setOrgName(String orgName) {
		this.orgName = orgName;
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


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public Integer getOverTimeType() {
		return overTimeType;
	}


	public void setOverTimeType(Integer overTimeType) {
		this.overTimeType = overTimeType;
	}


	public String getWorkTimeSummer() {
		return workTimeSummer;
	}


	public void setWorkTimeSummer(String workTimeSummer) {
		this.workTimeSummer = workTimeSummer;
	}


	public String getWorkTimeWinter() {
		return workTimeWinter;
	}


	public void setWorkTimeWinter(String workTimeWinter) {
		this.workTimeWinter = workTimeWinter;
	}


	public String getRestTimeSummer() {
		return restTimeSummer;
	}


	public void setRestTimeSummer(String restTimeSummer) {
		this.restTimeSummer = restTimeSummer;
	}


	public String getRestTimeWinter() {
		return restTimeWinter;
	}


	public void setRestTimeWinter(String restTimeWinter) {
		this.restTimeWinter = restTimeWinter;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Integer getRestType() {
		return restType;
	}


	public void setRestType(Integer restType) {
		this.restType = restType;
	}


	public String getRestDay() {
		return restDay;
	}


	public void setRestDay(String restDay) {
		this.restDay = restDay;
	}


	public Double getTurnWorkTimeSummer() {
		return turnWorkTimeSummer;
	}


	public void setTurnWorkTimeSummer(Double turnWorkTimeSummer) {
		this.turnWorkTimeSummer = turnWorkTimeSummer;
	}


	public Double getTurnWorkTimeWinter() {
		return turnWorkTimeWinter;
	}


	public void setTurnWorkTimeWinter(Double turnWorkTimeWinter) {
		this.turnWorkTimeWinter = turnWorkTimeWinter;
	}


	public Double getRestSummer() {
		return restSummer;
	}


	public void setRestSummer(Double restSummer) {
		this.restSummer = restSummer;
	}


	public Double getRestWinter() {
		return restWinter;
	}


	public void setRestWinter(Double restWinter) {
		this.restWinter = restWinter;
	}


	public Integer getRestTimeWork() {
		return restTimeWork;
	}


	public void setRestTimeWork(Integer restTimeWork) {
		this.restTimeWork = restTimeWork;
	}


	public Integer getComeWork() {
		return comeWork;
	}


	public void setComeWork(Integer comeWork) {
		this.comeWork = comeWork;
	}
	
	
    
    

}
