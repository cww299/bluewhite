package com.bluewhite.personnel.roomboard.entity;

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
 * 住宿记录
 * @author qiyong
 *
 */
@Entity
@Table(name = "person_live")
public class Live  extends BaseEntity<Long>{
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
	 * 床位编号
	 */
	@Column(name = "bed")
	private Integer bed;
	
	/**
	 * 入住时间
	 */
	@Column(name = "in_live_date")
    private Date inLiveDate;
	
	/**
	 * 退房时间
	 */
	@Column(name = "ot_live_date")
    private Date otLiveDate;
	
	/**
	 * 宿舍备注
	 */
	@Column(name = "live_remark")
	private String liveRemark;
	
	/**
	 * 状态
	 */
	@Column(name = "type")
	private Integer type;
	
	/**
	 * 预存天数
	 */
	@Transient
	private Long day;
	
	/**
	 * 预存金额
	 */
	@Transient
	private Double money;

	
	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Long getDay() {
		return day;
	}

	public void setDay(Long day) {
		this.day = day;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

	public Integer getBed() {
		return bed;
	}

	public void setBed(Integer bed) {
		this.bed = bed;
	}

	public Date getInLiveDate() {
		return inLiveDate;
	}

	public void setInLiveDate(Date inLiveDate) {
		this.inLiveDate = inLiveDate;
	}

	public Date getOtLiveDate() {
		return otLiveDate;
	}

	public void setOtLiveDate(Date otLiveDate) {
		this.otLiveDate = otLiveDate;
	}

	public String getLiveRemark() {
		return liveRemark;
	}

	public void setLiveRemark(String liveRemark) {
		this.liveRemark = liveRemark;
	}
	
	

}
