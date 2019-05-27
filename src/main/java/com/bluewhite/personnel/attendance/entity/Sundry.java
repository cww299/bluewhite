package com.bluewhite.personnel.attendance.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.bluewhite.base.BaseEntity;
/**
 * 住宿水电费
 * @author qiyong
 *
 */
@Entity
@Table(name = "person_sundry")
public class Sundry  extends BaseEntity<Long>{
	/**
	 * 宿舍id
	 */
	@Column(name = "hostel_id")
	private Long hostelId;
	
	/**
	 * 宿舍
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hostel_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Hostel hostel;
	
	
	/**
	 * 月份
	 */
	@Column(name = "month_date")
    private Date monthDate;
	
	
	/**
	 * 当月房租
	 */
	@Column(name = "rent")
	private Double rent;
	
	/**
	 * 当月标准内的水费
	 */
	@Column(name = "water")
	private Double water;
	
	/**
	 * 当月标准内的电费
	 */
	@Column(name = "power")
	private Double power ;
	
	/**
	 * 当月煤气费
	 */
	@Column(name = "coal")
	private Double coal;
	
	/**
	 * 固定资产
	 */
	@Column(name = "fixed")
	private Double fixed;
	
	/**
	 * 当月宽带费
	 */
	@Column(name = "broadband")
	private Double broadband;
	
	/**
	 * 当月安检管理费
	 */
	@Column(name = "administration")
	private Double administration;
	
	/**
	 * 当月总费用
	 */
	@Column(name = "summary_price")
	private Double summaryPrice;
	
	public Long getHostelId() {
		return hostelId;
	}

	public void setHostelId(Long hostelId) {
		this.hostelId = hostelId;
	}

	public Hostel getHostel() {
		return hostel;
	}

	public void setHostel(Hostel hostel) {
		this.hostel = hostel;
	}

	public Date getMonthDate() {
		return monthDate;
	}

	public void setMonthDate(Date monthDate) {
		this.monthDate = monthDate;
	}

	public Double getRent() {
		return rent;
	}

	public void setRent(Double rent) {
		this.rent = rent;
	}

	public Double getWater() {
		return water;
	}

	public void setWater(Double water) {
		this.water = water;
	}

	public Double getPower() {
		return power;
	}

	public void setPower(Double power) {
		this.power = power;
	}

	public Double getCoal() {
		return coal;
	}

	public void setCoal(Double coal) {
		this.coal = coal;
	}

	public Double getBroadband() {
		return broadband;
	}

	public void setBroadband(Double broadband) {
		this.broadband = broadband;
	}

	

	public Double getSummaryPrice() {
		return summaryPrice;
	}

	public void setSummaryPrice(Double summaryPrice) {
		this.summaryPrice = summaryPrice;
	}

	public Double getAdministration() {
		return administration;
	}

	public void setAdministration(Double administration) {
		this.administration = administration;
	}

	public Double getFixed() {
		return fixed;
	}

	public void setFixed(Double fixed) {
		this.fixed = fixed;
	}

	
	
	
}
