package com.bluewhite.production.finance.entity;

import java.util.Date;

import javax.persistence.Transient;

import com.bluewhite.common.utils.excel.Poi;

/**
 * 月产量报表
 * @author zhangliang
 *
 */
public class MonthlyProduction {
	/**
	 * 考勤人数
	 */
	@Poi(name = "当天出勤人数", column = "B")
	private Integer peopleNumber;
	
	/**
	 * 考勤总时间  
	 */
	@Poi(name = "当天总出勤", column = "C")
	private Double  time;
	
	/**
	 * 当天产量  
	 */
	@Poi(name = "当天产量", column = "D")
	private Double  productNumber;
	
	/**
	 * 当天产值(外发单价乘以质检的个数)   
	 */
	@Poi(name = "当天产值", column = "E")
	private Double productPrice;
	
	/**
	 * 杂工总时间
	 */
	@Poi(name = "杂工时间", column = "J")
	private Double  farragoTaskTime;
	
	/**
	 * 杂工价值 
	 */
	@Poi(name = "杂工价值", column = "K")
	private Double farragoTaskPrice;
	
	/**
	 * 返工时间     
	 */
	@Poi(name = "返工时间（小时)", column = "F")
	private Double  reworkTime;
	
	/**
	 * 返工人员   
	 */
	@Poi(name = "返工人员签字", column = "G")
	private String  userName;
	
	/**
	 * 返工出勤人数   
	 */
	private Integer  reworkNumber;
	
	/**
	 * 返工出勤时间   
	 */
	private Double  reworkTurnTime;
	
	/**
	 * 返工个数  
	 */
	private Double  rework;
	
	/**
	 * 备注
	 */
	@Poi(name = "备注", column = "H")
	private String  remark;
	
	/**
	 * 车间主任签名  
	 */
	@Poi(name = "车间主任签名", column = "I")
	private String  sign;
	
	
	/**
	 * 日期
	 */
	@Poi(name = "日期", column = "A")
	private String startDate;
	
	/**
	 * 返工再验个数
	 */
	@Poi(name = "返工再验个数", column = "L")
	private Double reworkCount;
	
	/**
	 * 查询字段开始时间
	 */
	private Date orderTimeBegin;
	/**
	 * 查询字段结束时间
	 */
	private Date orderTimeEnd;
	
	
	private Integer type;
	
    /**
     * 机工区分字段（0=二楼，1=三楼）
     */
    private Integer machinist;
	
	
	

	public Integer getMachinist() {
		return machinist;
	}

	public void setMachinist(Integer machinist) {
		this.machinist = machinist;
	}

	public Double getReworkCount() {
		return reworkCount;
	}

	public void setReworkCount(Double reworkCount) {
		this.reworkCount = reworkCount;
	}

	public Double getFarragoTaskTime() {
		return farragoTaskTime;
	}

	public void setFarragoTaskTime(Double farragoTaskTime) {
		this.farragoTaskTime = farragoTaskTime;
	}

	public Double getFarragoTaskPrice() {
		return farragoTaskPrice;
	}

	public void setFarragoTaskPrice(Double farragoTaskPrice) {
		this.farragoTaskPrice = farragoTaskPrice;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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



	public Integer getPeopleNumber() {
		return peopleNumber;
	}

	public void setPeopleNumber(Integer peopleNumber) {
		this.peopleNumber = peopleNumber;
	}

	public Double getTime() {
		return time;
	}

	public void setTime(Double time) {
		this.time = time;
	}

	public Double getProductNumber() {
		return productNumber;
	}

	public void setProductNumber(Double productNumber) {
		this.productNumber = productNumber;
	}

	public Double getProductPrice() {
		return productPrice;
	}

	public void setProductPrice(Double productPrice) {
		this.productPrice = productPrice;
	}


	public Integer getReworkNumber() {
		return reworkNumber;
	}

	public void setReworkNumber(Integer reworkNumber) {
		this.reworkNumber = reworkNumber;
	}

	public Double getReworkTurnTime() {
		return reworkTurnTime;
	}

	public void setReworkTurnTime(Double reworkTurnTime) {
		this.reworkTurnTime = reworkTurnTime;
	}

	public Double getRework() {
		return rework;
	}

	public void setRework(Double rework) {
		this.rework = rework;
	}

	public Double getReworkTime() {
		return reworkTime;
	}

	public void setReworkTime(Double reworkTime) {
		this.reworkTime = reworkTime;
	}
	
	
	
}
