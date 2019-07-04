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
import com.bluewhite.basedata.entity.BaseData;

/**
 * 已工招工的奖励基础数据
 * 
 * @author qiyong
 *
 */
@Entity
@Table(name = "person_seat" )
public class Seat extends BaseEntity<Long> {
	
	
	/**
	 * 职位id
	 */
	@Column(name = "position_id")
	private Long positionId;
	
	/**
	 * 职位
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "position_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseData position;
	
	/**
	 * 手速（0.不限 1.速度车间排名前30% 2.速度车间排名30%-70% 3.速度车间排名后30%）
	 */
	@Column(name = "speed")
	private Integer speed;
	
	/**
	 * 年龄(0. 小于等于40 1.大于40 2.小于等于50 3.大于50)
	 */
	@Column(name = "age")
	private Integer age;
	
	/**
	 * 转正奖励
	 */
	@Column(name ="price")
	private Double price;
	
	/**
	 * 入职满6个月奖励
	 */
	@Column(name ="Entry_price")
	private Double EntryPrice;
	
	/**
	 * 入职满12个月奖励
	 */
	@Column(name ="year_price")
	private Double yearPrice;
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
	public Long getPositionId() {
		return positionId;
	}
	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}
	public BaseData getPosition() {
		return position;
	}
	public void setPosition(BaseData position) {
		this.position = position;
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
	public Double getEntryPrice() {
		return EntryPrice;
	}
	public void setEntryPrice(Double entryPrice) {
		EntryPrice = entryPrice;
	}
	public Double getYearPrice() {
		return yearPrice;
	}
	public void setYearPrice(Double yearPrice) {
		this.yearPrice = yearPrice;
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
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	
	

}
