package com.bluewhite.production.temporarypack;


import java.util.Date;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 导出量化单实体 客户 批次号 产品名 单包个数 备注
 * 
 * @author zhangliang
 *
 */
public class QuantitativePoi {
	
	@ExcelIgnore
	private Long id;

	/**
	 * 量化编号
	 */
	@ExcelIgnore
	private String quantitativeNumber;

	/**
	 * 贴包人
	 */
	@ExcelIgnore
	private String userName;
	
	/**
	 * 发货时间
	 */
	@ExcelProperty("发货时间")
	private Date sendTime;

	/**
	 * 客户
	 * 
	 */
	@ExcelProperty("客户")
	private String customerName;

	/**
	 * 批次号
	 */
	@ExcelProperty("批次号")
	private String bacth;

	/**
	 * 产品名称
	 */
	@ExcelProperty("产品名称")
	private String name;

	/**
	 * 发货数量
	 */
	@ExcelProperty("发货数量")
	private Integer number;
	/**
	 * 备注
	 */
	@ExcelProperty("备注")
	private String remark;
	
	
	
	
	public String getBacth() {
		return bacth;
	}
	public void setBacth(String bacth) {
		this.bacth = bacth;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getQuantitativeNumber() {
		return quantitativeNumber;
	}
	public void setQuantitativeNumber(String quantitativeNumber) {
		this.quantitativeNumber = quantitativeNumber;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Date getSendTime() {
		return sendTime;
	}
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	

}
