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

/**
 * 招聘汇总数据
 * 
 * @author qiyong
 *
 */
@Entity
@Table(name = "person_reward" )
public class Reward extends BaseEntity<Long> {
	
	/**
	 * 时间
	 */
	@Column(name = "time")
	private Date time;
	
	/**
	 * 招聘记录
	 */
	@Column(name = "recruit_id")
	private Long recruitId;
	
	/**
	 * 招聘
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recruit_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Recruit recruitName;
	
	/**
	 * 手速
	 */
	@Column(name = "speed")
	private Integer speed;
	
	
	/**
	 * 奖励费用
	 */
	@Column(name ="price")
	private Double price;
	/**
	 *0.转正 1.入职满6个月 2.入职满1年
	 */
	@Column(name ="type")
	private Integer type;
	
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
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public Long getRecruitId() {
		return recruitId;
	}
	public void setRecruitId(Long recruitId) {
		this.recruitId = recruitId;
	}
	public Recruit getRecruitName() {
		return recruitName;
	}
	public void setRecruitName(Recruit recruitName) {
		this.recruitName = recruitName;
	}
	public Integer getSpeed() {
		return speed;
	}
	public void setSpeed(Integer speed) {
		this.speed = speed;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
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
	

}
