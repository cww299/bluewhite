package com.bluewhite.personnel.attendance.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;



public class AttendanceTime {
	
	
	/**
	 * 员工考勤日期
	 * 
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date time;
	
	/**
	 * 员工编号（考勤机上的编号）
	 * 
	 */
	private String number;
	
	/**
	 * 员工姓名
	 * 
	 */
	private String username;
	
	/**
	 * 上班签到时间  0—Check-In 
	 * 
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date checkIn ;
	
	/**
	 * 下班签到时间 1—Check-Out
	 * 
	 */
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	private Date checkOut;
	
	/**
	 * 
	 * 工作时长
	 */
	private Double workTime;
	
	/**
	 * 
	 * 出勤时长
	 */
	private Double turnWorkTime;
	
	/**
	 * 加班时长
	 * 
	 */
	private Double overtime;
	
	/**
	 * 缺勤时长
	 * 
	 */
	private Double dutytime;
	
	/**
	 * 星期
	 * 
	 */
	private String week;
	
	

	public Double getDutytime() {
		return dutytime;
	}

	public void setDutytime(Double dutytime) {
		this.dutytime = dutytime;
	}

	public Double getWorkTime() {
		return workTime;
	}

	public void setWorkTime(Double workTime) {
		this.workTime = workTime;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getCheckIn() {
		return checkIn;
	}

	public void setCheckIn(Date checkIn) {
		this.checkIn = checkIn;
	}

	public Date getCheckOut() {
		return checkOut;
	}

	public void setCheckOut(Date checkOut) {
		this.checkOut = checkOut;
	}

	public Double getTurnWorkTime() {
		return turnWorkTime;
	}

	public void setTurnWorkTime(Double turnWorkTime) {
		this.turnWorkTime = turnWorkTime;
	}

	public Double getOvertime() {
		return overtime;
	}

	public void setOvertime(Double overtime) {
		this.overtime = overtime;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}
	
	
	
	

}
