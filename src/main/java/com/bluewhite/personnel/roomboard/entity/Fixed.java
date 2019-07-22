package com.bluewhite.personnel.roomboard.entity;

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
@Table(name = "person_fixed")
public class Fixed  extends BaseEntity<Long>{
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
	 * 名称
	 */
	@Column(name = "name")
	private String name;
	
	/**
	 * 固定资产
	 */
	@Column(name = "sum")
	private Double sum;
	
	/**
	 * 剩余的固定资产
	 */
	@Column(name = "surplus_sum")
	private Double surplusSum;
	
	/**
	 * 分成几份
	 */
	@Column(name = "branch")
	private Integer branch;
	
	/**
	 * 每一份的固定资产
	 */
	@Column(name = "price")
	private Double price;

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

	public Double getSum() {
		return sum;
	}

	public void setSum(Double sum) {
		this.sum = sum;
	}

	public Double getSurplusSum() {
		return surplusSum;
	}

	public void setSurplusSum(Double surplusSum) {
		this.surplusSum = surplusSum;
	}

	public Integer getBranch() {
		return branch;
	}

	public void setBranch(Integer branch) {
		this.branch = branch;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
