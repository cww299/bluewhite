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
import com.bluewhite.basedata.entity.BaseData;

/**
 * 单餐费用
 * 
 * @author qiyong
 *
 */
@Entity
@Table(name = "person_reward" )
public class SingleMeal extends BaseEntity<Long> {
	
	/**
	 * 时间
	 */
	@Column(name = "time")
	private Date time;
	
	
	/**
	 * 物料id
	 */
	@Column(name = "single_meal_consumption_id")
	private Long singleMealConsumptionId;
	
	/**
	 * 物料
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "single_meal_consumption_id", referencedColumnName = "id", insertable = false, updatable = false)
	private BaseData singleMealConsumption;
	
	/**
	 *内容
	 */
	@Column(name ="content")
	private String content;
	
	/**
	 * 每天花费
	 */
	@Column(name = "price")
	private Double price ;
	
	/**
	 *1.早餐 2.中餐 3.晚餐 4.夜宵
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
	public Long getSingleMealConsumptionId() {
		return singleMealConsumptionId;
	}
	public void setSingleMealConsumptionId(Long singleMealConsumptionId) {
		this.singleMealConsumptionId = singleMealConsumptionId;
	}
	public BaseData getSingleMealConsumption() {
		return singleMealConsumption;
	}
	public void setSingleMealConsumption(BaseData singleMealConsumption) {
		this.singleMealConsumption = singleMealConsumption;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
