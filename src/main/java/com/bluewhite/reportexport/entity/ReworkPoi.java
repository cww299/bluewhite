package com.bluewhite.reportexport.entity;

import java.util.Date;

import com.bluewhite.common.utils.excel.Poi;

public class ReworkPoi {
	
	/**
	 * 批次名
	 */	
	@Poi(name = "当表包装批次名", column = "A")
	private String bacthNumber;
	
	/**
	 * 产品名称
	 */
	@Poi(name = "当表包装产品名", column = "B")
	private String name;
	
	/**
	 * 返工个数
	 */
	@Poi(name = "被返工个数", column = "C")
	private Integer number;
	
	
	/**
	 * 费用
	 */
	@Poi(name = "在本部产生的返工费用", column = "D")
	private Double price;
	
	
	/**
	 * 返工时间
	 */
	@Poi(name = "当表当批返工时间（小时）", column = "E")
	private Double time;
	
	
	/**
	 * 被返工单位名
	 */
	@Poi(name = "被返工单位名", column = "F")
	private String remark;
	
	/**
	 * 完成日期
	 */
	@Poi(name = "完成日期", column = "G")
	private Date datetime;

	/**
	 * 返工人员
	 */
	@Poi(name = "完成人员", column = "H")
	private String username;
	
	/**
	 * 批次总数量
	 */
	@Poi(name = "批次总数量", column = "I")
	private Integer sumNumber;

	/**
	 * 返工率
	 */
	@Poi(name = "返工率", column = "J")
	private Double reworkRate;



	

	public Integer getSumNumber() {
		return sumNumber;
	}


	public void setSumNumber(Integer sumNumber) {
		this.sumNumber = sumNumber;
	}


	public Double getReworkRate() {
		return reworkRate;
	}


	public void setReworkRate(Double reworkRate) {
		this.reworkRate = reworkRate;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public Date getDatetime() {
		return datetime;
	}


	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}


	public String getBacthNumber() {
		return bacthNumber;
	}


	public void setBacthNumber(String bacthNumber) {
		this.bacthNumber = bacthNumber;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Integer getNumber() {
		return number;
	}


	public void setNumber(Integer number) {
		this.number = number;
	}


	public Double getPrice() {
		return price;
	}


	public void setPrice(Double price) {
		this.price = price;
	}


	public Double getTime() {
		return time;
	}


	public void setTime(Double time) {
		this.time = time;
	}


	public String getRemark() {
		return remark;
	}


	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
	
	
	

}
