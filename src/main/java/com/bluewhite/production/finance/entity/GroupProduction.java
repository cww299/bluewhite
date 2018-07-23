package com.bluewhite.production.finance.entity;

import java.util.Date;

import javax.persistence.Transient;

import com.bluewhite.common.utils.excel.Poi;

/**
 * 分组按日期产量
 * @author zhangliang
 *
 */
public class GroupProduction {
	
	/**
	 * 查询字段开始时间
	 */
	private Date orderTimeBegin;
	/**
	 * 查询字段结束时间
	 */
	private Date orderTimeEnd;
	
	/**
	 * 日期
	 */
	@Poi(name = "日期", column = "A")
	private String startDate;
	
	/**
	 * 产品名字
	 */
	@Poi(name = "产品批次号品名", column = "B")
	private String name;
	
	/**
	 * 总产量
	 */
	@Poi(name = "总数量", column = "C")
	private Integer sumNumber;
	
	/**
	 * 一组产量
	 */
	@Poi(name = "一组", column = "D")
	private Integer oneNumber;
	
	/**
	 * 二组产量
	 */
	@Poi(name = "二组", column = "E")
	private Integer twoNumber;
	
	/**
	 * 三组产量
	 */
	@Poi(name = "三组", column = "F")
	private Integer threeNumber;
	
	/**
	 * 四组产量
	 */
	@Poi(name = "四组", column = "G")
	private Integer fourNumber;
	
	/**
	 * 备注
	 */
	@Poi(name = "备注", column = "H")
	private String remark;
	
	//工序部门类型
	@Transient
	private Integer type;	

	
	
		
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public Integer getSumNumber() {
		return sumNumber;
	}

	public void setSumNumber(Integer sumNumber) {
		this.sumNumber = sumNumber;
	}

	
	public Integer getOneNumber() {
		return oneNumber;
	}

	public void setOneNumber(Integer oneNumber) {
		this.oneNumber = oneNumber;
	}

	public Integer getTwoNumber() {
		return twoNumber;
	}

	public void setTwoNumber(Integer twoNumber) {
		this.twoNumber = twoNumber;
	}

	public Integer getThreeNumber() {
		return threeNumber;
	}

	public void setThreeNumber(Integer threeNumber) {
		this.threeNumber = threeNumber;
	}

	public Integer getFourNumber() {
		return fourNumber;
	}

	public void setFourNumber(Integer fourNumber) {
		this.fourNumber = fourNumber;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
	
	

}
