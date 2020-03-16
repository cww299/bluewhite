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
	
	/****  财务  ****/
    
    /**
     * 发货时间
     */
    @ExcelProperty("发货时间")
    private Date sendTime1;

    /**
     * 客户
     * 
     */
    @ExcelProperty("客户")
    private String customerName1;

    /**
     * 批次号
     */
    @ExcelProperty("批次号")
    private String bacth1;

    /**
     * 产品名称
     */
    @ExcelProperty("产品名称")
    private String name1;

    /**
     * 发货数量
     */
    @ExcelProperty("发货数量")
    private Integer number1;
    
    /**
     * 量化编号
     */
    @ExcelProperty("编号")
    private String quantitativeNumber;
	
	
	
	
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
    public Date getSendTime1() {
        return sendTime1;
    }
    public void setSendTime1(Date sendTime1) {
        this.sendTime1 = sendTime1;
    }
    public String getCustomerName1() {
        return customerName1;
    }
    public void setCustomerName1(String customerName1) {
        this.customerName1 = customerName1;
    }
    public String getBacth1() {
        return bacth1;
    }
    public void setBacth1(String bacth1) {
        this.bacth1 = bacth1;
    }
    public String getName1() {
        return name1;
    }
    public void setName1(String name1) {
        this.name1 = name1;
    }
    public Integer getNumber1() {
        return number1;
    }
    public void setNumber1(Integer number1) {
        this.number1 = number1;
    }
	
	
	
	

}
