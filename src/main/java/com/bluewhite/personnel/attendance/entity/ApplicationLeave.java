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
	 * 请假类型(事假、病假、丧假、婚假、产假、护理假）
	 */
	@Column(name = "holiday_type")
	private Integer holidayType;
	
	/**
	 * 是否请假(true=是)
	 */
	@Column(name = "holiday")
	private boolean holiday;
	
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
	 * （请假，调休，补签，加班）时间(json格式传递（日期+时长）)
	 */
	@Column(name = "time")
	private String time;
	
	/**
	 * 	调休到的日期时间
	 */
	@Column(name = "tradeDays_time")
	private Date tradeDaysTime;

	
	

	public Date getWriteTime() {
		return writeTime;
	}

	public void setWriteTime(Date writeTime) {
		this.writeTime = writeTime;
	}

	public boolean isHoliday() {
		return holiday;
	}

	public void setHoliday(boolean holiday) {
		this.holiday = holiday;
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
