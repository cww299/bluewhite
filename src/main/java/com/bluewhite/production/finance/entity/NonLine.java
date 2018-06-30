package com.bluewhite.production.finance.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.bluewhite.base.BaseEntity;

/**
 * 非一线员工绩效汇总表
 * @author zhangliang
 *
 */
@Entity
@Table(name = "pro_non_line")
public class NonLine extends BaseEntity<Long>{
	
	/**
	 * 员工姓名
	 */
	@Column(name = "user_name")
    private String userName;
	
	/**
	 * 员工id
	 */
	@Column(name = "user_id")
    private Long userId;
	
	
	/**
	 * 考勤时间
	 */
	@Column(name = "time")
	private Double time;
	
	/**
	 * 人为改变后产生的量化绩效出勤时间
	 */
	@Column(name = "change_time")
	private Double changeTime;
	
	/**
	 * 产生考勤工资和已发绩效
	 */
	@Column(name = "pay")
	private Double pay;
	
	
	/**
	 * 单只协助发货费用/元选择
	 */
	@Column(name = "onePay")
	private Double onePay;
	
	
	/**
	 * 人为手动加减量化绩效比
	 */
	@Column(name = "addition")
	private Double addition;
	
	
	/**
	 * 累计产生的发货绩效
	 */
	@Column(name = "cumulative")
	private Double cumulative;

	/**
	 * 剩余管理加绩发放
	 */
	@Column(name = "surplusManagement ")
	private Double surplusManagement;
	
	/**
	 * 分组包装所累积的产量
	 */
	@Column(name = "accumulate_yield")
	private Integer accumulateYield;
	
	
	/**
	 * 所有日期的所对应的产量
	 * @return
	 */
	@Column(name = "yields")
	private String yields;
	
	/**
	 * 类型
	 * @return
	 */
	@Column(name = "type")
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
	
	
	
	
	public Double getChangeTime() {
		return changeTime;
	}


	public void setChangeTime(Double changeTime) {
		this.changeTime = changeTime;
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


	public Integer getAccumulateYield() {
		return accumulateYield;
	}


	public void setAccumulateYield(Integer accumulateYield) {
		this.accumulateYield = accumulateYield;
	}


	public Double getSurplusManagement() {
		return surplusManagement;
	}


	public void setSurplusManagement(Double surplusManagement) {
		this.surplusManagement = surplusManagement;
	}



	public String getYields() {
		return yields;
	}


	public void setYields(String yields) {
		this.yields = yields;
	}


	public Double getCumulative() {
		return cumulative;
	}


	public void setCumulative(Double cumulative) {
		this.cumulative = cumulative;
	}


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


	public Double getTime() {
		return time;
	}


	public void setTime(Double time) {
		this.time = time;
	}


	public Double getPay() {
		return pay;
	}


	public void setPay(Double pay) {
		this.pay = pay;
	}


	public Double getOnePay() {
		return onePay;
	}


	public void setOnePay(Double onePay) {
		this.onePay = onePay;
	}


	public Double getAddition() {
		return addition;
	}


	public void setAddition(Double addition) {
		this.addition = addition;
	}
	
	
	
	

}
