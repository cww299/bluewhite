package com.bluewhite.production.group.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;

/**
 * 借调人员汇总
 * 
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_temporarily_collect")
public class TemporarilyCollect extends BaseEntity<Long> {

	/**
	 * 借调人员id
	 */
	@Column(name = "user_id")
	private Long userId;

	/**
	 * 借调人员姓名
	 */
	@Column(name = "user_name")
	private String userName;
	
	/**
	 * 工作时长
	 */
	@Column(name = "work_time")
	private Double workTime;

	/**
	 * 小时单价
	 */
	@Column(name = "price")
	private Double price;
	
	/**
	 * 工种
	 */
	@Column(name = "kindWork")
	private String kindWork;
	
	/**
	 * 总费用
	 */
	@Column(name = "sum_price")
	private Double sumPrice;
	
	/**
	 * 是否本厂
	 */
	@Column(name = "foreigns")
	private String foreigns;
	
	/**
	 * 汇总日期
	 */
	@Column(name = "temporarily_date")
	private String temporarilyDate;

	/**
	 * 分组所属部门类型 (1=一楼质检，2=一楼包装3.二楼针工)
	 */
	@Column(name = "type")
	private Integer type;

	/**
	 * 时间查询字段
	 */
	@Transient
	private Date orderTimeBegin;
	/**
	 * 时间查询字段
	 */
	@Transient
	private Date orderTimeEnd;
	
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Double getWorkTime() {
		return workTime;
	}
	public void setWorkTime(Double workTime) {
		this.workTime = workTime;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getKindWork() {
		return kindWork;
	}
	public void setKindWork(String kindWork) {
		this.kindWork = kindWork;
	}
	public Double getSumPrice() {
		return sumPrice;
	}
	public void setSumPrice(Double sumPrice) {
		this.sumPrice = sumPrice;
	}
	public String getForeigns() {
		return foreigns;
	}
	public void setForeigns(String foreigns) {
		this.foreigns = foreigns;
	}
	
	public String getTemporarilyDate() {
		return temporarilyDate;
	}
	public void setTemporarilyDate(String temporarilyDate) {
		this.temporarilyDate = temporarilyDate;
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
