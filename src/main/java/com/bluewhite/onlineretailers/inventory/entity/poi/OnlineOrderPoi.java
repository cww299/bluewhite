package com.bluewhite.onlineretailers.inventory.entity.poi;

import javax.persistence.Column;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

public class OnlineOrderPoi  extends BaseRowModel{
	
	
	/**
	 * 订单编号
	 */
	@ExcelProperty(index = 0)
	private String documentNumber;
	
	/**
	 * 客户昵称
	 * 
	 */
	@ExcelProperty(index = 1)
	private String name;
	
	/**
	 * 实付金额。精确到2位小数;单位:元。如:200.07，表示:200元7分
	 */
	@ExcelProperty(index = 2)
	private Double payment;
	
	/**
	 * 邮费
	 */
	@ExcelProperty(index = 3)
	private Double postFee;
	
	
	/**
	 * 整单优惠
	 */
	@ExcelProperty(index = 4)
	private Double allBillPreferential;
	
	/**
	 * 货品总价。精确到2位小数;单位:元。如:200.07，表示:200元7分
	 */
	@ExcelProperty(index = 5)
	private Double sumPrice;
	
	
	/**
	 * 收货人姓名
	 */
	@ExcelProperty(index = 6)
	private String buyerName;
	
	/**
	 * 收货人的详细地址
	 * 
	 */
	@ExcelProperty(index = 7)
	private String address;
	
	/**
	 * 买家电话号
	 * 
	 */
	@ExcelProperty(index = 8)
	private String telephone;
	
	/**
	 * 买家手机号
	 * 
	 */
	@ExcelProperty(index = 9)
	private String phone;
	
	/**
	 * 单价
	 */
	@ExcelProperty(index = 10)
	private Double price;
	
	/**
	 * 数量
	 */
	@ExcelProperty(index = 11)
	private Integer number;
	
	/**
	 * 商品名称
	 */
	@ExcelProperty(index = 12)
	private String commodityName;
	
	/**
	 * 买家留言
	 * 
	 */
	@ExcelProperty(index = 13)
	private String buyerMessage;
	
	/**
	 * 运单号
	 */
	@ExcelProperty(index = 14)
	private String trackingNumber;
	
	/**
	 * 仓库类型
	 */
	@ExcelProperty(index = 15)
	private Long warehouseId;

	
	
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

	public Double getPayment() {
		return payment;
	}

	public void setPayment(Double payment) {
		this.payment = payment;
	}

	public Double getPostFee() {
		return postFee;
	}

	public void setPostFee(Double postFee) {
		this.postFee = postFee;
	}

	public Double getAllBillPreferential() {
		return allBillPreferential;
	}

	public void setAllBillPreferential(Double allBillPreferential) {
		this.allBillPreferential = allBillPreferential;
	}

	public Double getSumPrice() {
		return sumPrice;
	}

	public void setSumPrice(Double sumPrice) {
		this.sumPrice = sumPrice;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public String getBuyerMessage() {
		return buyerMessage;
	}

	public void setBuyerMessage(String buyerMessage) {
		this.buyerMessage = buyerMessage;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}
	
	
	

}
