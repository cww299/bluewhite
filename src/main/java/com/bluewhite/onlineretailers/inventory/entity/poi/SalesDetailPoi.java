package com.bluewhite.onlineretailers.inventory.entity.poi;

import java.util.Date;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

public class SalesDetailPoi extends BaseRowModel{
	
	@ExcelProperty(value = "日期" ,index = 0)
    private Date time;

    @ExcelProperty(value = "客户",index = 1)
    private String customer;

    @ExcelProperty(value = "货号",index = 2)
    private String name;

    @ExcelProperty(value = "数量",index = 3)
    private int number;

    @ExcelProperty(value = "价格",index = 4)
    private double price;

    @ExcelProperty(value = "金额",index = 5)
    private double money;

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

    
    
}
