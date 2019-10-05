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
	 * 招聘人ID
	 */
	@Column(name = "recruit_id")
	private Long recruitId;
	
	/**
	 * 被聘人ID
	 */
	@Column(name = "cover_recruit_id")
	private Long coverRecruitId;
	
	/**
	 * 招聘
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cover_recruit_id", referencedColumnName = "id", insertable = false, updatable = false)
	private Recruit recruitName;
	
	/**
	 * 奖励费用
	 */
	@Column(name ="price")
	private Double price;
	
	/**
	 *0.奖励 1.领取
	 */
	@Column(name ="type")
	private Integer type;
	
	/**
	 *备注
	 */
	@Column(name ="remarks")
	private String remarks;
	
	
	/**
	 * 累计奖励
	 */
	@Transient
	private Double collarPrice;
	
	/**
	 * 剩余奖励
	 */
	@Transient
	private Double hairPrice;
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
	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public Long getRecruitId() {
		return recruitId;
	}
	public void setRecruitId(Long recruitId) {
		this.recruitId = recruitId;
	}
	public Long getCoverRecruitId() {
		return coverRecruitId;
	}
	public void setCoverRecruitId(Long coverRecruitId) {
		this.coverRecruitId = coverRecruitId;
	}
	public Recruit getRecruitName() {
		return recruitName;
	}
	public void setRecruitName(Recruit recruitName) {
		this.recruitName = recruitName;
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
	public Double getCollarPrice() {
		return collarPrice;
	}
	public void setCollarPrice(Double collarPrice) {
		this.collarPrice = collarPrice;
	}
	public Double getHairPrice() {
		return hairPrice;
	}
	public void setHairPrice(Double hairPrice) {
		this.hairPrice = hairPrice;
	}
	
	

}
