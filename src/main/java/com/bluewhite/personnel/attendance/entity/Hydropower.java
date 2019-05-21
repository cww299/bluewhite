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
 * 住宿水电费
 * @author qiyong
 *
 */
@Entity
@Table(name = "person_hydropower")
public class Hydropower  extends BaseEntity<Long>{
	/**
	 * 员工
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
	private User user;
	
	
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
	 * 当月抄表度数（吨）
	 */
	@Column(name = "now_degree_num")
	private Integer nowDegreeNum;
	
	/**
	 * 上月抄表度数（吨）
	 */
	@Column(name = "upper_degree_num")
	private Integer upperDegreeNum;
	
	/**
	 * 当月度数（吨）
	 */
	@Column(name = "sum")
	private Integer sum;
	
	/**
	 * 标准度数（吨数）
	 */
	@Column(name = "talon_num")
	private Integer talonNum;
	
	/**
	 * 金额
	 */
	@Column(name = "price")
	private Double price;
	
	/**
	 * 总金额
	 */
	@Column(name = "summary_price")
	private Double summaryPrice;
	

	/**
	 * 1.水费 2.电费
	 */
	@Column(name = "type")
	private Integer type;
	
	/**
	 * 超支的度数(吨数)
	 */
	@Column(name = "exceed_num")
	private Integer exceedNum;
	
	/**
	 * 超支金额
	 */
	@Column(name = "exceed_price")
	private Double exceedPrice;
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getHostelId() {
		return hostelId;
	}

	public void setHostelId(Long hostelId) {
		this.hostelId = hostelId;
	}

	public Hostel getHostel() {
		return hostel;
	}
	
	public Double getExceedPrice() {
		return exceedPrice;
	}

	public void setExceedPrice(Double exceedPrice) {
		this.exceedPrice = exceedPrice;
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

	public Integer getNowDegreeNum() {
		return nowDegreeNum;
	}

	public void setNowDegreeNum(Integer nowDegreeNum) {
		this.nowDegreeNum = nowDegreeNum;
	}

	public Integer getUpperDegreeNum() {
		return upperDegreeNum;
	}

	public void setUpperDegreeNum(Integer upperDegreeNum) {
		this.upperDegreeNum = upperDegreeNum;
	}

	

	public Integer getTalonNum() {
		return talonNum;
	}

	public void setTalonNum(Integer talonNum) {
		this.talonNum = talonNum;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getSummaryPrice() {
		return summaryPrice;
	}

	public void setSummaryPrice(Double summaryPrice) {
		this.summaryPrice = summaryPrice;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getExceedNum() {
		return exceedNum;
	}

	public void setExceedNum(Integer exceedNum) {
		this.exceedNum = exceedNum;
	}

	public Integer getSum() {
		return sum;
	}

	public void setSum(Integer sum) {
		this.sum = sum;
	}


	
}
