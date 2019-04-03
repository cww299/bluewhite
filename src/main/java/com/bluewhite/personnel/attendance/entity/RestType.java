package com.bluewhite.personnel.attendance.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;

@Entity
@Table(name = "person_rest_type")
public class RestType extends BaseEntity<Long>{
	
	/**
	 * 周休1天
	 */
	@Column(name = "weekly_rest_date")
	private String weeklyRestDate;
	
	/**
	 * 月休2天
	 */
	@Column(name = "month_rest_date")
	private String monthRestDate;

	public String getWeeklyRestDate() {
		return weeklyRestDate;
	}

	public void setWeeklyRestDate(String weeklyRestDate) {
		this.weeklyRestDate = weeklyRestDate;
	}

	public String getMonthRestDate() {
		return monthRestDate;
	}

	public void setMonthRestDate(String monthRestDate) {
		this.monthRestDate = monthRestDate;
	}
	
	

}
